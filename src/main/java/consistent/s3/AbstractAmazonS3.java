package consistent.s3;

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

public class AbstractAmazonS3 implements AmazonS3 {

  @Override
  public void setEndpoint(String endpoint) {
    throw new RuntimeException("Not Implemented");
  }

  @Override
  public void setRegion(Region region) throws IllegalArgumentException {
    throw new RuntimeException("Not Implemented");
  }

  @Override
  public void setS3ClientOptions(S3ClientOptions clientOptions) {
    throw new RuntimeException("Not Implemented");
  }

  @Override
  public void changeObjectStorageClass(String bucketName, String key, StorageClass newStorageClass)
      throws SdkClientException, AmazonServiceException {
    throw new RuntimeException("Not Implemented");
  }

  @Override
  public void setObjectRedirectLocation(String bucketName, String key, String newRedirectLocation)
      throws SdkClientException, AmazonServiceException {
    throw new RuntimeException("Not Implemented");
  }

  @Override
  public ObjectListing listObjects(String bucketName) throws SdkClientException, AmazonServiceException {
    throw new RuntimeException("Not Implemented");
  }

  @Override
  public ObjectListing listObjects(String bucketName, String prefix) throws SdkClientException, AmazonServiceException {
    throw new RuntimeException("Not Implemented");
  }

  @Override
  public ObjectListing listObjects(ListObjectsRequest listObjectsRequest)
      throws SdkClientException, AmazonServiceException {
    throw new RuntimeException("Not Implemented");
  }

  @Override
  public ListObjectsV2Result listObjectsV2(String bucketName) throws SdkClientException, AmazonServiceException {
    throw new RuntimeException("Not Implemented");
  }

  @Override
  public ListObjectsV2Result listObjectsV2(String bucketName, String prefix)
      throws SdkClientException, AmazonServiceException {
    throw new RuntimeException("Not Implemented");
  }

  @Override
  public ListObjectsV2Result listObjectsV2(ListObjectsV2Request listObjectsV2Request)
      throws SdkClientException, AmazonServiceException {
    throw new RuntimeException("Not Implemented");
  }

  @Override
  public ObjectListing listNextBatchOfObjects(ObjectListing previousObjectListing)
      throws SdkClientException, AmazonServiceException {
    throw new RuntimeException("Not Implemented");
  }

  @Override
  public ObjectListing listNextBatchOfObjects(ListNextBatchOfObjectsRequest listNextBatchOfObjectsRequest)
      throws SdkClientException, AmazonServiceException {
    throw new RuntimeException("Not Implemented");
  }

  @Override
  public VersionListing listVersions(String bucketName, String prefix)
      throws SdkClientException, AmazonServiceException {
    throw new RuntimeException("Not Implemented");
  }

  @Override
  public VersionListing listNextBatchOfVersions(VersionListing previousVersionListing)
      throws SdkClientException, AmazonServiceException {
    throw new RuntimeException("Not Implemented");
  }

  @Override
  public VersionListing listNextBatchOfVersions(ListNextBatchOfVersionsRequest listNextBatchOfVersionsRequest)
      throws SdkClientException, AmazonServiceException {
    throw new RuntimeException("Not Implemented");
  }

  @Override
  public VersionListing listVersions(String bucketName, String prefix, String keyMarker, String versionIdMarker,
      String delimiter, Integer maxResults) throws SdkClientException, AmazonServiceException {
    throw new RuntimeException("Not Implemented");
  }

  @Override
  public VersionListing listVersions(ListVersionsRequest listVersionsRequest)
      throws SdkClientException, AmazonServiceException {
    throw new RuntimeException("Not Implemented");
  }

  @Override
  public Owner getS3AccountOwner() throws SdkClientException, AmazonServiceException {
    throw new RuntimeException("Not Implemented");
  }

  @Override
  public Owner getS3AccountOwner(GetS3AccountOwnerRequest getS3AccountOwnerRequest)
      throws SdkClientException, AmazonServiceException {
    throw new RuntimeException("Not Implemented");
  }

  @Override
  public boolean doesBucketExist(String bucketName) throws SdkClientException, AmazonServiceException {
    throw new RuntimeException("Not Implemented");
  }

  @Override
  public boolean doesBucketExistV2(String bucketName) throws SdkClientException, AmazonServiceException {
    throw new RuntimeException("Not Implemented");
  }

