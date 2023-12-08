package kafka;

import Models.Address;
import Models.User;

import kafka.streams.OrderProcessingApp;
import kafka.streams.UpperCaseStream;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import serds.JsonDeserializer;
import serds.JsonSerializer;
import utility.ConfigManager;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static kafka.streams.EnrichOrderWithUserInfo.runEnrichOrderWithUserInfo;
import static kafka.streams.FailedTxnAlerts.runFailedTxnAlert;
import static kafka.streams.PageViewCount.readStateStore;
import static kafka.streams.PageViewCount.runPageViewCount;
import static kafka.streams.SessionDurationCalculation.runUserSessionCalculation;

public class kafkaCalls {
    public static void main(String []args){
        String src1 = "src_1",src2 = "src_2",sink1 = "sink_1",sink2 = "sink_2";
        runFailedTxnAlert(src1,sink1);
//        runUserSessionCalculation(src1,sink1);
//        runPageViewCount(src1,sink1);
//        CompletableFuture<Void> runPageViewCount = CompletableFuture.supplyAsync(()->{
////            runPageViewCount();
//            runPageViewCount(src1,sink1);
//            return null;
//        });

//        runPageViewCount.join();



//        readStateStore("e");
//        runEnrichOrderWithUserInfo(src1,src2,sink1);

//        OrderProcessingApp.runOrderProcessingSystem(src1,sink1,sink2);
//        CountingEvent.runCountEvent("clicks","click-count");
//        UpperCaseStream.runUpCaseStream();
//        testProducerConsumer();
    }



    private static void testProducerConsumer() {
        CompletableFuture<Void> producerCall = CompletableFuture.supplyAsync(()->{
            for (int i=5; i< 20; i++) callProducer(i);
            return null;
        });
        CompletableFuture<Void> consumerCall = CompletableFuture.supplyAsync(()-> {
            callConsumer();
            return null;
        });
        // producerCall
        CompletableFuture<Void> allFutures = CompletableFuture.allOf(consumerCall);
        allFutures.join();
    }
    private static void callConsumer() {
        Properties properties = ConfigManager.getAllProperties();
        properties.put("value.deserializer",JsonDeserializer.class);
        properties.put("value.deserializer.type", User.class);
        properties.put("auto.offset.reset","earliest");
        properties.put("group.id", "d");

        KafkaConsumer<String, User> consumer = Consumers.getOrCreateUserConsumer(properties);
        try (consumer) {
            consumer.subscribe(Collections.singleton("users"));
            while (true) {
                ConsumerRecords<String, User> consumerRecords = consumer.poll(Duration.ofMillis(100));
                consumerRecords.forEach(record -> {
                    User user = record.value();
                    System.out.println(user);
                });
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    private static void callProducer(int idx) {
        Properties properties = ConfigManager.getAllProperties();
//        properties.put("value.serializer", JsonSerializer.class);
        KafkaProducer<String, User> producer = Producers.getOrCreateUserProducer(properties);

        User user1 = new User("idx","salman :" + idx,null);
        ProducerRecord<String,User> record = new ProducerRecord<>("users","use2 :" + idx,user1);
        try {
            RecordMetadata recordMetadata= producer.send(record).get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}
