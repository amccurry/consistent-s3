package consistent.s3;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CuratorUtils {

  private static final Logger LOGGER = LoggerFactory.getLogger(CuratorUtils.class);

  public static List<String> getChildrenIfExists(CuratorFramework curatorFramework, String path) {
    while (true) {
      try {
        Stat stat = curatorFramework.checkExists()
                                    .forPath(path);
        if (stat != null) {
          return curatorFramework.getChildren()
                                 .forPath(path);
        }
        return null;
      } catch (Exception e) {
        if (e instanceof KeeperException) {
          LOGGER.warn(e.getMessage());
          waitAfterError();
        } else {
          throw new RuntimeException(e);
        }
      }
    }
  }

  public static DataResult getDataIfExists(CuratorFramework curatorFramework, String path) {
    while (true) {
      try {
        Stat stat = curatorFramework.checkExists()
                                    .forPath(path);
        if (stat != null) {
          byte[] data = curatorFramework.getData()
                                        .storingStatIn(stat)
                                        .forPath(path);
          return new DataResult(stat, data);
        }
        return null;
      } catch (Exception e) {
        if (e instanceof KeeperException) {
          LOGGER.warn(e.getMessage());
          waitAfterError();
        } else {
          throw new RuntimeException(e);
        }
      }
    }
  }

  public static void deletePathIfExists(CuratorFramework curatorFramework, String path) {
    while (true) {
      try {
        Stat stat = curatorFramework.checkExists()
                                    .forPath(path);
        if (stat != null) {
          curatorFramework.delete()
                          .withVersion(stat.getVersion())
                          .forPath(path);
        }
        return;
      } catch (Exception e) {
        if (e instanceof KeeperException) {
          LOGGER.warn(e.getMessage());
          waitAfterError();
        } else {
          throw new RuntimeException(e);
        }
      }
    }
  }

  public static void createOrSetDataForPath(CuratorFramework curatorFramework, String path, byte[] data) {
    while (true) {
      try {
        Stat stat = curatorFramework.checkExists()
                                    .creatingParentsIfNeeded()
                                    .forPath(path);
        if (stat != null) {
          curatorFramework.setData()
                          .withVersion(stat.getVersion())
                          .forPath(path, data);
        } else {
          curatorFramework.create()
                          .creatingParentsIfNeeded()
                          .forPath(path, data);
        }
        return;
      } catch (Exception e) {
        if (e instanceof KeeperException) {
          LOGGER.warn(e.getMessage());
          waitAfterError();
        } else {
          throw new RuntimeException(e);
        }
      }
    }
  }

  private static void waitAfterError() {
    try {
      Thread.sleep(TimeUnit.SECONDS.toMillis(1));
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  public static boolean createPath(CuratorFramework curatorFramework, String path, byte[] data) {
    while (true) {
      try {
        Stat stat = curatorFramework.checkExists()
                                    .creatingParentsIfNeeded()
                                    .forPath(path);
        if (stat != null) {
          return false;
        } else {
          curatorFramework.create()
                          .creatingParentsIfNeeded()
                          .forPath(path, data);
          return true;
        }
      } catch (Exception e) {
        if (e instanceof KeeperException) {
          LOGGER.warn(e.getMessage());
          waitAfterError();
        } else {
          throw new RuntimeException(e);
        }
      }
    }
  }

  public static Stat getStatPath(CuratorFramework curatorFramework, String path) {
    while (true) {
      try {
        return curatorFramework.checkExists()
                               .creatingParentsIfNeeded()
                               .forPath(path);
      } catch (Exception e) {
        if (e instanceof KeeperException) {
          LOGGER.warn(e.getMessage());
          waitAfterError();
        } else {
          throw new RuntimeException(e);
        }
      }
    }
  }

  public static boolean setData(CuratorFramework curatorFramework, String path, byte[] data, int versionProvided) {
    while (true) {
      try {
        Stat stat = curatorFramework.checkExists()
                                    .creatingParentsIfNeeded()
                                    .forPath(path);
        if (stat != null) {
          if (versionProvided != stat.getVersion()) {
            return false;
          }
          curatorFramework.setData()
                          .withVersion(versionProvided)
                          .forPath(path, data);
          return true;
        } else {
          return false;
        }
      } catch (Exception e) {
        if (e instanceof KeeperException) {
          LOGGER.warn(e.getMessage());
          waitAfterError();
        } else {
          throw new RuntimeException(e);
        }
      }
    }
  }
}
