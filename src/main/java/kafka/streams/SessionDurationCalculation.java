package kafka.streams;

import Models.UserSession.UserSession;
import Models.UserSession.UserSessionDuration;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.*;
import serds.JsonSerde;
import utility.ConfigManager;

import java.util.Properties;

public class SessionDurationCalculation {
    static ObjectMapper mapper = new ObjectMapper();

    public static void runUserSessionCalculation(String src, String sink){
        Properties properties = new Properties();
        properties.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG,"localhost:9092");
        properties.put(StreamsConfig.APPLICATION_ID_CONFIG,"user-session-duration");
        StreamsBuilder builder = new StreamsBuilder();
        JsonSerde<UserSession> session = new JsonSerde<>(UserSession.class);
        JsonSerde<UserSessionDuration> duration = new JsonSerde<>(UserSessionDuration.class);
//        KTable<String,UserSession> startEvents = builder.table(src, Materialized.with(Serdes.String(),session))
//                .filter((key,val) -> "start".equals(val.type));
//        KStream<String,UserSession> endEvents = builder.stream(src, Consumed.with(Serdes.String(),session))
//                .filter((key,val) -> "end".equals(val.type));
        KStream<String,UserSession> [] branches = builder.stream(src,Consumed.with(Serdes.String(),session)).branch(
                (key,val) -> "start".equals(val.type),
                (key, val) -> "end".equals(val.type)
        );
//        KStream<String,UserSession>[] branches = builder.stream(src,Consumed.with(Serdes.String(),session))
//                .split()
//                .branch((key,val) -> "start".equals(val.type))
//                .branch((key, value) -> "end".equals(value.type))
//                .branch((key, value) -> true);


//        KStream<String, UserSession>[] branches = builder.stream(src,Consumed.with(Serdes.String(),session)).split()
//                .branch((key, appearance) -> "drama".equals(appearance.type))
//                .branch((key, appearance) -> "fantasy".equals(appearance.type))
//                .branch((key, appearance) -> true)  // Other genres
//                .noDefaultBranch();

        KTable<String,UserSession> startEvents = branches[0].toTable(Materialized.with(Serdes.String(),session));
//      KTable<String,UserSession> startEvents = branches[0].groupByKey().reduce((aggValue, newValue) -> newValue);

        KStream<String,UserSession> endEvents = branches[1];

        ValueJoiner<UserSession,UserSession,UserSessionDuration> valueJoiner = (end,start) ->{
            if (start == null) return null;
            return new UserSessionDuration(start.userId,end.timestamp-start.timestamp);
        };

        KStream<String,UserSessionDuration> userSessionDurationKStream = endEvents.leftJoin(startEvents,valueJoiner);
        userSessionDurationKStream.to(sink,Produced.with(Serdes.String(),duration));
        pushActivity(src,100);
        KafkaStreams streams = new KafkaStreams(builder.build(),properties);
        streams.start();
        Runtime.getRuntime().addShutdownHook(new Thread(streams::close));
    }
    public static void pushActivity(String topic, int start){
        Properties properties = ConfigManager.getAllProperties();
        try (KafkaProducer<String,byte[]>producer = new KafkaProducer<>(properties)){
            for (int i = start ; i < start + 10 ; i++){
                int timestamp = (int)(Math.random()*10000+1);
                String userId = "id:"+i;
                byte[] sessionStart = mapper.writeValueAsBytes(new UserSession("id:"+i,"start",timestamp));
                byte [] sessionEnd = mapper.writeValueAsBytes(new UserSession("id:"+i,"end",(int)(timestamp+ Math.random()*10+1)));

                ProducerRecord<String,byte[]> sRecord = new ProducerRecord<>(topic,userId,sessionStart);
                producer.send(new ProducerRecord<>(topic,userId,sessionStart)).get();
                Thread.sleep(100);
                producer.send(new ProducerRecord<>(topic,userId,sessionEnd)).get();
            }
        }catch (Exception e){

        }
    }
}
