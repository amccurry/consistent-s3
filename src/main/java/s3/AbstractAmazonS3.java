package s3;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;
import java.util.List;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.AmazonWebServiceRequest;
import com.amazonaws.HttpMethod;
import com.amazonaws.SdkClientException;
import com.amazonaws.regions.Region;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.S3ClientOptions;
import com.amazonaws.services.s3.S3ResponseMetadata;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.services.s3.model.analytics.AnalyticsConfiguration;
import com.amazonaws.services.s3.model.inventory.InventoryConfiguration;
import com.amazonaws.services.s3.model.metrics.MetricsConfiguration;
import com.amazonaws.services.s3.waiters.AmazonS3Waiters;

@SuppressWarnings("deprecation")
public class AbstractAmazonS3 {

  private final AmazonS3 _client;

  public AbstractAmazonS3(AmazonS3 client) {
    _client = client;
  }

  public PutObjectResult putObject(PutObjectRequest req) {
    return _client.putObject(req);
  }

  public S3Object getObject(GetObjectRequest req) {
    return _client.getObject(req);
  }

  public ObjectMetadata getObject(GetObjectRequest req, File dest) {
    return _client.getObject(req, dest);
  }

  public CompleteMultipartUploadResult completeMultipartUpload(CompleteMultipartUploadRequest req) {
    return _client.completeMultipartUpload(req);
  }

  public InitiateMultipartUploadResult initiateMultipartUpload(InitiateMultipartUploadRequest req) {
    return _client.initiateMultipartUpload(req);
  }

  public UploadPartResult uploadPart(UploadPartRequest req) {
    return _client.uploadPart(req);
  }

  public CopyPartResult copyPart(CopyPartRequest req) {
    return _client.copyPart(req);
  }

  public void setEndpoint(String endpoint) {
    _client.setEndpoint(endpoint);
  }

  public void setRegion(Region region) throws IllegalArgumentException {
    _client.setRegion(region);
  }

  public void setS3ClientOptions(S3ClientOptions clientOptions) {
    _client.setS3ClientOptions(clientOptions);
  }

  public void changeObjectStorageClass(String bucketName, String key, StorageClass newStorageClass)
      throws SdkClientException, AmazonServiceException {
    _client.changeObjectStorageClass(bucketName, key, newStorageClass);
  }

  public void setObjectRedirectLocation(String bucketName, String key, String newRedirectLocation)
      throws SdkClientException, AmazonServiceException {
    _client.setObjectRedirectLocation(bucketName, key, newRedirectLocation);
  }

  public ObjectListing listObjects(String bucketName) throws SdkClientException, AmazonServiceException {
    return _client.listObjects(bucketName);
  }

  public ObjectListing listObjects(String bucketName, String prefix) throws SdkClientException, AmazonServiceException {
    return _client.listObjects(bucketName, prefix);
  }

  public ObjectListing listObjects(ListObjectsRequest listObjectsRequest)
      throws SdkClientException, AmazonServiceException {
    return _client.listObjects(listObjectsRequest);
  }

  public ListObjectsV2Result listObjectsV2(String bucketName) throws SdkClientException, AmazonServiceException {
    return _client.listObjectsV2(bucketName);
  }

  public ListObjectsV2Result listObjectsV2(String bucketName, String prefix)
      throws SdkClientException, AmazonServiceException {
    return _client.listObjectsV2(bucketName, prefix);
  }

  public ListObjectsV2Result listObjectsV2(ListObjectsV2Request listObjectsV2Request)
      throws SdkClientException, AmazonServiceException {
    return _client.listObjectsV2(listObjectsV2Request);
  }

  public ObjectListing listNextBatchOfObjects(ObjectListing previousObjectListing)
      throws SdkClientException, AmazonServiceException {
    return _client.listNextBatchOfObjects(previousObjectListing);
  }

  public ObjectListing listNextBatchOfObjects(ListNextBatchOfObjectsRequest listNextBatchOfObjectsRequest)
      throws SdkClientException, AmazonServiceException {
    return _client.listNextBatchOfObjects(listNextBatchOfObjectsRequest);
  }

  public VersionListing listVersions(String bucketName, String prefix)
      throws SdkClientException, AmazonServiceException {
    return _client.listVersions(bucketName, prefix);
  }

  public VersionListing listNextBatchOfVersions(VersionListing previousVersionListing)
      throws SdkClientException, AmazonServiceException {
    return _client.listNextBatchOfVersions(previousVersionListing);
  }

