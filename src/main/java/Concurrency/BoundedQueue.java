package Concurrency;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

public class BoundedQueue <T>{
    private final Semaphore spaces ;
    private final Semaphore items ;
    private final ReentrantLock lock = new ReentrantLock();
    private final Queue<T> queue ;
    public BoundedQueue(int cap){
        spaces = new Semaphore(cap);
        items = new Semaphore(0);
        queue = new LinkedList<>();
    }
    public void add(T item){

        try {
            spaces.acquire();

            try {
                lock.lock();
                queue.add(item);
            }finally {
                lock.unlock();
            }

            items.release();
        }catch (InterruptedException ex){
            Thread.currentThread().interrupt();
        }
    }
    public T get(){
        try {
            items.acquire();
            T item;

            try{
                lock.lock();
                item =  this.queue.remove();
            }finally {
                lock.unlock();
            }

            spaces.release();
            return item;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return null;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        BoundedQueue <Integer> bq = new BoundedQueue<>(3);
        Thread t1 = new Thread(() -> {
            for (int i=1; i <= 10; i++){
                bq.add(i);
                System.out.printf("Sent %d\n",i);
            }
        });

        Thread t2 = new Thread(() -> {
            int i=10;
            while (--i > 0) {
                int item = bq.get();
                System.out.printf("received %d\n",item);
            }
        });

        t1.start();
        t2.start();

        t1.join();
        t2.join();
        System.out.println("DONE");
    }
}
