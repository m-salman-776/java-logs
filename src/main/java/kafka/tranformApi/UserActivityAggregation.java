package kafka.tranformApi;

import Models.UserActivity;
import com.fasterxml.jackson.databind.ObjectMapper;
import kafka.Windows;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.TimeWindows;
import org.apache.kafka.streams.kstream.Transformer;
import org.apache.kafka.streams.processor.ProcessorContext;
import serds.JsonSerde;
import utility.StreamUtility;

import java.time.Duration;
import java.util.Properties;

public class UserActivityAggregation {
    ObjectMapper mapper = StreamUtility.getMapper();
    public static void runAgg(String ...args){
        Properties properties = StreamUtility.getProperties("user-activity-count");
        String src = args[0];

        TimeWindows windows = TimeWindows.ofSizeWithNoGrace(Duration.ofHours(1)).advanceBy(Duration.ofHours(1));

        StreamsBuilder builder = new StreamsBuilder();
        JsonSerde<UserActivity> userActivityJsonSerde = new JsonSerde<>(UserActivity.class);
        KStream<String, UserActivity> kafkaStreams = builder.stream(src, Consumed.with(Serdes.String(),userActivityJsonSerde));

//        kafkaStreams.groupByKey()
//                .windowedBy(windows)
//                .
//
//        KafkaStreams streams = new KafkaStreams(builder.build(),properties);
//        streams.start();
    }
}


class Tns implements Transformer<String,UserActivity, KeyValue<String,Integer>>{



    @Override
    public void init(ProcessorContext context) {

    }

    @Override
    public KeyValue<String, Integer> transform(String key, UserActivity value) {
        return null;
    }

    @Override
    public void close() {

    }
}