  public VersionListing listNextBatchOfVersions(ListNextBatchOfVersionsRequest listNextBatchOfVersionsRequest)
      throws SdkClientException, AmazonServiceException {
    return _client.listNextBatchOfVersions(listNextBatchOfVersionsRequest);
  }

  public VersionListing listVersions(String bucketName, String prefix, String keyMarker, String versionIdMarker,
      String delimiter, Integer maxResults) throws SdkClientException, AmazonServiceException {
    return _client.listVersions(bucketName, prefix, keyMarker, versionIdMarker, delimiter, maxResults);
  }

  public VersionListing listVersions(ListVersionsRequest listVersionsRequest)
      throws SdkClientException, AmazonServiceException {
    return _client.listVersions(listVersionsRequest);
  }

  public Owner getS3AccountOwner() throws SdkClientException, AmazonServiceException {
    return _client.getS3AccountOwner();
  }

  public Owner getS3AccountOwner(GetS3AccountOwnerRequest getS3AccountOwnerRequest)
      throws SdkClientException, AmazonServiceException {
    return _client.getS3AccountOwner(getS3AccountOwnerRequest);
  }

  public boolean doesBucketExist(String bucketName) throws SdkClientException, AmazonServiceException {
    return _client.doesBucketExist(bucketName);
  }

  public boolean doesBucketExistV2(String bucketName) throws SdkClientException, AmazonServiceException {
    return _client.doesBucketExistV2(bucketName);
  }

  public HeadBucketResult headBucket(HeadBucketRequest headBucketRequest)
      throws SdkClientException, AmazonServiceException {
    return _client.headBucket(headBucketRequest);
  }

  public List<Bucket> listBuckets() throws SdkClientException, AmazonServiceException {
    return _client.listBuckets();
  }

  public List<Bucket> listBuckets(ListBucketsRequest listBucketsRequest)
      throws SdkClientException, AmazonServiceException {
    return _client.listBuckets(listBucketsRequest);
  }

  public String getBucketLocation(String bucketName) throws SdkClientException, AmazonServiceException {
    return _client.getBucketLocation(bucketName);
  }

  public String getBucketLocation(GetBucketLocationRequest getBucketLocationRequest)
      throws SdkClientException, AmazonServiceException {
    return _client.getBucketLocation(getBucketLocationRequest);
  }

  public Bucket createBucket(CreateBucketRequest createBucketRequest)
      throws SdkClientException, AmazonServiceException {
    return _client.createBucket(createBucketRequest);
  }

  public Bucket createBucket(String bucketName) throws SdkClientException, AmazonServiceException {
    return _client.createBucket(bucketName);
  }

  public Bucket createBucket(String bucketName, com.amazonaws.services.s3.model.Region region)
      throws SdkClientException, AmazonServiceException {
    return _client.createBucket(bucketName, region);
  }

  public Bucket createBucket(String bucketName, String region) throws SdkClientException, AmazonServiceException {
    return _client.createBucket(bucketName, region);
  }

  public AccessControlList getObjectAcl(String bucketName, String key)
      throws SdkClientException, AmazonServiceException {
    return _client.getObjectAcl(bucketName, key);
  }

  public AccessControlList getObjectAcl(String bucketName, String key, String versionId)
      throws SdkClientException, AmazonServiceException {
    return _client.getObjectAcl(bucketName, key, versionId);
  }

  public AccessControlList getObjectAcl(GetObjectAclRequest getObjectAclRequest)
      throws SdkClientException, AmazonServiceException {
    return _client.getObjectAcl(getObjectAclRequest);
  }

  public void setObjectAcl(String bucketName, String key, AccessControlList acl)
      throws SdkClientException, AmazonServiceException {
    _client.setObjectAcl(bucketName, key, acl);
  }

  public void setObjectAcl(String bucketName, String key, CannedAccessControlList acl)
      throws SdkClientException, AmazonServiceException {
    _client.setObjectAcl(bucketName, key, acl);
  }

  public void setObjectAcl(String bucketName, String key, String versionId, AccessControlList acl)
      throws SdkClientException, AmazonServiceException {
    _client.setObjectAcl(bucketName, key, versionId, acl);
  }

  public void setObjectAcl(String bucketName, String key, String versionId, CannedAccessControlList acl)
      throws SdkClientException, AmazonServiceException {
    _client.setObjectAcl(bucketName, key, versionId, acl);
  }

  public void setObjectAcl(SetObjectAclRequest setObjectAclRequest) throws SdkClientException, AmazonServiceException {
    _client.setObjectAcl(setObjectAclRequest);
  }

