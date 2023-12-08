package kafka.streams;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Properties;
import Models.Order;
import serds.JsonSerde;

@Slf4j
public class OrderProcessingApp {
    static ObjectMapper objectMapper = new ObjectMapper();
    public static void runOrderProcessingSystem(String source1 , String sink1,String sink2) {
        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "order-processing-app");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.ByteArray().getClass());

        StreamsBuilder builder = new StreamsBuilder();
        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.writeValueAsBytes(Order())


        KStream<String, byte[]> ordersStream = builder.stream(source1);
//        ordersStream.peek((key, value) -> {
//            try{
//                Order order = objectMapper.readValue(value,Order.class);
//                System.out.println("Order -> " + order);
//            }catch (Exception e){
//                System.out.println("Exception"+e.toString());
//            }
//        });

        // Use Jackson to deserialize JSON into Order objects


        KStream<String, Order> parsedOrders = ordersStream.mapValues((key, value) -> {
            try {
                return objectMapper.readValue(value, Order.class);
            } catch (Exception e) {
                return null;
            }
        }).filter((key, value) -> value != null);


//
//        parsedOrders.filter((key, value) -> value == null).to("problematic");

//        parsedOrders.filter((key, value) -> value != null)
//                .mapValues((value) -> {
//                    try {
//                        return objectMapper.writeValueAsBytes(value);
//                    }catch (Exception e){
//                        return null;
//                    }
//                })
//                .to("good");


//        KStream<String, Order> parsedOrders = ordersStream.mapValues(value -> {
//            try {
//                return objectMapper.readValue(value, Order.class);
//            } catch (Exception e) {
//                return null;
//            }
//        });


        // Count by userId
        System.out.println("before first stream");
//        KTable<String, Long> userOrderCounts = parsedOrders.groupBy((key,order) -> order.userId)
//                .count();

        KTable<String, Long> userOrderCounts = parsedOrders
                .groupBy((key, order) -> order.userId, Grouped.with(Serdes.String(), new JsonSerde<>(Order.class)))
                .count();

//        parsedOrders.groupBy((key, order) -> order.userId, Serialized.with(Serdes.String(), new JsonSerde<>(Order.class)));



//        userOrderCounts.toStream()
//                .mapValues((value -> {
//                    try {
//                        return objectMapper.writeValueAsBytes(value);
//                    }catch (Exception e){
//                        return new byte[]{};
//                    }
//                })).filter((k,v) -> v.length > 0)
//                .to(sink1);
        userOrderCounts.toStream().to(sink1,Produced.with(Serdes.String(),Serdes.Long()));

//        userOrderCounts.toStream()
//                .mapValues((value -> {
//                    try {
//                        return objectMapper.writeValueAsBytes(value);
//                    }catch (Exception e){
//                        return null;
//                    }
//                })).filter((k, v) -> v != null)
//                .to(sink1, Produced.with(Serdes.String(), Serdes.ByteArray()));

//                .to(sink1, Produced.with(Serdes.String(), Serdes.Long()));

        // Calculate total spend by userId
        KTable<String, Double> userTotalSpend = parsedOrders.groupBy((key, order) -> order.userId,Grouped.with(Serdes.String(),new JsonSerde<>(Order.class)))
                .aggregate(
                        () -> 0.0, // Initializer
                        (userId, order, currentTotal) -> currentTotal + order.amount, // Aggregator
                        Materialized.with(Serdes.String(),Serdes.Double())
                );

        userTotalSpend.toStream().to(sink2, Produced.with(Serdes.String(), Serdes.Double()));

        KafkaStreams streams = new KafkaStreams(builder.build(), props);
        streams.start();

        Runtime.getRuntime().addShutdownHook(new Thread(streams::close));
    }

    public static void produceOrderEvent(KafkaProducer<String,byte[]> producer, int start, String topic){
        try {
            for (int i = start ; i < start + 10;i++){
                Order order = new Order("orderId"+i,"userId"+i,"productId"+i,Math.random()*1000+i);
                byte [] valueByte = objectMapper.writeValueAsBytes(order);
                ProducerRecord<String,byte[]> producerRecord = new ProducerRecord<>(topic,order.userId,valueByte);
                producer.send(producerRecord).get();
                Thread.sleep(1000);
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}