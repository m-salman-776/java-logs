package kafka;

import Models.User;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Properties;

public class Consumers {
    static KafkaConsumer<String, User> userKafkaConsumer;
    public static KafkaConsumer<String,String> getConsumer (){
        Properties props = new Properties();
//        props.put("bootstrap.servers", "broker1:9092,broker2:9092");
//
//        props.put("key.deserializer",
//                "org.apache.kafka.common.serialization.StringDeserializer");
//        props.put("value.deserializer",
//                "org.apache.kafka.common.serialization.StringDeserializer");
        KafkaConsumer<String, String> consumer = new KafkaConsumer<String,String>(props);
        return consumer;
    }
    public static KafkaConsumer<String,User> getOrCreateUserConsumer(Properties properties){
        if (userKafkaConsumer != null) return userKafkaConsumer;
        userKafkaConsumer = new KafkaConsumer<>(properties);
        return userKafkaConsumer;
    }
}
