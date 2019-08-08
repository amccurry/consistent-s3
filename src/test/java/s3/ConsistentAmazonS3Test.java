package s3;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryForever;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

public class ConsistentAmazonS3Test {

  private static final Logger LOGGER = LoggerFactory.getLogger(ConsistentAmazonS3Test.class);

  private static final String ZK;
  private static final String BUCKET;
  private static AmazonS3 CLIENT;
  private static CuratorFramework CURATOR;

  static {
    Properties properties = new Properties();
    try {
      properties.load(new FileInputStream("test.properties"));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    ZK = properties.getProperty("zk", "localhost");
    BUCKET = properties.getProperty("bucket");
  }

  @BeforeClass
  public static void setup() {
    CLIENT = AmazonS3ClientBuilder.defaultClient();
    RetryPolicy retryPolicy = new RetryForever((int) TimeUnit.SECONDS.toMillis(10));
    CURATOR = CuratorFrameworkFactory.newClient(ZK, retryPolicy);
    CURATOR.getUnhandledErrorListenable()
           .addListener((message, e) -> {
             LOGGER.error("Unknown error " + message, e);
           });
    CURATOR.getConnectionStateListenable()
           .addListener((c, newState) -> {
             LOGGER.info("Connection state {}", newState);
           });
    CURATOR.start();
  }

  @Test
  public void testConsistentAmazonS3Basic() throws Exception {
    ConsistentAmazonS3 s3 = ConsistentAmazonS3.create(CLIENT, CURATOR, "/s3/consistent/test");
    String key = "contest/test";
    for (int i = 0; i < 20; i++) {
      String value = UUID.randomUUID()
                         .toString();
      s3.putConsistentObject(BUCKET, key, value);
      assertEquals(value, s3.getConsistentObjectAsString(BUCKET, key));
    }
  }

  @Test
  public void testConsistentAmazonS3AllPuts() throws Exception {
    ConsistentAmazonS3 s3 = ConsistentAmazonS3.create(CLIENT, CURATOR, "/s3/consistent/test");
    String key = "contest/test";
    for (int i = 0; i < 20; i++) {
      String value = UUID.randomUUID()
                         .toString();
      s3.putConsistentObject(BUCKET, key, value);
    }
    s3.checkOutstandingConsistencyEntries(true);
    List<ConsistencyEntry> entries = s3.getOutstandingConsistencyEntries();
    assertTrue(entries.isEmpty());
  }

  @Test
  public void testConsistentAmazonS3Deletes() throws Exception {
    ConsistentAmazonS3 s3 = ConsistentAmazonS3.create(CLIENT, CURATOR, "/s3/consistent/test");
    String key = "contest/test";
    s3.putConsistentObject(BUCKET, key, UUID.randomUUID()
                                            .toString());
    s3.deleteConsistentObject(BUCKET, key);
    List<ConsistencyEntry> entries = s3.getOutstandingConsistencyEntries();
    assertTrue(entries.isEmpty());
  }

  @Test
  public void testConsistentAmazonS3Threaded() throws Exception {
    ConsistentAmazonS3 s3 = ConsistentAmazonS3.create(CLIENT, CURATOR, "/s3/consistent/test");
    String key = "contest/test";
    s3.putConsistentObject(BUCKET, key, Integer.toString(0));
    int max = 100;
    Callable<Integer> even = createCallable(s3, key, "even", 0, max);
    Callable<Integer> odd = createCallable(s3, key, "odd", 1, max);
    ExecutorService pool = Executors.newCachedThreadPool();
    try {
      Future<Integer> evenFuture = pool.submit(even);
      Future<Integer> oddFuture = pool.submit(odd);
      System.out.println(evenFuture.get());
      System.out.println(oddFuture.get());
    } finally {
      pool.shutdownNow();
    }
  }

  private Callable<Integer> createCallable(ConsistentAmazonS3 s3, String key, String type, int checkValue, int max) {
    return new Callable<Integer>() {
      @Override
      public Integer call() throws Exception {
        try {
          while (true) {
            String value = s3.getConsistentObjectAsString(BUCKET, key);
            int i = Integer.parseInt(value);
            if (i >= max) {
              return i;
            }
            if (i % 2 == checkValue) {
              String newValue = Integer.toString(i + 1);
              LOGGER.info("{} {} => {}", type, value, newValue);
              s3.putConsistentObject(BUCKET, key, newValue);
            }
          }
        } catch (Exception e) {
          LOGGER.error("Unknown error", e);
          throw e;
        }
      }
    };
  }

}
