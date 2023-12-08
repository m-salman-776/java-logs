import Models.ClickEvent;
import Models.Order;
import kafka.streams.PageViewCount;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.common.protocol.types.Field;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StoreQueryParameters;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.springframework.beans.factory.annotation.Autowired;
import serds.JsonSerializer;
import utility.ConfigManager;

import java.util.Map;
import java.util.Properties;
import java.util.concurrent.CompletableFuture;

import static kafka.streams.EnrichOrderWithUserInfo.pushOrderEvent;
import static kafka.streams.EnrichOrderWithUserInfo.pushUserEvent;
import static kafka.streams.OrderProcessingApp.produceOrderEvent;
import static kafka.streams.PageViewCount.pushViewCount;
import static kafka.streams.SessionDurationCalculation.pushActivity;

public class main {
    static KafkaStreams streams;

    static {
        streams = PageViewCount.streams;
    }
    public void setStreams(KafkaStreams streams) {
        main.streams = streams;
    }
    public static void main(String []args){
        String topic = "src_1";
        pushActivity(topic,100);
//        getState();
//        pushViewCount(20,topic);
//        readStateStore("r");
//        sendOrderEvent(topic,100);
//        Properties properties = ConfigManager.getAllProperties();
//        KafkaProducer<String, byte[]> producer = new KafkaProducer<>(properties);
//        CompletableFuture<Void> orderEvent = CompletableFuture.supplyAsync(()->{
//            pushOrderEvent(producer,"src_2",20);
//            return null;
//        });
//        CompletableFuture<Void> userEvent = CompletableFuture.supplyAsync(()->{
//            pushUserEvent(producer,"src_1",10);
//            return null;
//        });
//        CompletableFuture<Void> all = CompletableFuture.allOf(orderEvent,userEvent);
//        all.join();
    }
    public static void getState(){
        ReadOnlyKeyValueStore<String, Long> keyValueStore =
                PageViewCount.streams.store(StoreQueryParameters.fromNameAndType("page-view-count-app", QueryableStoreTypes.keyValueStore()));

        String pageIdToQuery = "pageId86";
        Long countForPage = keyValueStore.get(pageIdToQuery);
        System.out.println("Count for page: " + countForPage);
    }



    public void sendOrderEvent(String topic , int start){

        Properties properties = ConfigManager.getAllProperties();
//        properties.put("value.serializer", JsonSerializer.class);
        try (KafkaProducer<String, byte[]> producer = new KafkaProducer<>(properties)){
            produceOrderEvent(producer,start,topic);
        }
    }
}