  @Override
  public HeadBucketResult headBucket(HeadBucketRequest headBucketRequest)
      throws SdkClientException, AmazonServiceException {
    throw new RuntimeException("Not Implemented");
  }

  @Override
  public List<Bucket> listBuckets() throws SdkClientException, AmazonServiceException {
    throw new RuntimeException("Not Implemented");
  }

  @Override
  public List<Bucket> listBuckets(ListBucketsRequest listBucketsRequest)
      throws SdkClientException, AmazonServiceException {
    throw new RuntimeException("Not Implemented");
  }

  @Override
  public String getBucketLocation(String bucketName) throws SdkClientException, AmazonServiceException {
    throw new RuntimeException("Not Implemented");
  }

  @Override
  public String getBucketLocation(GetBucketLocationRequest getBucketLocationRequest)
      throws SdkClientException, AmazonServiceException {
    throw new RuntimeException("Not Implemented");
  }

  @Override
  public Bucket createBucket(CreateBucketRequest createBucketRequest)
      throws SdkClientException, AmazonServiceException {
    throw new RuntimeException("Not Implemented");
  }

  @Override
  public Bucket createBucket(String bucketName) throws SdkClientException, AmazonServiceException {
    throw new RuntimeException("Not Implemented");
  }

  @Override
  public Bucket createBucket(String bucketName, com.amazonaws.services.s3.model.Region region)
      throws SdkClientException, AmazonServiceException {
    throw new RuntimeException("Not Implemented");
  }

  @Override
  public Bucket createBucket(String bucketName, String region) throws SdkClientException, AmazonServiceException {
    throw new RuntimeException("Not Implemented");
  }

  @Override
  public AccessControlList getObjectAcl(String bucketName, String key)
      throws SdkClientException, AmazonServiceException {
    throw new RuntimeException("Not Implemented");
  }

  @Override
  public AccessControlList getObjectAcl(String bucketName, String key, String versionId)
      throws SdkClientException, AmazonServiceException {
    throw new RuntimeException("Not Implemented");
  }

  @Override
  public AccessControlList getObjectAcl(GetObjectAclRequest getObjectAclRequest)
      throws SdkClientException, AmazonServiceException {
    throw new RuntimeException("Not Implemented");
  }

  @Override
  public void setObjectAcl(String bucketName, String key, AccessControlList acl)
      throws SdkClientException, AmazonServiceException {
    throw new RuntimeException("Not Implemented");
  }

  @Override
  public void setObjectAcl(String bucketName, String key, CannedAccessControlList acl)
      throws SdkClientException, AmazonServiceException {
    throw new RuntimeException("Not Implemented");
  }

  @Override
  public void setObjectAcl(String bucketName, String key, String versionId, AccessControlList acl)
      throws SdkClientException, AmazonServiceException {
    throw new RuntimeException("Not Implemented");
  }

  @Override
  public void setObjectAcl(String bucketName, String key, String versionId, CannedAccessControlList acl)
      throws SdkClientException, AmazonServiceException {
    throw new RuntimeException("Not Implemented");
  }

  @Override
  public void setObjectAcl(SetObjectAclRequest setObjectAclRequest) throws SdkClientException, AmazonServiceException {
    throw new RuntimeException("Not Implemented");
  }

  @Override
  public AccessControlList getBucketAcl(String bucketName) throws SdkClientException, AmazonServiceException {
    throw new RuntimeException("Not Implemented");
  }

  @Override
  public void setBucketAcl(SetBucketAclRequest setBucketAclRequest) throws SdkClientException, AmazonServiceException {
    throw new RuntimeException("Not Implemented");
  }

  @Override
  public AccessControlList getBucketAcl(GetBucketAclRequest getBucketAclRequest)
      throws SdkClientException, AmazonServiceException {
    throw new RuntimeException("Not Implemented");
  }

  @Override
  public void setBucketAcl(String bucketName, AccessControlList acl) throws SdkClientException, AmazonServiceException {
    throw new RuntimeException("Not Implemented");
  }

  @Override
  public void setBucketAcl(String bucketName, CannedAccessControlList acl)
      throws SdkClientException, AmazonServiceException {
    throw new RuntimeException("Not Implemented");
  }

  @Override
  public ObjectMetadata getObjectMetadata(String bucketName, String key)
      throws SdkClientException, AmazonServiceException {
    throw new RuntimeException("Not Implemented");
  }