  public AccessControlList getBucketAcl(String bucketName) throws SdkClientException, AmazonServiceException {
    return _client.getBucketAcl(bucketName);
  }

  public void setBucketAcl(SetBucketAclRequest setBucketAclRequest) throws SdkClientException, AmazonServiceException {
    _client.setBucketAcl(setBucketAclRequest);
  }

  public AccessControlList getBucketAcl(GetBucketAclRequest getBucketAclRequest)
      throws SdkClientException, AmazonServiceException {
    return _client.getBucketAcl(getBucketAclRequest);
  }

  public void setBucketAcl(String bucketName, AccessControlList acl) throws SdkClientException, AmazonServiceException {
    _client.setBucketAcl(bucketName, acl);
  }

  public void setBucketAcl(String bucketName, CannedAccessControlList acl)
      throws SdkClientException, AmazonServiceException {
    _client.setBucketAcl(bucketName, acl);
  }

  public ObjectMetadata getObjectMetadata(String bucketName, String key)
      throws SdkClientException, AmazonServiceException {
    return _client.getObjectMetadata(bucketName, key);
  }

  public ObjectMetadata getObjectMetadata(GetObjectMetadataRequest getObjectMetadataRequest)
      throws SdkClientException, AmazonServiceException {
    return _client.getObjectMetadata(getObjectMetadataRequest);
  }

  public S3Object getObject(String bucketName, String key) throws SdkClientException, AmazonServiceException {
    return _client.getObject(bucketName, key);
  }

  public String getObjectAsString(String bucketName, String key) throws AmazonServiceException, SdkClientException {
    return _client.getObjectAsString(bucketName, key);
  }

  public GetObjectTaggingResult getObjectTagging(GetObjectTaggingRequest getObjectTaggingRequest) {
    return _client.getObjectTagging(getObjectTaggingRequest);
  }

  public SetObjectTaggingResult setObjectTagging(SetObjectTaggingRequest setObjectTaggingRequest) {
    return _client.setObjectTagging(setObjectTaggingRequest);
  }

  public DeleteObjectTaggingResult deleteObjectTagging(DeleteObjectTaggingRequest deleteObjectTaggingRequest) {
    return _client.deleteObjectTagging(deleteObjectTaggingRequest);
  }

  public void deleteBucket(DeleteBucketRequest deleteBucketRequest) throws SdkClientException, AmazonServiceException {
    _client.deleteBucket(deleteBucketRequest);
  }

  public void deleteBucket(String bucketName) throws SdkClientException, AmazonServiceException {
    _client.deleteBucket(bucketName);
  }

  public PutObjectResult putObject(String bucketName, String key, File file)
      throws SdkClientException, AmazonServiceException {
    return _client.putObject(bucketName, key, file);
  }

  public PutObjectResult putObject(String bucketName, String key, InputStream input, ObjectMetadata metadata)
      throws SdkClientException, AmazonServiceException {
    return _client.putObject(bucketName, key, input, metadata);
  }

  public PutObjectResult putObject(String bucketName, String key, String content)
      throws AmazonServiceException, SdkClientException {
    return _client.putObject(bucketName, key, content);
  }

  public CopyObjectResult copyObject(String sourceBucketName, String sourceKey, String destinationBucketName,
      String destinationKey) throws SdkClientException, AmazonServiceException {
    return _client.copyObject(sourceBucketName, sourceKey, destinationBucketName, destinationKey);
  }

  public CopyObjectResult copyObject(CopyObjectRequest copyObjectRequest)
      throws SdkClientException, AmazonServiceException {
    return _client.copyObject(copyObjectRequest);
  }

  public void deleteObject(String bucketName, String key) throws SdkClientException, AmazonServiceException {
    _client.deleteObject(bucketName, key);
  }

  public void deleteObject(DeleteObjectRequest deleteObjectRequest) throws SdkClientException, AmazonServiceException {
    _client.deleteObject(deleteObjectRequest);
  }

  public DeleteObjectsResult deleteObjects(DeleteObjectsRequest deleteObjectsRequest)
      throws SdkClientException, AmazonServiceException {
    return _client.deleteObjects(deleteObjectsRequest);
  }

  public void deleteVersion(String bucketName, String key, String versionId)
      throws SdkClientException, AmazonServiceException {
    _client.deleteVersion(bucketName, key, versionId);
  }

  public void deleteVersion(DeleteVersionRequest deleteVersionRequest)
      throws SdkClientException, AmazonServiceException {
    _client.deleteVersion(deleteVersionRequest);
  }

