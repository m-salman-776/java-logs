package kafka.streams;

import Models.Order;
import Models.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.*;
import serds.JsonSerde;

import java.util.Properties;
@Slf4j
public class EnrichOrderWithUserInfo {
    private  static final ObjectMapper mapper = new ObjectMapper();
    public static void runEnrichOrderWithUserInfo(String source1,String source2 , String sink1){
        Properties properties = new Properties();
        properties.put(StreamsConfig.APPLICATION_ID_CONFIG,"order-enrich");
        properties.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");

        StreamsBuilder builder = new StreamsBuilder();
        KTable<String, User> userKTable = builder.table(source1, Materialized.with(Serdes.String(),new JsonSerde<>(User.class)));
        KStream<String, Order> orderKStream = builder.stream(source2, Consumed.with(Serdes.String(),new JsonSerde<>(Order.class)));
//        ValueJoiner<Order,User,Order> joiner = (order,user) -> {
//            if (user == null) return order;
//            return new Order(order.getOrderId(),user.getUserId(),order.getProductId(),order.getAmount());
//        };

        KStream<String,Order> enrichOrder = orderKStream.leftJoin(userKTable,
                (order,user)->{
                    if (user != null) order.userId = user.userId + "added from stream";
                    return order;
                },
                Joined.with(Serdes.String(),new JsonSerde<Order>(Order.class),new JsonSerde<User>(User.class)));
        enrichOrder.to(sink1,Produced.with(Serdes.String(),new JsonSerde<Order>(Order.class)));

        KafkaStreams streams = new KafkaStreams(builder.build(), properties);
        streams.start();
        Runtime.getRuntime().addShutdownHook(new Thread(streams::close));
    }
    public static void pushUserEvent(KafkaProducer<String,byte[]> producer,String topic , int start){
        for (int i = start ; i < start + 20;i++){
            User user = new User(""+i,"Some name :" + i,"address :"+i);
            try {
                byte[] val = mapper.writeValueAsBytes(user);
                pushData(producer,topic,val, user.userId);
            }catch (Exception e){
                log.error("Unable to map to bytes");
            }
        }
    }
    public static void pushOrderEvent(KafkaProducer<String,byte[]> producer,String topic , int start){
        for (int i = start ; i < start + 20;i++){
            Order order = new Order("orderId:"+i,""+i,"prodId:"+i,Math.random()*100);
            try {
                byte[] val = mapper.writeValueAsBytes(order);
                pushData(producer,topic,val,order.userId);
            }catch (Exception e){
                log.error("Unable to map to bytes");
            }
        }
    }
    private static void pushData(KafkaProducer<String,byte[]>producer, String topic , byte [] val,String key){
        ProducerRecord<String,byte[]> producerRecord = new ProducerRecord<>(topic,key,val);
        try {
            producer.send(producerRecord).get();
        }catch (Exception e){
            log.error("unable to produce {}",e.getMessage());
        }
    }
}
