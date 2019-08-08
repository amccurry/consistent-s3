package s3;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.DeleteObjectsRequest;
import com.amazonaws.services.s3.model.DeleteObjectsRequest.KeyVersion;
import com.amazonaws.services.s3.model.DeleteObjectsResult;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.thirdparty.jackson.databind.ObjectMapper;
import com.amazonaws.util.IOUtils;
import com.amazonaws.util.StringUtils;

public class ConsistentAmazonS3 extends AbstractAmazonS3 {

  private static final Logger LOGGER = LoggerFactory.getLogger(ConsistentAmazonS3.class);

  private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
  private static final String CONSISTENCY_KEY = "consistencyKey";
  private static final String _404_NOT_FOUND = "404 Not Found";
  private final CuratorFramework _curatorFramework;
  private final String _zkPrefix;
  private final long _retryDelay = TimeUnit.SECONDS.toMillis(1);
  private final int _maxConsistencyCount = 10;

  public static ConsistentAmazonS3 create(AmazonS3 client, CuratorFramework curatorFramework) throws Exception {
    return new ConsistentAmazonS3(client, curatorFramework, null);
  }

  public static ConsistentAmazonS3 create(AmazonS3 client, CuratorFramework curatorFramework, String zkPrefix)
      throws Exception {
    return new ConsistentAmazonS3(client, curatorFramework, zkPrefix);
  }

  private ConsistentAmazonS3(AmazonS3 client, CuratorFramework curatorFramework, String zkPrefix) throws Exception {
    super(client);
    _curatorFramework = curatorFramework;
    _zkPrefix = zkPrefix;
  }

  public void checkOutstandingConsistencyEntries(boolean waitForEntriesToBecomeConsistent) throws Exception {
    List<String> list = CuratorUtils.getChildrenIfExists(_curatorFramework, getPrefix());
    if (list == null) {
      return;
    }
    for (String s : list) {
      String path = getPrefix() + "/" + s;
      ConsistencyEntry entry = getConsistencyEntry(path);
      if (entry != null) {
        ObjectMetadata objectMetadata = getObjectMetadata(entry.getBucket(), entry.getKey());
        if (waitForEntriesToBecomeConsistent) {
          while (!isValid(entry, objectMetadata, true)) {
            Thread.sleep(_retryDelay);
          }
        } else {
          isValid(entry, objectMetadata);
        }
      }
    }
  }

  public List<ConsistencyEntry> getOutstandingConsistencyEntries() throws Exception {
    List<ConsistencyEntry> result = new ArrayList<>();
    List<String> list = CuratorUtils.getChildrenIfExists(_curatorFramework, getPrefix());
    if (list == null) {
      return result;
    }
    for (String s : list) {
      String path = getPrefix() + "/" + s;
      result.add(getConsistencyEntry(path));
    }
    return result;
  }

  public S3Object getConsistentObject(GetObjectRequest getObjectRequest) throws Exception {
    String consistencyPath = getConsistencyPath(getObjectRequest.getBucketName(), getObjectRequest.getKey());
    while (true) {
      ConsistencyEntry entry = getConsistencyEntry(consistencyPath);
      if (entry != null) {
        // none found just fetch the object
        return getObject(getObjectRequest);
      }
      try {
        S3Object object = getObject(getObjectRequest);
        if (isValid(entry, object.getObjectMetadata())) {
          return object;
        }
      } catch (AmazonS3Exception e) {
        LOGGER.error(e.getMessage(), e);
        if (!e.getErrorCode()
              .equals(_404_NOT_FOUND)) {
          throw e;
        }
      }
      Thread.sleep(_retryDelay);
    }
  }

  private boolean isValid(ConsistencyEntry entry, ObjectMetadata objectMetadata) throws Exception {
    return isValid(entry, objectMetadata, false);
  }

  private boolean isValid(ConsistencyEntry entry, ObjectMetadata objectMetadata, boolean fullyConsistent)
      throws Exception {
    String actualConsistencyKey = objectMetadata.getUserMetaDataOf(CONSISTENCY_KEY);
    if (actualConsistencyKey.equals(entry.getConsistencyKey())) {
      if (fullyConsistent) {
        return markEntryAsPotentiallyConsistent(entry);
      } else {
        markEntryAsPotentiallyConsistent(entry);
        return true;
      }
    }
    LOGGER.warn("entry {} is NOT consistent", entry);
    return false;
  }

  /**
   * 
   * @param entry
   * @return return true if known to be consistent, false if probably.
   * @throws Exception
   */
  private boolean markEntryAsPotentiallyConsistent(ConsistencyEntry entry) throws Exception {
    String consistencyPath = getConsistencyPath(entry.getBucket(), entry.getKey());
    LOGGER.debug("entry is potentially consistent {}", entry);
    while (true) {
      if (updateEntryConsistencyInfo(entry)) {
        CuratorUtils.deletePathIfExists(_curatorFramework, consistencyPath);
        return true;
      } else {
        if (setConsistencyEntry(entry)) {
          LOGGER.debug("setConsistencyEntry successful {}", entry);
          return false;
        }
        LOGGER.debug("setConsistencyEntry failed, refetch {}", entry);
        entry = getConsistencyEntry(consistencyPath);
      }
    }
  }

  private boolean updateEntryConsistencyInfo(ConsistencyEntry entry) throws Exception {
    if (entry.getConsistencyCount() >= _maxConsistencyCount) {
      return true;
    }
    if (entry.getFirstConsistentView() == 0) {
      entry.setConsistencyCount(1);
      entry.setFirstConsistentView(System.currentTimeMillis());
    } else {
      entry.setConsistencyCount(entry.getConsistencyCount() + 1);
    }
    return false;
  }

