package kafka.streams;

import Models.PageView;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.protocol.types.Field;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StoreQueryParameters;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.*;
import org.apache.kafka.streams.state.KeyValueStore;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import serds.JsonSerde;
import utility.ConfigManager;

import java.util.Properties;

public class PageViewCount {
    static ObjectMapper mapper  = new ObjectMapper();
    public static KafkaStreams streams;
//    static {
//        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
//    }
    public static void readStateStore(String pageId){
        while (true) {
            if (streams.state() == KafkaStreams.State.RUNNING) {
                break;
            }
            try {
                Thread.sleep(100);  // Sleep for a short period before checking again
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        String pageIdToQuery = "pageId86";
        ReadOnlyKeyValueStore<String, Long> keyValueStore = streams.store(StoreQueryParameters.fromNameAndType("page-view-count-app", QueryableStoreTypes.keyValueStore()));

//        ReadOnlyKeyValueStore<String, Long> keyValueStore =
//                streams.store("page-view-counts-store", QueryableStoreTypes.keyValueStore());

        Long countForPage = keyValueStore.get(pageIdToQuery);
        System.out.println(keyValueStore);
    }
    public static void runPageViewCount(String src,String sink){
        Properties properties = new Properties();
        properties.put(StreamsConfig.APPLICATION_ID_CONFIG,"page-view-count-app");
        properties.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG,"localhost:9092");
        JsonSerde<PageView> pageViewJsonSerde = new JsonSerde<PageView>(PageView.class);
        StreamsBuilder builder = new StreamsBuilder();
        KStream<String,PageView> viewKStream = builder.stream(src, Consumed.with(Serdes.String(),pageViewJsonSerde));
//        KTable<String,PageView> kTable = builder.table(src, Materialized.with(Serdes.String(),pageViewJsonSerde));


        // assuming that i am keying event by page id
        KTable<String,Long> viewCount = viewKStream.groupByKey().count(
                Materialized.<String, Long, KeyValueStore<Bytes, byte[]>>as("page-view-count-app")
                        .withKeySerde(Serdes.String())
                        .withValueSerde(Serdes.Long())
//                Materialized.with(
//                Serdes.String(),Serdes.Long())
        );
        viewCount.toStream().to(sink, Produced.with(Serdes.String(),Serdes.Long()));

        streams = new KafkaStreams(builder.build(),properties);
        pushViewCount((int) (Math.random() * 100),src);
        streams.start();
        Runtime.getRuntime().addShutdownHook(new Thread(streams::close));

    }

    public KafkaStreams makeStream(Properties properties,StreamsBuilder builder){
        KafkaStreams streams = new KafkaStreams(builder.build(),properties);
        return streams;
    }

    public static void pushViewCount(int start,String topic){
        Properties properties = ConfigManager.getAllProperties();
        try(KafkaProducer<String,byte[]> producer = new KafkaProducer<>(properties)){
            for (int i = start ; i < start + 10 ; i++){
                PageView pageView = new PageView("user:"+i,"pageId"+i);
                byte [] val = mapper.writeValueAsBytes(pageView);
                producer.send(new ProducerRecord<>(topic,pageView.pageId,val)).get();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
