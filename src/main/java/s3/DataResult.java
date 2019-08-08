package s3;

import org.apache.zookeeper.data.Stat;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DataResult {

  Stat stat;
  byte[] data;

}
