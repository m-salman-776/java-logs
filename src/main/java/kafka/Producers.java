package kafka;

import Models.ClickEvent;
import Models.User;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.common.protocol.types.Field;

import java.util.Properties;

public class Producers {
    static KafkaProducer<String, User> userProducer;
    static KafkaProducer<String,ClickEvent> clickEventKafkaProducer;
    public static KafkaProducer<String,String> buildKafkaProducer() {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("acks","all");
//        props.put("compression.type", configuration.get("kafka.producer.compression.type","none"));
//        props.put("max.in.flight.requests.per.connection", configuration.getInt("kafka.producer.max.in.flight.requests.per.connection",1));
//        props.put("batch.size", configuration.getInt("kafka.producer.batch.size",16384));
//        props.put("linger.ms", configuration.getInt("kafka.producer.linger.ms",5));
        props.put("key.serializer","org.apache.kafka.common.serialization.ByteArraySerializer");
        props.put("value.serializer","org.apache.kafka.common.serialization.ByteArraySerializer");
        KafkaProducer <String,String>producer  = new KafkaProducer<>(props);
        return producer;
    }
    public static KafkaProducer<String,User> getOrCreateUserProducer(Properties properties){
        if (userProducer != null){
            return userProducer;
        }
        userProducer = new KafkaProducer<String,User>(properties);
        return userProducer;
    }
    public static KafkaProducer<String, ClickEvent> getOrCreateClickEventProducer(Properties properties){
        clickEventKafkaProducer = new KafkaProducer<String,ClickEvent>(properties);
        return clickEventKafkaProducer;
    }

}
