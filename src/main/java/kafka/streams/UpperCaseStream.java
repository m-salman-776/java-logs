package kafka.streams;

import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;

import java.util.Properties;

public class UpperCaseStream {
    public static void runUpCaseStream(){
        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "uppercase-app");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, "org.apache.kafka.common.serialization.Serdes$StringSerde");
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, "org.apache.kafka.common.serialization.Serdes$StringSerde");
        StreamsBuilder builder = new StreamsBuilder();

        KStream<String, String> source = builder.stream("input-topic");
        source.mapValues(value -> value.toUpperCase()).to("output-topic");

        try(KafkaStreams streams = new KafkaStreams(builder.build(), props);) {
            streams.start();
        }
        System.out.println("DONE");
    }
}