  public BucketLoggingConfiguration getBucketLoggingConfiguration(String bucketName)
      throws SdkClientException, AmazonServiceException {
    return _client.getBucketLoggingConfiguration(bucketName);
  }

  public BucketLoggingConfiguration getBucketLoggingConfiguration(
      GetBucketLoggingConfigurationRequest getBucketLoggingConfigurationRequest)
      throws SdkClientException, AmazonServiceException {
    return _client.getBucketLoggingConfiguration(getBucketLoggingConfigurationRequest);
  }

  public void setBucketLoggingConfiguration(SetBucketLoggingConfigurationRequest setBucketLoggingConfigurationRequest)
      throws SdkClientException, AmazonServiceException {
    _client.setBucketLoggingConfiguration(setBucketLoggingConfigurationRequest);
  }

  public BucketVersioningConfiguration getBucketVersioningConfiguration(String bucketName)
      throws SdkClientException, AmazonServiceException {
    return _client.getBucketVersioningConfiguration(bucketName);
  }

  public BucketVersioningConfiguration getBucketVersioningConfiguration(
      GetBucketVersioningConfigurationRequest getBucketVersioningConfigurationRequest)
      throws SdkClientException, AmazonServiceException {
    return _client.getBucketVersioningConfiguration(getBucketVersioningConfigurationRequest);
  }

  public void setBucketVersioningConfiguration(
      SetBucketVersioningConfigurationRequest setBucketVersioningConfigurationRequest)
      throws SdkClientException, AmazonServiceException {
    _client.setBucketVersioningConfiguration(setBucketVersioningConfigurationRequest);
  }

  public BucketLifecycleConfiguration getBucketLifecycleConfiguration(String bucketName) {
    return _client.getBucketLifecycleConfiguration(bucketName);
  }

  public BucketLifecycleConfiguration getBucketLifecycleConfiguration(
      GetBucketLifecycleConfigurationRequest getBucketLifecycleConfigurationRequest) {
    return _client.getBucketLifecycleConfiguration(getBucketLifecycleConfigurationRequest);
  }

  public void setBucketLifecycleConfiguration(String bucketName,
      BucketLifecycleConfiguration bucketLifecycleConfiguration) {
    _client.setBucketLifecycleConfiguration(bucketName, bucketLifecycleConfiguration);
  }

  public void setBucketLifecycleConfiguration(
      SetBucketLifecycleConfigurationRequest setBucketLifecycleConfigurationRequest) {
    _client.setBucketLifecycleConfiguration(setBucketLifecycleConfigurationRequest);
  }

  public void deleteBucketLifecycleConfiguration(String bucketName) {
    _client.deleteBucketLifecycleConfiguration(bucketName);
  }

  public void deleteBucketLifecycleConfiguration(
      DeleteBucketLifecycleConfigurationRequest deleteBucketLifecycleConfigurationRequest) {
    _client.deleteBucketLifecycleConfiguration(deleteBucketLifecycleConfigurationRequest);
  }

  public BucketCrossOriginConfiguration getBucketCrossOriginConfiguration(String bucketName) {
    return _client.getBucketCrossOriginConfiguration(bucketName);
  }

  public BucketCrossOriginConfiguration getBucketCrossOriginConfiguration(
      GetBucketCrossOriginConfigurationRequest getBucketCrossOriginConfigurationRequest) {
    return _client.getBucketCrossOriginConfiguration(getBucketCrossOriginConfigurationRequest);
  }

  public void setBucketCrossOriginConfiguration(String bucketName,
      BucketCrossOriginConfiguration bucketCrossOriginConfiguration) {
    _client.setBucketCrossOriginConfiguration(bucketName, bucketCrossOriginConfiguration);
  }

  public void setBucketCrossOriginConfiguration(
      SetBucketCrossOriginConfigurationRequest setBucketCrossOriginConfigurationRequest) {
    _client.setBucketCrossOriginConfiguration(setBucketCrossOriginConfigurationRequest);
  }

  public void deleteBucketCrossOriginConfiguration(String bucketName) {
    _client.deleteBucketCrossOriginConfiguration(bucketName);
  }

  public void deleteBucketCrossOriginConfiguration(
      DeleteBucketCrossOriginConfigurationRequest deleteBucketCrossOriginConfigurationRequest) {
    _client.deleteBucketCrossOriginConfiguration(deleteBucketCrossOriginConfigurationRequest);
  }

