import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
public class Common {
    public static void main(String[] args) {
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);
        Stack<Integer> count = new Stack<>();
        executor.scheduleAtFixedRate(()->{
            LocalDateTime dateTime = LocalDateTime.now();
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formatedDateTime = dateTimeFormatter.format(dateTime);
            System.out.println(formatedDateTime);
        },0,1, TimeUnit.SECONDS);
        System.out.println("DONE");
    }
}
