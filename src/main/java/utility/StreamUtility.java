package utility;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.errors.LogAndContinueExceptionHandler;

import java.util.Properties;

public class StreamUtility {
    static ObjectMapper mapper;
    static Properties properties;
    public static ObjectMapper getMapper(){
        return mapper = new ObjectMapper();
    }
    public static Properties getProperties(String appId){
        properties.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG,"localhost:9092");
        properties.put(StreamsConfig.APPLICATION_ID_CONFIG,appId);
        properties.put(StreamsConfig.DEFAULT_DESERIALIZATION_EXCEPTION_HANDLER_CLASS_CONFIG, LogAndContinueExceptionHandler.class.getName());
        return properties;
    }
}