  public BucketTaggingConfiguration getBucketTaggingConfiguration(String bucketName) {
    return _client.getBucketTaggingConfiguration(bucketName);
  }

  public BucketTaggingConfiguration getBucketTaggingConfiguration(
      GetBucketTaggingConfigurationRequest getBucketTaggingConfigurationRequest) {
    return _client.getBucketTaggingConfiguration(getBucketTaggingConfigurationRequest);
  }

  public void setBucketTaggingConfiguration(String bucketName, BucketTaggingConfiguration bucketTaggingConfiguration) {
    _client.setBucketTaggingConfiguration(bucketName, bucketTaggingConfiguration);
  }

  public void setBucketTaggingConfiguration(SetBucketTaggingConfigurationRequest setBucketTaggingConfigurationRequest) {
    _client.setBucketTaggingConfiguration(setBucketTaggingConfigurationRequest);
  }

  public void deleteBucketTaggingConfiguration(String bucketName) {
    _client.deleteBucketTaggingConfiguration(bucketName);
  }

  public void deleteBucketTaggingConfiguration(
      DeleteBucketTaggingConfigurationRequest deleteBucketTaggingConfigurationRequest) {
    _client.deleteBucketTaggingConfiguration(deleteBucketTaggingConfigurationRequest);
  }

  public BucketNotificationConfiguration getBucketNotificationConfiguration(String bucketName)
      throws SdkClientException, AmazonServiceException {
    return _client.getBucketNotificationConfiguration(bucketName);
  }

  public BucketNotificationConfiguration getBucketNotificationConfiguration(
      GetBucketNotificationConfigurationRequest getBucketNotificationConfigurationRequest)
      throws SdkClientException, AmazonServiceException {
    return _client.getBucketNotificationConfiguration(getBucketNotificationConfigurationRequest);
  }

  public void setBucketNotificationConfiguration(
      SetBucketNotificationConfigurationRequest setBucketNotificationConfigurationRequest)
      throws SdkClientException, AmazonServiceException {
    _client.setBucketNotificationConfiguration(setBucketNotificationConfigurationRequest);
  }

  public void setBucketNotificationConfiguration(String bucketName,
      BucketNotificationConfiguration bucketNotificationConfiguration)
      throws SdkClientException, AmazonServiceException {
    _client.setBucketNotificationConfiguration(bucketName, bucketNotificationConfiguration);
  }

  public BucketWebsiteConfiguration getBucketWebsiteConfiguration(String bucketName)
      throws SdkClientException, AmazonServiceException {
    return _client.getBucketWebsiteConfiguration(bucketName);
  }

  public BucketWebsiteConfiguration getBucketWebsiteConfiguration(
      GetBucketWebsiteConfigurationRequest getBucketWebsiteConfigurationRequest)
      throws SdkClientException, AmazonServiceException {
    return _client.getBucketWebsiteConfiguration(getBucketWebsiteConfigurationRequest);
  }

  public void setBucketWebsiteConfiguration(String bucketName, BucketWebsiteConfiguration configuration)
      throws SdkClientException, AmazonServiceException {
    _client.setBucketWebsiteConfiguration(bucketName, configuration);
  }

  public void setBucketWebsiteConfiguration(SetBucketWebsiteConfigurationRequest setBucketWebsiteConfigurationRequest)
      throws SdkClientException, AmazonServiceException {
    _client.setBucketWebsiteConfiguration(setBucketWebsiteConfigurationRequest);
  }

  public void deleteBucketWebsiteConfiguration(String bucketName) throws SdkClientException, AmazonServiceException {
    _client.deleteBucketWebsiteConfiguration(bucketName);
  }

  public void deleteBucketWebsiteConfiguration(
      DeleteBucketWebsiteConfigurationRequest deleteBucketWebsiteConfigurationRequest)
      throws SdkClientException, AmazonServiceException {
    _client.deleteBucketWebsiteConfiguration(deleteBucketWebsiteConfigurationRequest);
  }

  public BucketPolicy getBucketPolicy(String bucketName) throws SdkClientException, AmazonServiceException {
    return _client.getBucketPolicy(bucketName);
  }

  public BucketPolicy getBucketPolicy(GetBucketPolicyRequest getBucketPolicyRequest)
      throws SdkClientException, AmazonServiceException {
    return _client.getBucketPolicy(getBucketPolicyRequest);
  }

  public void setBucketPolicy(String bucketName, String policyText) throws SdkClientException, AmazonServiceException {
    _client.setBucketPolicy(bucketName, policyText);
  }

