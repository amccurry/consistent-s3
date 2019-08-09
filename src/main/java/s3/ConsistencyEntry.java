package s3;

import org.apache.zookeeper.data.Stat;

import com.amazonaws.thirdparty.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode
@ToString
public class ConsistencyEntry {
  String bucket;
  String key;
  String consistencyKey;
  boolean deleted;
  int consistencyCount;
  long firstConsistentView;
  @JsonIgnore
  Integer version;

  public void setStat(Stat stat) {
    if (stat != null) {
      setVersion(stat.getVersion());
    } else {
      setVersion(null);
    }
  }
}
