package serds;


import Models.ClickEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

import javax.security.auth.DestroyFailedException;

public class ClickEventDeserializer<T> implements Deserializer<ClickEvent> {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private Class<ClickEvent> type;

    public ClickEventDeserializer(Class<ClickEvent> type) {
        this.type = type;
    }

    public ClickEventDeserializer(){}

    @Override
    public ClickEvent deserialize(String topic, byte[] bytes) {
        if (bytes == null || bytes.length == 0) return null;

        try {
            return objectMapper.readValue(bytes, type);
        } catch (Exception e) {
            throw new SerializationException(e);
        }
    }
}