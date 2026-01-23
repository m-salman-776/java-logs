package Concurrency;

import java.util.concurrent.*;

public class ThreadPoolExecutorDemo<T> {
    public static void main(String[] args) throws InterruptedException {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                1,
                4,
                10, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(5),
                new ThreadPoolExecutor.DiscardPolicy()
        );
        CompletionService<Integer> completionService =  new ExecutorCompletionService<>(executor);



        Callable<Integer> callable = () -> 1 + 10;

        completionService.submit(callable);

        Future<Integer> future = completionService.take();


        Runnable slowTask = () -> {
            String t = Thread.currentThread().getName();
            System.out.println("Started: " + t);

            try {
                Thread.sleep(10_000); // simulate slow work
            } catch (InterruptedException e) {
                System.out.println("Interrupted: " + t);
                Thread.currentThread().interrupt();
            }

            System.out.println("Finished: " + t);
        };

        for (int i=1; i<= 10;i++){
            try {
                executor.submit(slowTask);
            }catch (RejectedExecutionException ex){
                System.out.println("Task " + i +" Rejected \n"+ex.getMessage());
            }
        }
        System.out.println("DONE");
    }
}