  @Override
  public ObjectMetadata getObjectMetadata(GetObjectMetadataRequest getObjectMetadataRequest)
      throws SdkClientException, AmazonServiceException {
    throw new RuntimeException("Not Implemented");
  }

  @Override
  public S3Object getObject(String bucketName, String key) throws SdkClientException, AmazonServiceException {
    throw new RuntimeException("Not Implemented");
  }

  @Override
  public S3Object getObject(GetObjectRequest getObjectRequest) throws SdkClientException, AmazonServiceException {
    throw new RuntimeException("Not Implemented");
  }

  @Override
  public ObjectMetadata getObject(GetObjectRequest getObjectRequest, File destinationFile)
      throws SdkClientException, AmazonServiceException {
    throw new RuntimeException("Not Implemented");
  }

  @Override
  public String getObjectAsString(String bucketName, String key) throws AmazonServiceException, SdkClientException {
    throw new RuntimeException("Not Implemented");
  }

  @Override
  public GetObjectTaggingResult getObjectTagging(GetObjectTaggingRequest getObjectTaggingRequest) {
    throw new RuntimeException("Not Implemented");
  }

  @Override
  public SetObjectTaggingResult setObjectTagging(SetObjectTaggingRequest setObjectTaggingRequest) {
    throw new RuntimeException("Not Implemented");
  }

  @Override
  public DeleteObjectTaggingResult deleteObjectTagging(DeleteObjectTaggingRequest deleteObjectTaggingRequest) {
    throw new RuntimeException("Not Implemented");
  }

  @Override
  public void deleteBucket(DeleteBucketRequest deleteBucketRequest) throws SdkClientException, AmazonServiceException {
    throw new RuntimeException("Not Implemented");
  }

  @Override
  public void deleteBucket(String bucketName) throws SdkClientException, AmazonServiceException {
    throw new RuntimeException("Not Implemented");
  }

  @Override
  public PutObjectResult putObject(PutObjectRequest putObjectRequest)
      throws SdkClientException, AmazonServiceException {
    throw new RuntimeException("Not Implemented");
  }

  @Override
  public PutObjectResult putObject(String bucketName, String key, File file)
      throws SdkClientException, AmazonServiceException {
    throw new RuntimeException("Not Implemented");
  }

  @Override
  public PutObjectResult putObject(String bucketName, String key, InputStream input, ObjectMetadata metadata)
      throws SdkClientException, AmazonServiceException {
    throw new RuntimeException("Not Implemented");
  }

  @Override
  public PutObjectResult putObject(String bucketName, String key, String content)
      throws AmazonServiceException, SdkClientException {
    throw new RuntimeException("Not Implemented");
  }

  @Override
  public CopyObjectResult copyObject(String sourceBucketName, String sourceKey, String destinationBucketName,
      String destinationKey) throws SdkClientException, AmazonServiceException {
    throw new RuntimeException("Not Implemented");
  }

  @Override
  public CopyObjectResult copyObject(CopyObjectRequest copyObjectRequest)
      throws SdkClientException, AmazonServiceException {
    throw new RuntimeException("Not Implemented");
  }

  @Override
  public CopyPartResult copyPart(CopyPartRequest copyPartRequest) throws SdkClientException, AmazonServiceException {
    throw new RuntimeException("Not Implemented");
  }

  @Override
  public void deleteObject(String bucketName, String key) throws SdkClientException, AmazonServiceException {
    throw new RuntimeException("Not Implemented");
  }

  @Override
  public void deleteObject(DeleteObjectRequest deleteObjectRequest) throws SdkClientException, AmazonServiceException {
    throw new RuntimeException("Not Implemented");
  }

  @Override
  public DeleteObjectsResult deleteObjects(DeleteObjectsRequest deleteObjectsRequest)
      throws SdkClientException, AmazonServiceException {
    throw new RuntimeException("Not Implemented");
  }

  @Override
  public void deleteVersion(String bucketName, String key, String versionId)
      throws SdkClientException, AmazonServiceException {
    throw new RuntimeException("Not Implemented");
  }

  @Override
  public void deleteVersion(DeleteVersionRequest deleteVersionRequest)
      throws SdkClientException, AmazonServiceException {
    throw new RuntimeException("Not Implemented");
  }

