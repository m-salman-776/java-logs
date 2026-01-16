package Concurrency;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReentrantLockDemo{
    ReentrantLock lock = new ReentrantLock();
//    ReentrantLock lock = new ReentrantReadWriteLock();
    Condition consumerRoom = lock.newCondition();
    Condition producerRoom = lock.newCondition();
    int cap = 5;
    Queue<Integer> queue = new LinkedList<>();
    public ReentrantLockDemo(){

    }
    public void produce(int item)throws InterruptedException{
        lock.lock();
        try {
            while (queue.size() == cap){
                producerRoom.await();
            }
            queue.add(item);
            consumerRoom.signal();
        } finally {
            lock.unlock();
        }
    }
    public void consume() throws InterruptedException {
        lock.lock();
        try {
            while (queue.isEmpty()){
                consumerRoom.await();
            }
            int item = queue.poll();
            producerRoom.signal();
            System.out.println("consuming "+item);
        }finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ReentrantLockDemo demo = new ReentrantLockDemo();
        Thread t1 = new Thread(() -> {
            for (int i=1; i<= 10;i++){
                try {
                    demo.produce(i);
                } catch (InterruptedException ex){
                    throw new RuntimeException(ex);
                }
            }
        });
        Thread t2 = new Thread(() -> {
            for (int i=1; i<= 10;i++){
                try {
                    demo.consume();
                } catch (InterruptedException ex){
                    throw new RuntimeException(ex);
                }
            }
        });
        t1.start();
        t2.start();

        t1.join();
        t2.join();
        System.out.println("DINE");
    }
}

/*
*
* // You manually check the condition
lock.lock();
try {
    while (permits == 0) { // The "Check"
        condition.await(); // The "Wait"
    }
    permits--; // The "Act"
} finally {
    lock.unlock();
}
*
*
* Semaphore : take lock , while loop , await , act
*
*
*/
