package kafka.streams;

import Models.AccTxn;
import Models.AccTxnSummary;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.*;
import org.apache.kafka.streams.processor.ProcessorContext;
import org.apache.kafka.streams.processor.StateStore;
import org.apache.kafka.streams.state.KeyValueStore;
import org.apache.kafka.streams.state.StoreBuilder;
import org.apache.kafka.streams.state.Stores;
import serds.JsonSerde;
import utility.StreamUtility;

import java.time.Duration;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Properties;

public class FraudDetection {
    static ObjectMapper mapper = StreamUtility.getMapper();
    public void runFraudDetector(String src,String sink){
        Properties properties = new Properties();
        properties.put(StreamsConfig.APPLICATION_ID_CONFIG,"fraud-detection");
        JsonSerde<AccTxn> txnJsonSerde = new JsonSerde<>(AccTxn.class);
        JsonSerde<AccTxnSummary> txnSummaryJsonSerde = new JsonSerde<>(AccTxnSummary.class);
        StreamsBuilder builder = new StreamsBuilder();
        StoreBuilder<KeyValueStore<String, Double>> keyValueStoreBuilder =
                Stores.keyValueStoreBuilder(
                        Stores.persistentKeyValueStore("myStoreName"),
                        Serdes.String(),
                        Serdes.Double()
                );

        builder.addStateStore(keyValueStoreBuilder);
        KStream<String,AccTxn> txnStream = builder.stream(src, Consumed.with(Serdes.String(),txnJsonSerde));
        txnStream.groupByKey()
                .windowedBy(TimeWindows.ofSizeAndGrace(Duration.ofHours(24),
                        Duration.ofHours(1)).advanceBy(Duration.ofHours(1)))
                .aggregate(
                        AccTxnSummary::new,
                        (key,txn,summary)->{
                            summary.totalCount += 1;
                            summary.totalAmount += txn.amount;
                            return summary;
                        },
                        Materialized.with(Serdes.String(),txnSummaryJsonSerde)
                ).toStream();
//                .transform(()-> new MovingAverageTransformer(),"state");

//        KStream<String, String> alerts = aggregates.toStream().transform(() -> new AverageComparatorTransformer(), "avgStore");






        KafkaStreams streams = new KafkaStreams(builder.build(),properties);
        streams.start();
        Runtime.getRuntime().addShutdownHook(new Thread(streams::close));
    }
}

class MovingAverageTransformer implements Transformer<String, AccTxnSummary, KeyValue<String, String>> {

    private ProcessorContext context;
    private KeyValueStore<String, Deque<Double>> dequeStore;
    private KeyValueStore<String, Double> totalStore;

    @Override
    public void init(ProcessorContext context) {
        this.context = context;
        dequeStore = (KeyValueStore) context.getStateStore("dequeStore");
        totalStore = (KeyValueStore) context.getStateStore("totalStore");
    }


    @Override
    public KeyValue<String, String> transform(String key, AccTxnSummary value) {
        Deque<Double> deque = dequeStore.get(key);
        if (deque == null) {
            deque = new LinkedList<>();
        }
        double total = totalStore.get(key) == null ? 0.0 : totalStore.get(key);

        // Add the current hour's transaction count to the end of the deque
        deque.addLast(value.getTotalCount());
        total += value.getTotalAmount();

        // If deque has more than 24 entries, remove the oldest and subtract from total
        if (deque.size() > 24) {
            total -= deque.removeFirst();
        }

        // Compute average
        double avg = total / deque.size();

        // Save state
        dequeStore.put(key, deque);
        totalStore.put(key, total);

        // Check if current transaction count is greater than twice the average
        if (value.getTotalCount() > 2 * avg) {
            return new KeyValue<>(key, "Alert: High transaction count detected!");
        }

        return null;  // No alert
    }

    @Override
    public void close() {}
}