  @Override
  public BucketLoggingConfiguration getBucketLoggingConfiguration(String bucketName)
      throws SdkClientException, AmazonServiceException {
    throw new RuntimeException("Not Implemented");
  }

  @Override
  public BucketLoggingConfiguration getBucketLoggingConfiguration(
      GetBucketLoggingConfigurationRequest getBucketLoggingConfigurationRequest)
      throws SdkClientException, AmazonServiceException {
    throw new RuntimeException("Not Implemented");
  }

  @Override
  public void setBucketLoggingConfiguration(SetBucketLoggingConfigurationRequest setBucketLoggingConfigurationRequest)
      throws SdkClientException, AmazonServiceException {
    throw new RuntimeException("Not Implemented");
  }

  @Override
  public BucketVersioningConfiguration getBucketVersioningConfiguration(String bucketName)
      throws SdkClientException, AmazonServiceException {
    throw new RuntimeException("Not Implemented");
  }

  @Override
  public BucketVersioningConfiguration getBucketVersioningConfiguration(
      GetBucketVersioningConfigurationRequest getBucketVersioningConfigurationRequest)
      throws SdkClientException, AmazonServiceException {
    throw new RuntimeException("Not Implemented");
  }

  @Override
  public void setBucketVersioningConfiguration(
      SetBucketVersioningConfigurationRequest setBucketVersioningConfigurationRequest)
      throws SdkClientException, AmazonServiceException {
    throw new RuntimeException("Not Implemented");
  }

  @Override
  public BucketLifecycleConfiguration getBucketLifecycleConfiguration(String bucketName) {
    throw new RuntimeException("Not Implemented");
  }

  @Override
  public BucketLifecycleConfiguration getBucketLifecycleConfiguration(
      GetBucketLifecycleConfigurationRequest getBucketLifecycleConfigurationRequest) {
    throw new RuntimeException("Not Implemented");
  }

  @Override
  public void setBucketLifecycleConfiguration(String bucketName,
      BucketLifecycleConfiguration bucketLifecycleConfiguration) {
    throw new RuntimeException("Not Implemented");
  }

  @Override
  public void setBucketLifecycleConfiguration(
      SetBucketLifecycleConfigurationRequest setBucketLifecycleConfigurationRequest) {
    throw new RuntimeException("Not Implemented");
  }

  @Override
  public void deleteBucketLifecycleConfiguration(String bucketName) {
    throw new RuntimeException("Not Implemented");
  }

  @Override
  public void deleteBucketLifecycleConfiguration(
      DeleteBucketLifecycleConfigurationRequest deleteBucketLifecycleConfigurationRequest) {
    throw new RuntimeException("Not Implemented");
  }

  @Override
  public BucketCrossOriginConfiguration getBucketCrossOriginConfiguration(String bucketName) {
    throw new RuntimeException("Not Implemented");
  }

  @Override
  public BucketCrossOriginConfiguration getBucketCrossOriginConfiguration(
      GetBucketCrossOriginConfigurationRequest getBucketCrossOriginConfigurationRequest) {
    throw new RuntimeException("Not Implemented");
  }

  @Override
  public void setBucketCrossOriginConfiguration(String bucketName,
      BucketCrossOriginConfiguration bucketCrossOriginConfiguration) {
    throw new RuntimeException("Not Implemented");
  }

  @Override
  public void setBucketCrossOriginConfiguration(
      SetBucketCrossOriginConfigurationRequest setBucketCrossOriginConfigurationRequest) {
    throw new RuntimeException("Not Implemented");
  }

  @Override
  public void deleteBucketCrossOriginConfiguration(String bucketName) {
    throw new RuntimeException("Not Implemented");
  }

  @Override
  public void deleteBucketCrossOriginConfiguration(
      DeleteBucketCrossOriginConfigurationRequest deleteBucketCrossOriginConfigurationRequest) {
    throw new RuntimeException("Not Implemented");
  }

  @Override
  public BucketTaggingConfiguration getBucketTaggingConfiguration(String bucketName) {
    throw new RuntimeException("Not Implemented");
  }

  @Override
  public BucketTaggingConfiguration getBucketTaggingConfiguration(
      GetBucketTaggingConfigurationRequest getBucketTaggingConfigurationRequest) {
    throw new RuntimeException("Not Implemented");
  }

