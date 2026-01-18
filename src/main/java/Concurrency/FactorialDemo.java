package Concurrency;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class FactorialDemo implements Callable<Long> {
    long number ;
    public FactorialDemo(long num){
        this.number = num;
    }

    @Override
    public Long call() throws Exception {
        if (this.number == 15) throw new RuntimeException("something went wrong\n");
        long ans = 1;
        for (int i=2; i<= this.number;i++){
            ans *= i;
        }
        return ans;
    }

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        List<Future<Long>> futures = new ArrayList<>();
        for (int i=10; i <= 20; i++){
            Future<Long> future = executorService.submit(new FactorialDemo(i));
            futures.add(future);
        }
        for (Future<Long> future : futures){
            try {
                System.out.printf("result : %d\n",future.get());
            } catch (ExecutionException | InterruptedException e) {
                System.out.printf("%s",e.getCause());
//                throw new RuntimeException(e);
            }
        }
        executorService.shutdown();
    }
}