  private boolean setConsistencyEntry(ConsistencyEntry entry) throws Exception {
    LOGGER.debug("setConsistencyEntry entry {}", entry);
    String path = getConsistencyPath(entry.getBucket(), entry.getKey());
    Stat stat = entry.getStat();
    byte[] data = OBJECT_MAPPER.writeValueAsBytes(entry);
    if (stat == null) {
      // probably new
      if (CuratorUtils.createPath(_curatorFramework, path, data)) {
        entry.setStat(CuratorUtils.getStatPath(_curatorFramework, path));
        return true;
      }
    } else {
      if (CuratorUtils.setData(_curatorFramework, path, data, stat)) {
        entry.setStat(CuratorUtils.getStatPath(_curatorFramework, path));
        return true;
      }
    }
    return false;
  }

  private ConsistencyEntry getConsistencyEntry(String path) throws Exception {
    DataResult dataResult = CuratorUtils.getDataIfExists(_curatorFramework, path);
    if (dataResult != null) {
      ConsistencyEntry entry = OBJECT_MAPPER.readValue(dataResult.getData(), ConsistencyEntry.class);
      entry.setStat(dataResult.getStat());
      return entry;
    }
    return null;
  }

  public PutObjectResult putConsistentObject(PutObjectRequest putObjectRequest) throws Exception {
    ObjectMetadata metadata = putObjectRequest.getMetadata();
    if (metadata == null) {
      metadata = new ObjectMetadata();
    }
    String consistencyKey = UUID.randomUUID()
                                .toString();
    metadata.addUserMetadata(CONSISTENCY_KEY, consistencyKey);
    putObjectRequest.setMetadata(metadata);
    String path = getConsistencyPath(putObjectRequest.getBucketName(), putObjectRequest.getKey());

    ConsistencyEntry entry = new ConsistencyEntry();
    entry.setBucket(putObjectRequest.getBucketName());
    entry.setKey(putObjectRequest.getKey());
    entry.setConsistencyKey(consistencyKey);
    byte[] data = OBJECT_MAPPER.writeValueAsBytes(entry);
    CuratorUtils.createOrSetDataForPath(_curatorFramework, path, data);
    return putObject(putObjectRequest);
  }

  public void deleteConsistentObject(DeleteObjectRequest deleteObjectRequest) throws Exception {
    String bucketName = deleteObjectRequest.getBucketName();
    String key = deleteObjectRequest.getKey();
    deleteEntry(bucketName, key);
    deleteObject(deleteObjectRequest);
  }

  public DeleteObjectsResult deleteConsistentObjects(DeleteObjectsRequest deleteObjectsRequest) throws Exception {
    String bucketName = deleteObjectsRequest.getBucketName();
    for (KeyVersion key : deleteObjectsRequest.getKeys()) {
      deleteEntry(bucketName, key.getKey());
    }
    return deleteObjects(deleteObjectsRequest);
  }

  public void deleteConsistentObject(String bucketName, String key) throws Exception {
    deleteConsistentObject(new DeleteObjectRequest(bucketName, key));
  }

  public PutObjectResult putConsistentObject(String bucketName, String key, File file) throws Exception {
    return putConsistentObject(new PutObjectRequest(bucketName, key, file));
  }

  public PutObjectResult putConsistentObject(String bucketName, String key, InputStream input, ObjectMetadata metadata)
      throws Exception {
    return putConsistentObject(new PutObjectRequest(bucketName, key, input, metadata));
  }

  public PutObjectResult putConsistentObject(String bucketName, String key, String content) throws Exception {
    byte[] bs = content.getBytes(StringUtils.UTF8);
    try (InputStream input = new ByteArrayInputStream(bs)) {
      ObjectMetadata metadata = new ObjectMetadata();
      metadata.setContentLength(bs.length);
      return putConsistentObject(new PutObjectRequest(bucketName, key, input, metadata));
    }
  }

  public String getConsistentObjectAsString(String bucketName, String key) throws Exception {
    S3Object s3Object = getConsistentObject(bucketName, key);
    try (S3ObjectInputStream input = s3Object.getObjectContent()) {
      return IOUtils.toString(input);
    }
  }

  public ObjectMetadata getConsistentObject(GetObjectRequest req, File dest) throws Exception {
    S3Object s3Object = getConsistentObject(req);
    try (S3ObjectInputStream input = s3Object.getObjectContent()) {
      try (OutputStream output = new BufferedOutputStream(new FileOutputStream(dest))) {
        IOUtils.copy(input, output);
      }
    }
    return s3Object.getObjectMetadata();
  }

  public S3Object getConsistentObject(String bucketName, String key) throws Exception {
    return getConsistentObject(new GetObjectRequest(bucketName, key));
  }

  private String getConsistencyPath(String bucketName, String key) {
    int hashCode = bucketName.hashCode();
    hashCode += key.hashCode();
    return getPrefix() + "/" + bucketName + "__" + key.replace("/", "__") + "__" + toString(hashCode);
  }

  private void deleteEntry(String bucketName, String key) throws Exception {
    String consistencyPath = getConsistencyPath(bucketName, key);
    if (_curatorFramework.checkExists()
                         .forPath(consistencyPath) != null) {
      _curatorFramework.delete()
                       .forPath(consistencyPath);
    }
  }

  private String getPrefix() {
    if (_zkPrefix == null) {
      return "/";
    }
    return _zkPrefix;
  }

  private String toString(int hashCode) {
    long h = (long) hashCode & 0xFFFFFFFF;
    return Long.toString(h, Character.MAX_RADIX);
  }

}