  @Override
  public void setBucketTaggingConfiguration(String bucketName, BucketTaggingConfiguration bucketTaggingConfiguration) {
    throw new RuntimeException("Not Implemented");
  }

  @Override
  public void setBucketTaggingConfiguration(SetBucketTaggingConfigurationRequest setBucketTaggingConfigurationRequest) {
    throw new RuntimeException("Not Implemented");
  }

  @Override
  public void deleteBucketTaggingConfiguration(String bucketName) {
    throw new RuntimeException("Not Implemented");
  }

  @Override
  public void deleteBucketTaggingConfiguration(
      DeleteBucketTaggingConfigurationRequest deleteBucketTaggingConfigurationRequest) {
    throw new RuntimeException("Not Implemented");
  }

  @Override
  public BucketNotificationConfiguration getBucketNotificationConfiguration(String bucketName)
      throws SdkClientException, AmazonServiceException {
    throw new RuntimeException("Not Implemented");
  }

  @Override
  public BucketNotificationConfiguration getBucketNotificationConfiguration(
      GetBucketNotificationConfigurationRequest getBucketNotificationConfigurationRequest)
      throws SdkClientException, AmazonServiceException {
    throw new RuntimeException("Not Implemented");
  }

  @Override
  public void setBucketNotificationConfiguration(
      SetBucketNotificationConfigurationRequest setBucketNotificationConfigurationRequest)
      throws SdkClientException, AmazonServiceException {
    throw new RuntimeException("Not Implemented");
  }

  @Override
  public void setBucketNotificationConfiguration(String bucketName,
      BucketNotificationConfiguration bucketNotificationConfiguration)
      throws SdkClientException, AmazonServiceException {
    throw new RuntimeException("Not Implemented");
  }

  @Override
  public BucketWebsiteConfiguration getBucketWebsiteConfiguration(String bucketName)
      throws SdkClientException, AmazonServiceException {
    throw new RuntimeException("Not Implemented");
  }

  @Override
  public BucketWebsiteConfiguration getBucketWebsiteConfiguration(
      GetBucketWebsiteConfigurationRequest getBucketWebsiteConfigurationRequest)
      throws SdkClientException, AmazonServiceException {
    throw new RuntimeException("Not Implemented");
  }

  @Override
  public void setBucketWebsiteConfiguration(String bucketName, BucketWebsiteConfiguration configuration)
      throws SdkClientException, AmazonServiceException {
    throw new RuntimeException("Not Implemented");
  }

  @Override
  public void setBucketWebsiteConfiguration(SetBucketWebsiteConfigurationRequest setBucketWebsiteConfigurationRequest)
      throws SdkClientException, AmazonServiceException {
    throw new RuntimeException("Not Implemented");
  }

  @Override
  public void deleteBucketWebsiteConfiguration(String bucketName) throws SdkClientException, AmazonServiceException {
    throw new RuntimeException("Not Implemented");
  }

  @Override
  public void deleteBucketWebsiteConfiguration(
      DeleteBucketWebsiteConfigurationRequest deleteBucketWebsiteConfigurationRequest)
      throws SdkClientException, AmazonServiceException {
    throw new RuntimeException("Not Implemented");
  }

  @Override
  public BucketPolicy getBucketPolicy(String bucketName) throws SdkClientException, AmazonServiceException {
    throw new RuntimeException("Not Implemented");
  }

  @Override
  public BucketPolicy getBucketPolicy(GetBucketPolicyRequest getBucketPolicyRequest)
      throws SdkClientException, AmazonServiceException {
    throw new RuntimeException("Not Implemented");
  }

  @Override
  public void setBucketPolicy(String bucketName, String policyText) throws SdkClientException, AmazonServiceException {
    throw new RuntimeException("Not Implemented");
  }

  @Override
  public void setBucketPolicy(SetBucketPolicyRequest setBucketPolicyRequest)
      throws SdkClientException, AmazonServiceException {
    throw new RuntimeException("Not Implemented");
  }

  @Override
  public void deleteBucketPolicy(String bucketName) throws SdkClientException, AmazonServiceException {
    throw new RuntimeException("Not Implemented");
  }

  @Override
  public void deleteBucketPolicy(DeleteBucketPolicyRequest deleteBucketPolicyRequest)
      throws SdkClientException, AmazonServiceException {
    throw new RuntimeException("Not Implemented");
  }

