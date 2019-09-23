package consistent.s3;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

import org.apache.curator.framework.CuratorFramework;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.AmazonServiceException.ErrorType;
import com.amazonaws.SdkClientException;
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
import com.amazonaws.util.IOUtils;
import com.amazonaws.util.StringUtils;

public class ConsistentAmazonS3 extends AbstractAmazonS3 implements Closeable {

  private static final String CONSISTENT_S3_CHECK = "consistent-s3-check";

  private static final Logger LOGGER = LoggerFactory.getLogger(ConsistentAmazonS3.class);

  private static final String CONSISTENCY_KEY = "consistencyKey";
  private final CuratorFramework _curatorFramework;
  private final String _zkPrefix;
  private final long _retryDelayMs;
  private final long _s3ConsistencyMaxWaitTimeMs;
  private final int _maxConsistencyCount;
  private final AmazonS3 _client;
  private final Timer _timer;
  private final long _s3ConsistencyCheckPeriodTimeMs;

  public static ConsistentAmazonS3 create(AmazonS3 client, CuratorFramework curatorFramework) throws Exception {
    return new ConsistentAmazonS3(client, curatorFramework, ConsistentAmazonS3Config.DEFAULT);
  }

  public static ConsistentAmazonS3 create(AmazonS3 client, CuratorFramework curatorFramework,
      ConsistentAmazonS3Config config) throws Exception {
    return new ConsistentAmazonS3(client, curatorFramework, config);
  }

  private ConsistentAmazonS3(AmazonS3 client, CuratorFramework curatorFramework, ConsistentAmazonS3Config config)
      throws Exception {
    _client = client;
    _curatorFramework = curatorFramework;
    _zkPrefix = config.getZkPrefix();
    _retryDelayMs = config.getRetryDelayMs();
    _s3ConsistencyMaxWaitTimeMs = config.getS3ConsistencyMaxWaitTimeMs();
    _s3ConsistencyCheckPeriodTimeMs = config.getS3ConsistencyCheckPeriodTimeMs();
    _maxConsistencyCount = config.getMaxConsistencyCount();
    if (config.isAutoCheckEntriesEnabled()) {
      _timer = new Timer(CONSISTENT_S3_CHECK, true);
      _timer.schedule(getCheckTask(), _s3ConsistencyCheckPeriodTimeMs, _s3ConsistencyCheckPeriodTimeMs);
    } else {
      _timer = null;
    }
  }

  private TimerTask getCheckTask() {
    return new TimerTask() {
      @Override
      public void run() {
        LOGGER.info("Checking for consistent entries");
        try {
          checkOutstandingConsistencyEntries(false);
        } catch (Exception e) {
          LOGGER.error("Unknown error while trying to check for expired entries", e);
        }
      }
    };
  }

  @Override
  public void close() throws IOException {
    if (_timer != null) {
      _timer.cancel();
      _timer.purge();
    }
  }

  public void checkOutstandingConsistencyEntries(boolean waitForEntriesToBecomeConsistent) {
    List<String> list = CuratorUtils.getChildrenIfExists(_curatorFramework, getPrefix());
    if (list == null) {
      return;
    }
    for (String s : list) {
      String path = getPrefix() + "/" + s;
      ConsistencyEntry entry = getConsistencyEntry(path);
      if (entry != null) {
        ObjectMetadata objectMetadata = _client.getObjectMetadata(entry.getBucket(), entry.getKey());
        if (waitForEntriesToBecomeConsistent) {
          while (!isValid(entry, objectMetadata, true)) {
            sleep(_retryDelayMs);
          }
        } else {
          isValid(entry, objectMetadata);
        }
      }
    }
  }

  private boolean isExpired(ConsistencyEntry entry) {
    return entry.getCreatedTs() + _s3ConsistencyMaxWaitTimeMs < System.currentTimeMillis();
  }

