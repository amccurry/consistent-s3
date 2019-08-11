package consistent.s3;

import org.apache.zookeeper.data.Stat;

import com.amazonaws.thirdparty.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode
@ToString
public class ConsistencyEntry {
  private String bucket;
  private String key;
  private String consistencyKey;
  private boolean deleted;
  private int consistencyCount;
  private long createdTs;
  private long firstConsistentViewTs;
  @JsonIgnore
  private Integer version;

  public void setStat(Stat stat) {
    if (stat != null) {
      setVersion(stat.getVersion());
    } else {
      setVersion(null);
    }
  }
}
