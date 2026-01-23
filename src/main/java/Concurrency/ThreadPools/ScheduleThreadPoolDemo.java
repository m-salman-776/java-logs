package Concurrency.ThreadPools;
import java.util.Map;
import java.util.concurrent.*;

public class ScheduleThreadPoolDemo {
    ScheduledThreadPoolExecutor scheduler;
    Map<Integer,Integer> map = new ConcurrentHashMap<>();
    public ScheduleThreadPoolDemo() {
        scheduler = new ScheduledThreadPoolExecutor(1);
//        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
//        scheduledExecutorService.schedule()
    }
    public void runSomeTask(int val) throws InterruptedException {
        scheduler.schedule(() -> callBack(val),1, TimeUnit.SECONDS);
    }
    public synchronized void callBack(int key){
        this.map.put(key,this.map.getOrDefault(key,0)+1);
        System.out.println("Printing "+key);
    }

    public static void main(String[] args) throws InterruptedException {
        ScheduleThreadPoolDemo scheduleThreadPoolDemo = new ScheduleThreadPoolDemo();
        scheduleThreadPoolDemo.runSomeTask(1);
        Thread.sleep(4 * 1000);
        System.out.println("done");
        System.out.println(scheduleThreadPoolDemo.map);
    }
}