  public List<ConsistencyEntry> getOutstandingConsistencyEntries() {
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

  @Override
  public S3Object getObject(GetObjectRequest getObjectRequest) throws SdkClientException, AmazonServiceException {
    String consistencyPath = getConsistencyPath(getObjectRequest.getBucketName(), getObjectRequest.getKey());
    while (true) {
      ConsistencyEntry entry = getConsistencyEntry(consistencyPath);
      if (entry == null) {
        // none found just fetch the object
        return _client.getObject(getObjectRequest);
      }
      if (entry.isDeleted()) {
        throw newNoKeyFound();
      }
      try {
        S3Object object = _client.getObject(getObjectRequest);
        if (isValid(entry, object.getObjectMetadata())) {
          return object;
        }
      } catch (AmazonS3Exception e) {
        if (e.getStatusCode() != 404) {
          LOGGER.error(e.getMessage(), e);
          throw e;
        }
      }
      sleep(_retryDelayMs);
    }
  }

  private AmazonServiceException newNoKeyFound() {
    AmazonServiceException exception = new AmazonServiceException("The specified key does not exist.");
    exception.setErrorCode("NoSuchKey");
    exception.setErrorType(ErrorType.Client);
    exception.setStatusCode(404);
    exception.setServiceName("Amazon S3");
    return exception;
  }

  @Override
  public PutObjectResult putObject(PutObjectRequest putObjectRequest)
      throws SdkClientException, AmazonServiceException {
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
    entry.setCreatedTs(System.currentTimeMillis());

    byte[] data = JsonUtils.toBytes(entry);
    CuratorUtils.createOrSetDataForPath(_curatorFramework, path, data);
    return _client.putObject(putObjectRequest);
  }

  @Override
  public void deleteObject(DeleteObjectRequest deleteObjectRequest) throws SdkClientException, AmazonServiceException {
    String bucketName = deleteObjectRequest.getBucketName();
    String key = deleteObjectRequest.getKey();
    deleteEntry(bucketName, key);
    _client.deleteObject(deleteObjectRequest);
  }

  @Override
  public DeleteObjectsResult deleteObjects(DeleteObjectsRequest deleteObjectsRequest)
      throws SdkClientException, AmazonServiceException {
    String bucketName = deleteObjectsRequest.getBucketName();
    for (KeyVersion key : deleteObjectsRequest.getKeys()) {
      deleteEntry(bucketName, key.getKey());
    }
    return _client.deleteObjects(deleteObjectsRequest);
  }

  @Override
  public void deleteObject(String bucketName, String key) throws SdkClientException, AmazonServiceException {
    deleteObject(new DeleteObjectRequest(bucketName, key));
  }

  @Override
  public PutObjectResult putObject(String bucketName, String key, File file)
      throws SdkClientException, AmazonServiceException {
    return putObject(new PutObjectRequest(bucketName, key, file));
  }

  @Override
  public PutObjectResult putObject(String bucketName, String key, InputStream input, ObjectMetadata metadata)
      throws SdkClientException, AmazonServiceException {
    return putObject(new PutObjectRequest(bucketName, key, input, metadata));
  }

  @Override
  public PutObjectResult putObject(String bucketName, String key, String content)
      throws SdkClientException, AmazonServiceException {
    byte[] bs = content.getBytes(StringUtils.UTF8);
    try (InputStream input = new ByteArrayInputStream(bs)) {
      ObjectMetadata metadata = new ObjectMetadata();
      metadata.setContentLength(bs.length);
      return putObject(new PutObjectRequest(bucketName, key, input, metadata));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public String getObjectAsString(String bucketName, String key) throws SdkClientException, AmazonServiceException {
    S3Object s3Object = getObject(bucketName, key);
    try (S3ObjectInputStream input = s3Object.getObjectContent()) {
      return IOUtils.toString(input);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public ObjectMetadata getObject(GetObjectRequest req, File dest) throws SdkClientException, AmazonServiceException {
    S3Object s3Object = getObject(req);
    try (S3ObjectInputStream input = s3Object.getObjectContent()) {
      try (OutputStream output = new BufferedOutputStream(new FileOutputStream(dest))) {
        IOUtils.copy(input, output);
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return s3Object.getObjectMetadata();
  }

  @Override
  public S3Object getObject(String bucketName, String key) throws SdkClientException, AmazonServiceException {
    return getObject(new GetObjectRequest(bucketName, key));
  }

  private String getConsistencyPath(ConsistencyEntry entry) {
    return getConsistencyPath(entry.getBucket(), entry.getKey());
  }

  private String getConsistencyPath(String bucketName, String key) {
    int hashCode = bucketName.hashCode();
    hashCode += key.hashCode();
    return getPrefix() + "/" + bucketName + "__" + key.replace("/", "__") + "__" + toString(hashCode);
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

  private boolean isValid(ConsistencyEntry entry, ObjectMetadata objectMetadata) {
    return isValid(entry, objectMetadata, false);
  }

  private boolean isValid(ConsistencyEntry entry, ObjectMetadata objectMetadata, boolean fullyConsistent) {
    if (isExpired(entry)) {
      LOGGER.info("Entry time expired {}", entry);
      deleteEntry(entry);
      return true;
    }
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

  private void deleteEntry(ConsistencyEntry entry) {
    String consistencyPath = getConsistencyPath(entry);
    CuratorUtils.deletePathIfExists(_curatorFramework, consistencyPath);
  }

  private void deleteEntry(String bucket, String key) {
    String consistencyPath = getConsistencyPath(bucket, key);
    CuratorUtils.deletePathIfExists(_curatorFramework, consistencyPath);
  }

  /**
   * 
   * @param entry
   * @return return true if known to be consistent, false if probably.
   * @throws Exception
   */
  private boolean markEntryAsPotentiallyConsistent(ConsistencyEntry entry) {
    String consistencyPath = getConsistencyPath(entry);
    LOGGER.debug("entry is potentially consistent {}", entry);
    while (true) {
      if (updateEntryConsistencyInfo(entry)) {
        deleteEntry(entry);
        return true;
      } else {
        if (setConsistencyEntry(entry)) {
          LOGGER.debug("setConsistencyEntry successful {}", entry);
          return false;
        }
        LOGGER.debug("setConsistencyEntry failed, refetch {}", entry);
        entry = getConsistencyEntry(consistencyPath);
        if (entry == null) {
          return true;
        }
      }
    }
  }

  private boolean updateEntryConsistencyInfo(ConsistencyEntry entry) {
    if (entry.getConsistencyCount() >= _maxConsistencyCount) {
      return true;
    }
    if (entry.getFirstConsistentViewTs() == 0) {
      entry.setConsistencyCount(1);
      entry.setFirstConsistentViewTs(System.currentTimeMillis());
    } else {
      entry.setConsistencyCount(entry.getConsistencyCount() + 1);
    }
    return false;
  }

  private boolean setConsistencyEntry(ConsistencyEntry entry) {
    LOGGER.debug("setConsistencyEntry entry {}", entry);
    String path = getConsistencyPath(entry);
    Integer version = entry.getVersion();
    byte[] data = JsonUtils.toBytes(entry);
    if (version == null) {
      // probably new
      if (CuratorUtils.createPath(_curatorFramework, path, data)) {
        entry.setStat(CuratorUtils.getStatPath(_curatorFramework, path));
        return true;
      }
    } else {
      if (CuratorUtils.setData(_curatorFramework, path, data, version)) {
        entry.setStat(CuratorUtils.getStatPath(_curatorFramework, path));
        return true;
      }
    }
    return false;
  }

  private ConsistencyEntry getConsistencyEntry(String path) {
    DataResult dataResult = CuratorUtils.getDataIfExists(_curatorFramework, path);
    if (dataResult != null) {
      ConsistencyEntry entry = JsonUtils.toObject(dataResult.getData(), ConsistencyEntry.class);
      entry.setStat(dataResult.getStat());
      return entry;
    }
    return null;
  }

  private static void sleep(long l) {
    try {
      Thread.sleep(l);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

}
