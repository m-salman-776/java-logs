package Project101.ResourcePool;
import Project101.ResourcePool.Resources.DBConnection;


import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) throws Exception {
        CountDownLatch latch = new CountDownLatch(3);
        CountDownLatch startupLatch = new CountDownLatch(2);
        Map<String,DBConnection> map = new ConcurrentHashMap<>();
        SemaphoreBasedPoolManager<DBConnection> poolManager =
                new SemaphoreBasedPoolManager<>(
                2,10,
                new DBConnectionResource()
        );

        Thread t1 = new Thread(()->{
           try {
               System.out.println("Requesting Resource by T1");
               DBConnection connection = poolManager.acquire();
               map.put("t1",connection);
               startupLatch.countDown();
               latch.countDown();
           }catch (Exception e){
               e.printStackTrace();
           }
        });

        Thread t2 = new Thread(()->{
            try {
                System.out.println("Requesting Resource by T2");
                DBConnection connection = poolManager.acquire();
                map.put("t2",connection);
                startupLatch.countDown();
                latch.countDown();
            }catch (Exception e){
                e.printStackTrace();
            }
        });

        Thread t3 = new Thread(()->{
            try {
                System.out.println("Releasing Resource by T2");
//                Thread.sleep(5 * 1000);
                DBConnection connection = map.get("t2");
                poolManager.release(connection);
                System.out.println("Released Resource by T2");
//                latch.countDown();
            }catch (Exception e){
                e.printStackTrace();
            }
        });

        Thread t4 = new Thread(()->{
            try {
                System.out.println("Requesting Resource by T4");
                DBConnection connection = poolManager.acquire();
                map.put("t4",connection);
                System.out.println("Requesting Resource Acquired by T4");
                latch.countDown();
            }catch (Exception e){
                e.printStackTrace();
            }
        });

        t1.start();
        t2.start();
        startupLatch.await();
        t4.start();

        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);
        executor.schedule(t3,3, TimeUnit.SECONDS);

        executor.shutdown();
        latch.await();
        System.out.println("DONE");
    }

}
