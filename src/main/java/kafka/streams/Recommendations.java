package kafka.streams;

import Models.UserSession.ViewPurchase.ViewPurchase;
import Models.UserSession.ViewPurchase.ViewPurchasePair;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.*;
import serds.JsonSerde;
import utility.StreamUtility;

import java.time.Duration;
import java.util.Properties;

public class Recommendations {
    static ObjectMapper mapper = StreamUtility.getMapper();
    public static void runRecommendation(String src,String sink){
        Properties properties = StreamUtility.getProperties("product-recommendation");
        StreamsBuilder builder = new StreamsBuilder();
        JsonSerde<ViewPurchase> serde = new JsonSerde<>(ViewPurchase.class);
        JsonSerde<ViewPurchasePair> serde1 = new JsonSerde<>(ViewPurchasePair.class);

        KStream<String, ViewPurchase> [] branches = builder.stream(src, Consumed.with(Serdes.String(),serde)).branch(
                        (key, value) -> "view".equals(value.type),
                        ((key, value) -> true)
                );
        KStream<String,ViewPurchase> purchaseKStream = branches[1];
        KStream<String,ViewPurchase> viewPurchaseKStream = branches[0];
        ValueJoiner<ViewPurchase,ViewPurchase, ViewPurchasePair> joiner = (v1,v2) -> {
            return new ViewPurchasePair(v1.productId,v2.productId);
        };

        JoinWindows joinWindows = JoinWindows.ofTimeDifferenceWithNoGrace(Duration.ofMinutes(3));

        KStream<String,ViewPurchasePair> viewPurchaseKStream1 = viewPurchaseKStream.join(
                purchaseKStream,
                joiner,
                joinWindows,
                StreamJoined.with(Serdes.String(),serde,serde));


        KTable<ViewPurchasePair,Long> purchasePairLongKStream = viewPurchaseKStream1
                .groupBy((key,val) -> val
                ,Grouped.with(serde1,serde1))
                .count();

        purchasePairLongKStream
                .toStream()
                .to(sink,Produced.with(serde1,Serdes.Long()));




        KafkaStreams streams = new KafkaStreams(builder.build(),properties);
        streams.start();
        Runtime.getRuntime().addShutdownHook(new Thread(streams::close));
    }

}
