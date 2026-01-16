package Concurrency;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class LogBatcher {
    Semaphore logCounter = new Semaphore(0);
    public void produceMessage(int i){
        System.out.println("message -> " + i);
        logCounter.release(1);
    }
    public void flushMessage(){
        try {
            logCounter.acquire(5);
            System.out.println("Flushing logs enter");
            Thread.sleep(5000);
            System.out.println("Done Flushing logs enter");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    public void flushMessageV2(){
        try {
            boolean fullPermit = logCounter.tryAcquire(5,2, TimeUnit.SECONDS);
            int batchSize = 0;
            if (fullPermit) {
                batchSize = 5;
            }else{
                batchSize = logCounter.drainPermits();
            }
            if (batchSize > 0){
                System.out.println("✅ Flushing batch of " + batchSize);
                Thread.sleep(1000);
                System.out.println("✅ Done.");
            }

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        LogBatcher logBatcher = new LogBatcher();
        new Thread(() -> {
            for (int i=1; i <= 12 ; i++){
                logBatcher.produceMessage(i);
            }
        }).start();
        new Thread(() -> {
            while (true) {
                logBatcher.flushMessageV2();
            }
        }).start();

        System.out.println("DONE");
    }
}
