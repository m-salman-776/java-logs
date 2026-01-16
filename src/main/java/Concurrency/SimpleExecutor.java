package Concurrency;


import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class SimpleExecutor {
    BlockingQueue<Runnable> taskQueue = new LinkedBlockingQueue<>();
    public SimpleExecutor(int threadCount){
        for (int i=1; i <= threadCount ; i++){
            String threadName = "Worker-" + i;

            new Thread(() -> {
                // BUG FIX: The Infinite Loop
                while(true) {
                    try {
                        // 1. Wait for a task (Blocks if empty)
                        Runnable runnable = taskQueue.take();

                        // 2. Run the task
                        runnable.run();

                    } catch (InterruptedException e) {
                        // If the pool is shutting down, we exit the loop
                        System.out.println(threadName + " is stopping.");
                        break;
                    } catch (Exception ex) {
                        // CRITICAL: If a task crashes, don't kill the thread!
                        // Log it and continue to the next task.
                        System.err.println("Task failed: " + ex.getMessage());
                    }
                }
            }, threadName).start();
        }
    }
    public void submit(Runnable runnable){
        this.taskQueue.add(runnable);
    }

    public static void main(String[] args) {
        SimpleExecutor executor = new SimpleExecutor(3);
        for (int i=1; i<= 10;i++){
            final String taskId = "task-" + i;
            executor.submit(() -> {
                System.out.println(Thread.currentThread().getName() + " executing the task " + taskId);
                try {
                    Thread.sleep(5 * 1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }
}
