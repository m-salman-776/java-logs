package Concurrency;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class MissedSignal {
    public static void main(String[] args) throws InterruptedException {
        ReentrantLock lock = new ReentrantLock();
        Condition condition = lock.newCondition();
        Thread signaler = new Thread(new Runnable() {
            @Override
            public void run() {
                lock.lock();
                System.out.println("Sending Signal");
                condition.signal();
                lock.unlock();
            }
        });

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    lock.lock();
                    System.out.println("Receiving Signal");
                    condition.await();
                }catch (Exception as) {
                    System.out.println(as.toString());
                }
            }
        });
        thread.start();
        signaler.start();
        signaler.join();
        thread.join();

        System.out.println("Program Exiting.");
    }
}
