package kafka;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.TopicPartition;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class KafkaTest {
    public static void main(String []args){
        KafkaProducer<String,String> producer = Producer.buildKafkaProducer();
        ProducerRecord<String,String> record = new ProducerRecord<>("msg","valies","j");
        try {
            RecordMetadata recordMetadata= producer.send(record).get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }

        KafkaConsumer<String, String> consumer = Consumer.getConsumer();
        consumer.subscribe(Collections.singleton("someName"));
        consumer.commitSync();
//        consumer.commi
        int a ;
        Map<TopicPartition, OffsetAndMetadata> currentOffset = new HashMap<>();
        ConsumerRebalanceListener lo = new ConsumerRebalanceListener() {
            @Override
            public void onPartitionsRevoked(Collection<TopicPartition> collection) {
                consumer.commitSync(currentOffset);
            }

            @Override
            public void onPartitionsAssigned(Collection<TopicPartition> collection) {

            }
        };
        consumer.commitAsync();
//        consumer.com
        try {
            while (true){
                ConsumerRecords<String,String> consumerRecords = consumer.poll(100);
                for (ConsumerRecord<String,String> record1 : consumerRecords){
                    consumer.commitSync();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            consumer.close();
        }
    }
}