  @Override
  public URL generatePresignedUrl(String bucketName, String key, Date expiration) throws SdkClientException {
    throw new RuntimeException("Not Implemented");
  }

  @Override
  public URL generatePresignedUrl(String bucketName, String key, Date expiration, HttpMethod method)
      throws SdkClientException {
    throw new RuntimeException("Not Implemented");
  }

  @Override
  public URL generatePresignedUrl(GeneratePresignedUrlRequest generatePresignedUrlRequest) throws SdkClientException {
    throw new RuntimeException("Not Implemented");
  }

  @Override
  public InitiateMultipartUploadResult initiateMultipartUpload(InitiateMultipartUploadRequest request)
      throws SdkClientException, AmazonServiceException {
    throw new RuntimeException("Not Implemented");
  }

  @Override
  public UploadPartResult uploadPart(UploadPartRequest request) throws SdkClientException, AmazonServiceException {
    throw new RuntimeException("Not Implemented");
  }

  @Override
  public PartListing listParts(ListPartsRequest request) throws SdkClientException, AmazonServiceException {
    throw new RuntimeException("Not Implemented");
  }

  @Override
  public void abortMultipartUpload(AbortMultipartUploadRequest request)
      throws SdkClientException, AmazonServiceException {
    throw new RuntimeException("Not Implemented");
  }

  @Override
  public CompleteMultipartUploadResult completeMultipartUpload(CompleteMultipartUploadRequest request)
      throws SdkClientException, AmazonServiceException {
    throw new RuntimeException("Not Implemented");
  }

  @Override
  public MultipartUploadListing listMultipartUploads(ListMultipartUploadsRequest request)
      throws SdkClientException, AmazonServiceException {
    throw new RuntimeException("Not Implemented");
  }

  @Override
  public S3ResponseMetadata getCachedResponseMetadata(AmazonWebServiceRequest request) {
    throw new RuntimeException("Not Implemented");
  }

  @Override
  public void restoreObject(RestoreObjectRequest request) throws AmazonServiceException {
    throw new RuntimeException("Not Implemented");
  }

  @Override
  public RestoreObjectResult restoreObjectV2(RestoreObjectRequest request) throws AmazonServiceException {
    throw new RuntimeException("Not Implemented");
  }

  @Override
  public void restoreObject(String bucketName, String key, int expirationInDays) throws AmazonServiceException {
    throw new RuntimeException("Not Implemented");
  }

  @Override
  public void enableRequesterPays(String bucketName) throws AmazonServiceException, SdkClientException {
    throw new RuntimeException("Not Implemented");
  }

  @Override
  public void disableRequesterPays(String bucketName) throws AmazonServiceException, SdkClientException {
    throw new RuntimeException("Not Implemented");
  }

  @Override
  public boolean isRequesterPaysEnabled(String bucketName) throws AmazonServiceException, SdkClientException {
    throw new RuntimeException("Not Implemented");
  }

  @Override
  public void setBucketReplicationConfiguration(String bucketName, BucketReplicationConfiguration configuration)
      throws AmazonServiceException, SdkClientException {
    throw new RuntimeException("Not Implemented");
  }

  @Override
  public void setBucketReplicationConfiguration(
      SetBucketReplicationConfigurationRequest setBucketReplicationConfigurationRequest)
      throws AmazonServiceException, SdkClientException {
    throw new RuntimeException("Not Implemented");
  }

  @Override
  public BucketReplicationConfiguration getBucketReplicationConfiguration(String bucketName)
      throws AmazonServiceException, SdkClientException {
    throw new RuntimeException("Not Implemented");
  }

  @Override
  public BucketReplicationConfiguration getBucketReplicationConfiguration(
      GetBucketReplicationConfigurationRequest getBucketReplicationConfigurationRequest)
      throws AmazonServiceException, SdkClientException {
    throw new RuntimeException("Not Implemented");
  }

  @Override
  public void deleteBucketReplicationConfiguration(String bucketName)
      throws AmazonServiceException, SdkClientException {
    throw new RuntimeException("Not Implemented");
  }

  @Override
  public void deleteBucketReplicationConfiguration(DeleteBucketReplicationConfigurationRequest request)
      throws AmazonServiceException, SdkClientException {
    throw new RuntimeException("Not Implemented");
  }

