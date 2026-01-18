package Concurrency;
import java.util.concurrent.*;
import java.util.function.Supplier;

public class TokenBucket <T> {
    private final BlockingQueue<T> queue;
    private final ScheduledExecutorService executorService;
    public TokenBucket(int size, Supplier<T> supplier,long period,TimeUnit timeUnit){
        queue = new ArrayBlockingQueue<>(size);
        executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(() -> {
           try {
               T token = supplier.get();
               if (this.addToken(token)){
                   System.out.println("Added token: " + token);
               }else{
                   System.out.println("Bucket full, discarded token: " + token);
               }
           }catch (Exception e) {
               e.printStackTrace();
           }
        },0,period,timeUnit);
    }
    public boolean addToken(T item){
        return this.queue.offer(item);
    }
    public T getToken() throws InterruptedException {
        return this.queue.take();
    }

    public void stop(){
        this.executorService.shutdown();
    }

    public static void main(String[] args) throws InterruptedException {
        TokenBucket<Integer> tokenBucket = new TokenBucket<>(3,() -> (int) (Math.random() * 100),2,TimeUnit.SECONDS);

        // Wait for the bucket to fill up to demonstrate overflow
        // Capacity is 3, rate is 2s. 10s wait ensures overflow.
        Thread.sleep(10000);

        for (int i=1; i<= 10;i++){
            int token = tokenBucket.getToken();
            System.out.println("Token requested " + token);
        }
        System.out.println("DONE Experiment");
        tokenBucket.stop();
    }
}