  public void setBucketPolicy(SetBucketPolicyRequest setBucketPolicyRequest)
      throws SdkClientException, AmazonServiceException {
    _client.setBucketPolicy(setBucketPolicyRequest);
  }

  public void deleteBucketPolicy(String bucketName) throws SdkClientException, AmazonServiceException {
    _client.deleteBucketPolicy(bucketName);
  }

  public void deleteBucketPolicy(DeleteBucketPolicyRequest deleteBucketPolicyRequest)
      throws SdkClientException, AmazonServiceException {
    _client.deleteBucketPolicy(deleteBucketPolicyRequest);
  }

  public URL generatePresignedUrl(String bucketName, String key, Date expiration) throws SdkClientException {
    return _client.generatePresignedUrl(bucketName, key, expiration);
  }

  public URL generatePresignedUrl(String bucketName, String key, Date expiration, HttpMethod method)
      throws SdkClientException {
    return _client.generatePresignedUrl(bucketName, key, expiration, method);
  }

  public URL generatePresignedUrl(GeneratePresignedUrlRequest generatePresignedUrlRequest) throws SdkClientException {
    return _client.generatePresignedUrl(generatePresignedUrlRequest);
  }

  public PartListing listParts(ListPartsRequest request) throws SdkClientException, AmazonServiceException {
    return _client.listParts(request);
  }

  public void abortMultipartUpload(AbortMultipartUploadRequest request)
      throws SdkClientException, AmazonServiceException {
    _client.abortMultipartUpload(request);
  }

  public MultipartUploadListing listMultipartUploads(ListMultipartUploadsRequest request)
      throws SdkClientException, AmazonServiceException {
    return _client.listMultipartUploads(request);
  }

  public S3ResponseMetadata getCachedResponseMetadata(AmazonWebServiceRequest request) {
    return _client.getCachedResponseMetadata(request);
  }

  public void restoreObject(RestoreObjectRequest request) throws AmazonServiceException {
    _client.restoreObject(request);
  }

  public RestoreObjectResult restoreObjectV2(RestoreObjectRequest request) throws AmazonServiceException {
    return _client.restoreObjectV2(request);
  }

  public void restoreObject(String bucketName, String key, int expirationInDays) throws AmazonServiceException {
    _client.restoreObject(bucketName, key, expirationInDays);
  }

  public void enableRequesterPays(String bucketName) throws AmazonServiceException, SdkClientException {
    _client.enableRequesterPays(bucketName);
  }

  public void disableRequesterPays(String bucketName) throws AmazonServiceException, SdkClientException {
    _client.disableRequesterPays(bucketName);
  }

  public boolean isRequesterPaysEnabled(String bucketName) throws AmazonServiceException, SdkClientException {
    return _client.isRequesterPaysEnabled(bucketName);
  }

  public void setBucketReplicationConfiguration(String bucketName, BucketReplicationConfiguration configuration)
      throws AmazonServiceException, SdkClientException {
    _client.setBucketReplicationConfiguration(bucketName, configuration);
  }

  public void setBucketReplicationConfiguration(
      SetBucketReplicationConfigurationRequest setBucketReplicationConfigurationRequest)
      throws AmazonServiceException, SdkClientException {
    _client.setBucketReplicationConfiguration(setBucketReplicationConfigurationRequest);
  }

  public BucketReplicationConfiguration getBucketReplicationConfiguration(String bucketName)
      throws AmazonServiceException, SdkClientException {
    return _client.getBucketReplicationConfiguration(bucketName);
  }

  public BucketReplicationConfiguration getBucketReplicationConfiguration(
      GetBucketReplicationConfigurationRequest getBucketReplicationConfigurationRequest)
      throws AmazonServiceException, SdkClientException {
    return _client.getBucketReplicationConfiguration(getBucketReplicationConfigurationRequest);
  }

  public void deleteBucketReplicationConfiguration(String bucketName)
      throws AmazonServiceException, SdkClientException {
    _client.deleteBucketReplicationConfiguration(bucketName);
  }

  public void deleteBucketReplicationConfiguration(DeleteBucketReplicationConfigurationRequest request)
      throws AmazonServiceException, SdkClientException {
    _client.deleteBucketReplicationConfiguration(request);
  }

  public boolean doesObjectExist(String bucketName, String objectName)
      throws AmazonServiceException, SdkClientException {
    return _client.doesObjectExist(bucketName, objectName);
  }

