package Concurrency;

public class Synchronisation {
    public static void main(String[] args) {
        Boolean flag = true;
        new Thread(()->{
            synchronized (flag){

            }
           while (flag) {
               try {
                   System.out.printf("First thread about to sleep");
                   Thread.sleep(3000);
                   System.out.printf("First thread woke up");
                   flag.wait();
               } catch (InterruptedException e) {
                   throw new RuntimeException(e);
               }
           }
        }).start();
        System.out.println("DONE");
    }
}
