import Common.Address;
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
    public static void main(String []args){
        Address address = new Address(1,"dsdsdfs");
        String s = address.getLocation();
        System.out.println(s);
    }
}