  public BucketAccelerateConfiguration getBucketAccelerateConfiguration(String bucketName)
      throws AmazonServiceException, SdkClientException {
    return _client.getBucketAccelerateConfiguration(bucketName);
  }

  public BucketAccelerateConfiguration getBucketAccelerateConfiguration(
      GetBucketAccelerateConfigurationRequest getBucketAccelerateConfigurationRequest)
      throws AmazonServiceException, SdkClientException {
    return _client.getBucketAccelerateConfiguration(getBucketAccelerateConfigurationRequest);
  }

  public void setBucketAccelerateConfiguration(String bucketName, BucketAccelerateConfiguration accelerateConfiguration)
      throws AmazonServiceException, SdkClientException {
    _client.setBucketAccelerateConfiguration(bucketName, accelerateConfiguration);
  }

  public void setBucketAccelerateConfiguration(
      SetBucketAccelerateConfigurationRequest setBucketAccelerateConfigurationRequest)
      throws AmazonServiceException, SdkClientException {
    _client.setBucketAccelerateConfiguration(setBucketAccelerateConfigurationRequest);
  }

  public DeleteBucketMetricsConfigurationResult deleteBucketMetricsConfiguration(String bucketName, String id)
      throws AmazonServiceException, SdkClientException {
    return _client.deleteBucketMetricsConfiguration(bucketName, id);
  }

  public DeleteBucketMetricsConfigurationResult deleteBucketMetricsConfiguration(
      DeleteBucketMetricsConfigurationRequest deleteBucketMetricsConfigurationRequest)
      throws AmazonServiceException, SdkClientException {
    return _client.deleteBucketMetricsConfiguration(deleteBucketMetricsConfigurationRequest);
  }

  public GetBucketMetricsConfigurationResult getBucketMetricsConfiguration(String bucketName, String id)
      throws AmazonServiceException, SdkClientException {
    return _client.getBucketMetricsConfiguration(bucketName, id);
  }

  public GetBucketMetricsConfigurationResult getBucketMetricsConfiguration(
      GetBucketMetricsConfigurationRequest getBucketMetricsConfigurationRequest)
      throws AmazonServiceException, SdkClientException {
    return _client.getBucketMetricsConfiguration(getBucketMetricsConfigurationRequest);
  }

  public SetBucketMetricsConfigurationResult setBucketMetricsConfiguration(String bucketName,
      MetricsConfiguration metricsConfiguration) throws AmazonServiceException, SdkClientException {
    return _client.setBucketMetricsConfiguration(bucketName, metricsConfiguration);
  }

  public SetBucketMetricsConfigurationResult setBucketMetricsConfiguration(
      SetBucketMetricsConfigurationRequest setBucketMetricsConfigurationRequest)
      throws AmazonServiceException, SdkClientException {
    return _client.setBucketMetricsConfiguration(setBucketMetricsConfigurationRequest);
  }

  public ListBucketMetricsConfigurationsResult listBucketMetricsConfigurations(
      ListBucketMetricsConfigurationsRequest listBucketMetricsConfigurationsRequest)
      throws AmazonServiceException, SdkClientException {
    return _client.listBucketMetricsConfigurations(listBucketMetricsConfigurationsRequest);
  }

  public DeleteBucketAnalyticsConfigurationResult deleteBucketAnalyticsConfiguration(String bucketName, String id)
      throws AmazonServiceException, SdkClientException {
    return _client.deleteBucketAnalyticsConfiguration(bucketName, id);
  }

  public DeleteBucketAnalyticsConfigurationResult deleteBucketAnalyticsConfiguration(
      DeleteBucketAnalyticsConfigurationRequest deleteBucketAnalyticsConfigurationRequest)
      throws AmazonServiceException, SdkClientException {
    return _client.deleteBucketAnalyticsConfiguration(deleteBucketAnalyticsConfigurationRequest);
  }

  public GetBucketAnalyticsConfigurationResult getBucketAnalyticsConfiguration(String bucketName, String id)
      throws AmazonServiceException, SdkClientException {
    return _client.getBucketAnalyticsConfiguration(bucketName, id);
  }

  public GetBucketAnalyticsConfigurationResult getBucketAnalyticsConfiguration(
      GetBucketAnalyticsConfigurationRequest getBucketAnalyticsConfigurationRequest)
      throws AmazonServiceException, SdkClientException {
    return _client.getBucketAnalyticsConfiguration(getBucketAnalyticsConfigurationRequest);
  }

