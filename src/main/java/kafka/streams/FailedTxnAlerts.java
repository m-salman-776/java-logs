package kafka.streams;

import Models.TransactionAlerts.Alert;
import Models.TransactionAlerts.Status;
import Models.TransactionAlerts.Transaction;
import com.fasterxml.jackson.databind.ObjectMapper;
import kafka.Windows;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.errors.LogAndContinueExceptionHandler;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;
import org.apache.kafka.streams.kstream.TimeWindows;
import serds.JsonSerde;
import utility.ConfigManager;
import utility.StreamUtility;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class FailedTxnAlerts {
    static ObjectMapper mapper = StreamUtility.getMapper();
    public static void runFailedTxnAlert(String src, String sink){
        Properties properties = new Properties();
        properties.put(StreamsConfig.APPLICATION_ID_CONFIG,"failed-txn-alert");
        properties.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG,"localhost:9092");
        properties.put(StreamsConfig.DEFAULT_DESERIALIZATION_EXCEPTION_HANDLER_CLASS_CONFIG, LogAndContinueExceptionHandler.class.getName());


        JsonSerde<Transaction> txnSerdes = new JsonSerde<Transaction>(Transaction.class);
        JsonSerde<Alert> alertSerde = new JsonSerde<>(Alert.class);

        StreamsBuilder builder = new StreamsBuilder();
        KStream<String, Transaction> txnStream = builder.stream(src, Consumed.with(Serdes.String(),txnSerdes));

        TimeWindows windows = Windows.getWindow(4,1);
        txnStream.filter(((key, value) -> value.status == Status.FAILED))
                .groupByKey()
                .windowedBy(windows)
                .count()
                .filter(((key, value) -> value > 3))
                .mapValues((key,val) -> {
                    return new Alert(key.key(), val.intValue(),key.window().start(),key.window().end());
                })
                .toStream()
                .selectKey((key,val) -> key.key())
                .to(sink,Produced.with(Serdes.String(),alertSerde));

        pushTransactionDataForMultipleUsers(src);

        KafkaStreams streams = new KafkaStreams(builder.build(),properties);
        streams.start();
        Runtime.getRuntime().addShutdownHook(new Thread(streams::close));
    }
    public static void pushTransactionDataForMultipleUsers(String topic) {
        List<String> userIds = Arrays.asList("userId1","userId2","userId3","userId4");
        Properties properties = ConfigManager.getAllProperties();

        try (KafkaProducer<String, byte[]> producer = new KafkaProducer<>(properties)) {
            int currentTimestamp = (int) (System.currentTimeMillis() / 1000);  // current time in seconds

            for (String userId : userIds) {
                // push 4 failed transactions within a 5-minute window for each user
                for (int i = 0; i < 4; i++) {
                    Transaction transaction = new Transaction();
                    transaction.userId = userId;
                    transaction.status = Status.FAILED;
                    transaction.timestamp = currentTimestamp + (i * 60);  // adding 1 minute for each transaction
                    byte[] val = mapper.writeValueAsBytes(transaction);
                    producer.send(new ProducerRecord<>(topic, transaction.userId, val)).get();
                }

                // push 2 success transactions for the same user outside the 5-minute window
                for (int i = 0; i < 2; i++) {
                    Transaction transaction = new Transaction();
                    transaction.userId = userId;
                    transaction.status = Status.SUCCESS;
                    transaction.timestamp = currentTimestamp + 360 + (i * 60);  // adding 6 minutes from the start, then 1 minute for each transaction
                    byte[] val = mapper.writeValueAsBytes(transaction);
                    producer.send(new ProducerRecord<>(topic, transaction.userId, val)).get();
                }
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
