package kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.common.protocol.types.Field;

import java.util.Properties;

public class Producer {
//    KafkaProducer producer;
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
//
//        KafkaProducerConfig config = new KafkaProducerConfig(props);
//        Producer<byte[], byte[]> producer = new KafkaProducer<>(config);
        KafkaProducer <String,String>producer  = new KafkaProducer<>(props);
        return producer;
    }
}
