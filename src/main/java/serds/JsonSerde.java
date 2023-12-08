package serds;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serializer;

public class JsonSerde<T> implements Serde<T> {

    private final ObjectMapper objectMapper = new ObjectMapper();
//    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//    objectMapper.
//    objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);


    private final Class<T> type;

    public JsonSerde(Class<T> type) {
        this.type = type;
    }

    @Override
    public Serializer<T> serializer() {
        return new JsonSerializer<>();
    }

    @Override
    public Deserializer<T> deserializer() {
        return new JsonDeserializer<>(type);
    }

    class JsonSerializer<U> implements Serializer<U> {
        @Override
        public byte[] serialize(String topic, U data) {
            try {
                return objectMapper.writeValueAsBytes(data);
            } catch (Exception e) {
                throw new SerializationException("Error serializing JSON message", e);
            }
        }
    }

    class JsonDeserializer<U> implements Deserializer<U> {
        private final Class<U> type;

        public JsonDeserializer(Class<U> type) {
            this.type = type;
        }

        @Override
        public U deserialize(String topic, byte[] bytes) {
            if (bytes == null)
                return null;

            try {
                return objectMapper.readValue(bytes, type);
            } catch (Exception e) {
                throw new SerializationException(e);
            }
        }
    }
}
