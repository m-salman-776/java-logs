package serds;

import Models.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Deserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;

//public class JsonDeserializer<T> implements Deserializer<T> {
//    private final ObjectMapper objectMapper = new ObjectMapper();
//    private Class<T> type;
//
//    public JsonDeserializer(Class<T> type) {
//        this.type = type;
//    }
//
//    public JsonDeserializer(){}
//
//    @Override
//    public T deserialize(String topic, byte[] bytes) {
//        if (bytes == null || bytes.length == 0) return null;
//
//        try {
//            return objectMapper.readValue(bytes, type);
//        } catch (Exception e) {
//            throw new SerializationException(e);
//        }
//    }
//}



public class JsonDeserializer implements Deserializer<User> {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private Class<User> type;

    // Default constructor remains the same
    public JsonDeserializer() {}

    // Constructor with type remains the same
    public JsonDeserializer(Class<User> type) {
        this.type = type;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
    }

    @Override
    public User deserialize(String topic, byte[] bytes) {
        if (bytes == null || bytes.length == 0) return null;

        try {
            return objectMapper.readValue(bytes, User.class);
        } catch (Exception e) {
            throw new SerializationException(e);
        }
    }

    @Override
    public void close() {
        // This can remain empty unless you need to close resources.
    }
}
