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
  int consistencyCount;
  long firstConsistentView;
  @JsonIgnore
  Stat stat;
}