  public SetBucketAnalyticsConfigurationResult setBucketAnalyticsConfiguration(String bucketName,
      AnalyticsConfiguration analyticsConfiguration) throws AmazonServiceException, SdkClientException {
    return _client.setBucketAnalyticsConfiguration(bucketName, analyticsConfiguration);
  }

  public SetBucketAnalyticsConfigurationResult setBucketAnalyticsConfiguration(
      SetBucketAnalyticsConfigurationRequest setBucketAnalyticsConfigurationRequest)
      throws AmazonServiceException, SdkClientException {
    return _client.setBucketAnalyticsConfiguration(setBucketAnalyticsConfigurationRequest);
  }

  public ListBucketAnalyticsConfigurationsResult listBucketAnalyticsConfigurations(
      ListBucketAnalyticsConfigurationsRequest listBucketAnalyticsConfigurationsRequest)
      throws AmazonServiceException, SdkClientException {
    return _client.listBucketAnalyticsConfigurations(listBucketAnalyticsConfigurationsRequest);
  }

  public DeleteBucketInventoryConfigurationResult deleteBucketInventoryConfiguration(String bucketName, String id)
      throws AmazonServiceException, SdkClientException {
    return _client.deleteBucketInventoryConfiguration(bucketName, id);
  }

  public DeleteBucketInventoryConfigurationResult deleteBucketInventoryConfiguration(
      DeleteBucketInventoryConfigurationRequest deleteBucketInventoryConfigurationRequest)
      throws AmazonServiceException, SdkClientException {
    return _client.deleteBucketInventoryConfiguration(deleteBucketInventoryConfigurationRequest);
  }

  public GetBucketInventoryConfigurationResult getBucketInventoryConfiguration(String bucketName, String id)
      throws AmazonServiceException, SdkClientException {
    return _client.getBucketInventoryConfiguration(bucketName, id);
  }

  public GetBucketInventoryConfigurationResult getBucketInventoryConfiguration(
      GetBucketInventoryConfigurationRequest getBucketInventoryConfigurationRequest)
      throws AmazonServiceException, SdkClientException {
    return _client.getBucketInventoryConfiguration(getBucketInventoryConfigurationRequest);
  }

  public SetBucketInventoryConfigurationResult setBucketInventoryConfiguration(String bucketName,
      InventoryConfiguration inventoryConfiguration) throws AmazonServiceException, SdkClientException {
    return _client.setBucketInventoryConfiguration(bucketName, inventoryConfiguration);
  }

  public SetBucketInventoryConfigurationResult setBucketInventoryConfiguration(
      SetBucketInventoryConfigurationRequest setBucketInventoryConfigurationRequest)
      throws AmazonServiceException, SdkClientException {
    return _client.setBucketInventoryConfiguration(setBucketInventoryConfigurationRequest);
  }

  public ListBucketInventoryConfigurationsResult listBucketInventoryConfigurations(
      ListBucketInventoryConfigurationsRequest listBucketInventoryConfigurationsRequest)
      throws AmazonServiceException, SdkClientException {
    return _client.listBucketInventoryConfigurations(listBucketInventoryConfigurationsRequest);
  }

  public DeleteBucketEncryptionResult deleteBucketEncryption(String bucketName)
      throws AmazonServiceException, SdkClientException {
    return _client.deleteBucketEncryption(bucketName);
  }

  public DeleteBucketEncryptionResult deleteBucketEncryption(DeleteBucketEncryptionRequest request)
      throws AmazonServiceException, SdkClientException {
    return _client.deleteBucketEncryption(request);
  }

  public GetBucketEncryptionResult getBucketEncryption(String bucketName)
      throws AmazonServiceException, SdkClientException {
    return _client.getBucketEncryption(bucketName);
  }

  public GetBucketEncryptionResult getBucketEncryption(GetBucketEncryptionRequest request)
      throws AmazonServiceException, SdkClientException {
    return _client.getBucketEncryption(request);
  }

  public SetBucketEncryptionResult setBucketEncryption(SetBucketEncryptionRequest setBucketEncryptionRequest)
      throws AmazonServiceException, SdkClientException {
    return _client.setBucketEncryption(setBucketEncryptionRequest);
  }

  public void shutdown() {
    _client.shutdown();
  }

  public com.amazonaws.services.s3.model.Region getRegion() {
    return _client.getRegion();
  }

  public String getRegionName() {
    return _client.getRegionName();
  }

  public URL getUrl(String bucketName, String key) {
    return _client.getUrl(bucketName, key);
  }

  public AmazonS3Waiters waiters() {
    return _client.waiters();
  }
}
