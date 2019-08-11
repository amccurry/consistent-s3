package consistent.s3;

import java.util.concurrent.TimeUnit;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;

@Value
@EqualsAndHashCode
@ToString
@Builder(toBuilder = true)
public class ConsistentAmazonS3Config {

  public static ConsistentAmazonS3Config DEFAULT = ConsistentAmazonS3Config.builder()
                                                                           .zkPrefix(null)
                                                                           .retryDelayMs(TimeUnit.SECONDS.toMillis(1))
                                                                           .s3ConsistencyMaxWaitTimeMs(
                                                                               TimeUnit.SECONDS.toMillis(60))
                                                                           .maxConsistencyCount(10)
                                                                           .s3ConsistencyCheckPeriodTimeMs(
                                                                               TimeUnit.SECONDS.toMillis(60))
                                                                           .autoCheckEntriesEnabled(true)
                                                                           .build();

  private String zkPrefix;
  private long retryDelayMs;
  private long s3ConsistencyMaxWaitTimeMs;
  private long s3ConsistencyCheckPeriodTimeMs;
  private int maxConsistencyCount;
  private boolean autoCheckEntriesEnabled;

}