  @Override
  public boolean doesObjectExist(String bucketName, String objectName)
      throws AmazonServiceException, SdkClientException {
    throw new RuntimeException("Not Implemented");
  }

  @Override
  public BucketAccelerateConfiguration getBucketAccelerateConfiguration(String bucketName)
      throws AmazonServiceException, SdkClientException {
    throw new RuntimeException("Not Implemented");
  }

  @Override
  public BucketAccelerateConfiguration getBucketAccelerateConfiguration(
      GetBucketAccelerateConfigurationRequest getBucketAccelerateConfigurationRequest)
      throws AmazonServiceException, SdkClientException {
    throw new RuntimeException("Not Implemented");
  }

  @Override
  public void setBucketAccelerateConfiguration(String bucketName, BucketAccelerateConfiguration accelerateConfiguration)
      throws AmazonServiceException, SdkClientException {
    throw new RuntimeException("Not Implemented");
  }

  @Override
  public void setBucketAccelerateConfiguration(
      SetBucketAccelerateConfigurationRequest setBucketAccelerateConfigurationRequest)
      throws AmazonServiceException, SdkClientException {
    throw new RuntimeException("Not Implemented");
  }

  @Override
  public DeleteBucketMetricsConfigurationResult deleteBucketMetricsConfiguration(String bucketName, String id)
      throws AmazonServiceException, SdkClientException {
    throw new RuntimeException("Not Implemented");
  }

  @Override
  public DeleteBucketMetricsConfigurationResult deleteBucketMetricsConfiguration(
      DeleteBucketMetricsConfigurationRequest deleteBucketMetricsConfigurationRequest)
      throws AmazonServiceException, SdkClientException {
    throw new RuntimeException("Not Implemented");
  }

  @Override
  public GetBucketMetricsConfigurationResult getBucketMetricsConfiguration(String bucketName, String id)
      throws AmazonServiceException, SdkClientException {
    throw new RuntimeException("Not Implemented");
  }

  @Override
  public GetBucketMetricsConfigurationResult getBucketMetricsConfiguration(
      GetBucketMetricsConfigurationRequest getBucketMetricsConfigurationRequest)
      throws AmazonServiceException, SdkClientException {
    throw new RuntimeException("Not Implemented");
  }

  @Override
  public SetBucketMetricsConfigurationResult setBucketMetricsConfiguration(String bucketName,
      MetricsConfiguration metricsConfiguration) throws AmazonServiceException, SdkClientException {
    throw new RuntimeException("Not Implemented");
  }

  @Override
  public SetBucketMetricsConfigurationResult setBucketMetricsConfiguration(
      SetBucketMetricsConfigurationRequest setBucketMetricsConfigurationRequest)
      throws AmazonServiceException, SdkClientException {
    throw new RuntimeException("Not Implemented");
  }

  @Override
  public ListBucketMetricsConfigurationsResult listBucketMetricsConfigurations(
      ListBucketMetricsConfigurationsRequest listBucketMetricsConfigurationsRequest)
      throws AmazonServiceException, SdkClientException {
    throw new RuntimeException("Not Implemented");
  }

  @Override
  public DeleteBucketAnalyticsConfigurationResult deleteBucketAnalyticsConfiguration(String bucketName, String id)
      throws AmazonServiceException, SdkClientException {
    throw new RuntimeException("Not Implemented");
  }

  @Override
  public DeleteBucketAnalyticsConfigurationResult deleteBucketAnalyticsConfiguration(
      DeleteBucketAnalyticsConfigurationRequest deleteBucketAnalyticsConfigurationRequest)
      throws AmazonServiceException, SdkClientException {
    throw new RuntimeException("Not Implemented");
  }

  @Override
  public GetBucketAnalyticsConfigurationResult getBucketAnalyticsConfiguration(String bucketName, String id)
      throws AmazonServiceException, SdkClientException {
    throw new RuntimeException("Not Implemented");
  }

  @Override
  public GetBucketAnalyticsConfigurationResult getBucketAnalyticsConfiguration(
      GetBucketAnalyticsConfigurationRequest getBucketAnalyticsConfigurationRequest)
      throws AmazonServiceException, SdkClientException {
    throw new RuntimeException("Not Implemented");
  }

  @Override
  public SetBucketAnalyticsConfigurationResult setBucketAnalyticsConfiguration(String bucketName,
      AnalyticsConfiguration analyticsConfiguration) throws AmazonServiceException, SdkClientException {
    throw new RuntimeException("Not Implemented");
  }

