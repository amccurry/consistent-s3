package consistent.s3;

import java.io.IOException;

import com.amazonaws.thirdparty.jackson.core.JsonProcessingException;
import com.amazonaws.thirdparty.jackson.databind.ObjectMapper;

public class JsonUtils {

  private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

  public static byte[] toBytes(Object o) {
    try {
      return OBJECT_MAPPER.writeValueAsBytes(o);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }

  public static <T> T toObject(byte[] bs, Class<T> clazz) {
    try {
      return OBJECT_MAPPER.readValue(bs, clazz);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

}
