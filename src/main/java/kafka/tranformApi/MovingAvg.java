package kafka.tranformApi;

import com.fasterxml.jackson.databind.ObjectMapper;
import kafka.KafkaTest;
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
import org.apache.kafka.streams.state.KeyValueStore;
import org.apache.kafka.streams.state.StoreBuilder;
import org.apache.kafka.streams.state.Stores;
import utility.StreamUtility;

import java.time.Duration;
import java.util.LinkedList;
import java.util.Properties;

public class MovingAvg implements KafkaTest {
    ObjectMapper mapper = StreamUtility.getMapper();

    @Override
    public void run(String... args) {
        String src = args[0] , sink = args[1],stateStore = args[2];
        Properties properties = StreamUtility.getProperties("running-avg");
        StreamsBuilder builder = new StreamsBuilder();
        StoreBuilder<KeyValueStore<String, Double>> keyValueStoreBuilder =
                        Stores.keyValueStoreBuilder(
                            Stores.persistentKeyValueStore(stateStore),
                            Serdes.String(), Serdes.Double()
                        );
        builder.addStateStore(keyValueStoreBuilder);

        KStream<String,Double> kStream = builder.stream(src, Consumed.with(Serdes.String(),Serdes.Double()));
        kStream.groupByKey()
                .windowedBy(TimeWindows.ofSizeWithNoGrace(Duration.ofMinutes(10)).advanceBy(Duration.ofMinutes(1)));

        kStream.transform(()-> new MovingAvgTransform(stateStore,10),"s");
//        kStream.transform(() -> new MovingAvgTransform(stateStore, 10), "s");

        KafkaStreams stream = new KafkaStreams(builder.build(),properties);
        stream.start();
//        KStream<MyKey, MyValue> transformedStream = inputStream.transform(new MyTransformerSupplier());
//        KStream<String, String> transformedStream = inputStream.transform(MyTransformer::new);
//        KStream<String, String> transformedStream = inputStream.transform(() -> new MyTransformer());


    }


}


class MovingAvgTransform implements Transformer<String,Double,KeyValue<String,Double>>{
    ProcessorContext context ;
    String stateStore;
    LinkedList<Double> list;
    int maxSize;
    int totalSum ;
    KeyValueStore<String,Double>keyValueStore;

    public MovingAvgTransform(String stateStore,int prev){
        this.stateStore = stateStore;
        this.maxSize = prev;
        list = new LinkedList<>();
        totalSum = 0;
    }
    @Override
    public void init(ProcessorContext context) {
        this.context = context;
        keyValueStore =  context.getStateStore(this.stateStore); // (KeyValueStore<String, Long>)
    }

    @Override
    public KeyValue<String,Double> transform(String key, Double value) {
        double prevTotal = 0.0;
        if (this.keyValueStore.get(key) != null){
            prevTotal = this.keyValueStore.get(key);
        }
        prevTotal += value;
        if (list.size() > maxSize){
            prevTotal -= list.removeFirst();
        }
        double avg = prevTotal / list.size();
        this.keyValueStore.put(key,prevTotal);
        return new KeyValue<>(key, avg);
    }

    @Override
    public void close() {

    }
}