  @Override
  public SetBucketAnalyticsConfigurationResult setBucketAnalyticsConfiguration(
      SetBucketAnalyticsConfigurationRequest setBucketAnalyticsConfigurationRequest)
      throws AmazonServiceException, SdkClientException {
    throw new RuntimeException("Not Implemented");
  }

  @Override
  public ListBucketAnalyticsConfigurationsResult listBucketAnalyticsConfigurations(
      ListBucketAnalyticsConfigurationsRequest listBucketAnalyticsConfigurationsRequest)
      throws AmazonServiceException, SdkClientException {
    throw new RuntimeException("Not Implemented");
  }

  @Override
  public DeleteBucketInventoryConfigurationResult deleteBucketInventoryConfiguration(String bucketName, String id)
      throws AmazonServiceException, SdkClientException {
    throw new RuntimeException("Not Implemented");
  }

  @Override
  public DeleteBucketInventoryConfigurationResult deleteBucketInventoryConfiguration(
      DeleteBucketInventoryConfigurationRequest deleteBucketInventoryConfigurationRequest)
      throws AmazonServiceException, SdkClientException {
    throw new RuntimeException("Not Implemented");
  }

  @Override
  public GetBucketInventoryConfigurationResult getBucketInventoryConfiguration(String bucketName, String id)
      throws AmazonServiceException, SdkClientException {
    throw new RuntimeException("Not Implemented");
  }

  @Override
  public GetBucketInventoryConfigurationResult getBucketInventoryConfiguration(
      GetBucketInventoryConfigurationRequest getBucketInventoryConfigurationRequest)
      throws AmazonServiceException, SdkClientException {
    throw new RuntimeException("Not Implemented");
  }

  @Override
  public SetBucketInventoryConfigurationResult setBucketInventoryConfiguration(String bucketName,
      InventoryConfiguration inventoryConfiguration) throws AmazonServiceException, SdkClientException {
    throw new RuntimeException("Not Implemented");
  }

  @Override
  public SetBucketInventoryConfigurationResult setBucketInventoryConfiguration(
      SetBucketInventoryConfigurationRequest setBucketInventoryConfigurationRequest)
      throws AmazonServiceException, SdkClientException {
    throw new RuntimeException("Not Implemented");
  }

  @Override
  public ListBucketInventoryConfigurationsResult listBucketInventoryConfigurations(
      ListBucketInventoryConfigurationsRequest listBucketInventoryConfigurationsRequest)
      throws AmazonServiceException, SdkClientException {
    throw new RuntimeException("Not Implemented");
  }

  @Override
  public DeleteBucketEncryptionResult deleteBucketEncryption(String bucketName)
      throws AmazonServiceException, SdkClientException {
    throw new RuntimeException("Not Implemented");
  }

  @Override
  public DeleteBucketEncryptionResult deleteBucketEncryption(DeleteBucketEncryptionRequest request)
      throws AmazonServiceException, SdkClientException {
    throw new RuntimeException("Not Implemented");
  }

  @Override
  public GetBucketEncryptionResult getBucketEncryption(String bucketName)
      throws AmazonServiceException, SdkClientException {
    throw new RuntimeException("Not Implemented");
  }

  @Override
  public GetBucketEncryptionResult getBucketEncryption(GetBucketEncryptionRequest request)
      throws AmazonServiceException, SdkClientException {
    throw new RuntimeException("Not Implemented");
  }

  @Override
  public SetBucketEncryptionResult setBucketEncryption(SetBucketEncryptionRequest setBucketEncryptionRequest)
      throws AmazonServiceException, SdkClientException {
    throw new RuntimeException("Not Implemented");
  }

  @Override
  public void shutdown() {
    throw new RuntimeException("Not Implemented");
  }

  @Override
  public com.amazonaws.services.s3.model.Region getRegion() {
    throw new RuntimeException("Not Implemented");
  }

  @Override
  public String getRegionName() {
    throw new RuntimeException("Not Implemented");
  }

  @Override
  public URL getUrl(String bucketName, String key) {
    throw new RuntimeException("Not Implemented");
  }

  @Override
  public AmazonS3Waiters waiters() {
    throw new RuntimeException("Not Implemented");
  }

}
