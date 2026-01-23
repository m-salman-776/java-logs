package Concurrency;

import java.util.concurrent.Executors;

public class NamingThread {
    public static void main(String[] args) throws InterruptedException {
//        Executors.
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.printf("Thread going to sleep %s\n" , Thread.currentThread().getName());
                try {
                    Thread.sleep(4 * 60 * 1000);
                }catch (InterruptedException ex){
                    System.out.printf("Thread interrupted %s",Thread.currentThread().getName());
                }
            }
        },"Thread-01");

        t1.start();

//        Thread.
//        System.out.
        Thread.sleep(2 * 60 * 100);
        t1.interrupt();
        System.out.println("DONE");
    }
}
