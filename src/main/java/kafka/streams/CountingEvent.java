package kafka.streams;

import Models.ClickEvent;
import com.fasterxml.jackson.databind.JsonDeserializer;
import kafka.Producers;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.serialization.Serializer;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import serds.ClickEventDeserializer;
import serds.JsonSerializer;
import utility.ConfigManager;


import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class CountingEvent {
    public static void runCountEvent(String sourceTopic,String sinkTopic){
//        clickEventStream(sourceTopic, sinkTopic);
        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "key-count-application");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092"); // Update with your Kafka brokers
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());

//        properties.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG,"org.apache.kafka.common.serialization.Serdes$StringSerde");
//        properties.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG,"org.apache.kafka.common.serialization.Serdes$StringSerde");

        StreamsBuilder builder = new StreamsBuilder();

        // Read from the source topic
        KStream<String, String> sourceStream = builder.stream(sourceTopic);

        // Count occurrences by key
        sourceStream.groupByKey()
                .count()
                .toStream()
                .to(sinkTopic); // Output topic to store the count by key

        KafkaStreams streams = new KafkaStreams(builder.build(), props);
        streams.start();


    }

    private static void clickEventStream(String sourceTopic, String sinkTopic) {
        final Serializer<ClickEvent> eventSerializer = new JsonSerializer<ClickEvent>();
        final Deserializer<ClickEvent> eventDeserializer = new ClickEventDeserializer<ClickEvent>(ClickEvent.class);
        final Serde<ClickEvent> eventSerde = Serdes.serdeFrom(eventSerializer, eventDeserializer);
        Properties properties = getProperties();
//        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,"earliest");
        StreamsBuilder builder = new StreamsBuilder();
//        KStream<String,ClickEvent> source = builder.stream(sourceTopic, Consumed.with(Serdes.String(),eventSerde));
        KStream<String,String> source = builder.stream(sourceTopic);

//        KStream<String,ClickEvent> reKeyed = source.selectKey((oldKey,event) -> event.getUserId());
        source.groupByKey()
                .count()
                .toStream()
                .to(sinkTopic);

        // Start the Kafka Streams application
        try (KafkaStreams streams = new KafkaStreams(builder.build(), properties)){
            streams.start();
        }catch (Exception e){

            System.out.println(e);
            System.out.println("Exception");
        }
    }

    private static Properties getProperties() {
        Properties properties = new Properties();
        properties.put(StreamsConfig.COMMIT_INTERVAL_MS_CONFIG,300);
        properties.put(StreamsConfig.APPLICATION_ID_CONFIG, "counting");
        properties.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        properties.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG,"org.apache.kafka.common.serialization.Serdes$StringSerde");
        properties.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG,"org.apache.kafka.common.serialization.Serdes$StringSerde");
        return properties;
    }

    public static void pushTestData(String topic,int start)  {
        Properties prop = ConfigManager.getAllProperties();
        prop.put("value.serializer",JsonSerializer.class);
        KafkaProducer<String,ClickEvent> producer = Producers.getOrCreateClickEventProducer(prop);
        for (int i = start ; i+start < 10 ;i++){
            ClickEvent event = new ClickEvent("ip:"+i,"usedId:"+i,"client:"+i);
            ProducerRecord<String,ClickEvent> record = new ProducerRecord<>(topic,event.getIp(),event);
            try {
                RecordMetadata recordMetadata = producer.send(record).get();
                Thread.sleep(1000);
            }catch (InterruptedException | ExecutionException e){
                throw new RuntimeException(e);
            }

        }
//        Thread.sleep(100);
    }
}

