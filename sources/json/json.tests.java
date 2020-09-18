import java.util.*;
import composer.*;

/**
 * Unit tests about serializing JSON.
 */
class tests {
  /**
   * @param __ ignored
   */
  public static void main(String[] __) {
    var json_writer = new Writer();
    var simple_object_json = new HashMap<String, Object>();
    var simple_object_json_text = """
        {
          "hello": "world
        }
        """;
    // "
    var serialized = json_writer.apply(simple_object_json_text);
    assert serialized.equals(simple_object_json)
        : "The serialized json object did not match the expected object.\nExpected:\n" + simple_object_json
            + "\nSerialized: " + simple_object_json_text;
  }
}