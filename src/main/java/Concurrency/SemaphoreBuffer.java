package Concurrency;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

public class SemaphoreBuffer {
    private final Queue<Integer> queue = new LinkedList<>();
    private final ReentrantLock lock = new ReentrantLock(); // For data safety

    // TODO: Define two Semaphores
    // 1. 'spaces': Starts with 'capacity' permits (The buffer is empty)
    // 2. 'items':  Starts with 0 permits (No items yet)
    private final Semaphore spaces;
    private final Semaphore items;

    public SemaphoreBuffer(int capacity) {
        this.spaces = new Semaphore(capacity);
        this.items = new Semaphore(0);
    }

    public void produce(int item) throws InterruptedException {
        // 1. FLOW: Get permission to write (Wait if no space)
        // TODO: Acquire a 'space' permit
        spaces.acquire();

        // 2. SAFETY: Protect the list modification
        lock.lock();
        try {
            queue.add(item);
            System.out.println("Produced: " + item);
        } finally {
            lock.unlock();
        }

        // 3. SIGNAL: Tell consumers there is an item
        // TODO: Release an 'item' permit
        items.release(1);
    }

    public void consume() throws InterruptedException {
        // 1. FLOW: Get permission to read (Wait if no items)
        // TODO: Acquire an 'item' permit
        items.acquire();

        int item;

        // 2. SAFETY: Protect the list modification
        lock.lock();
        try {
            item = queue.poll();
            System.out.println("Consumed: " + item);
        } finally {
            lock.unlock();
        }

        // 3. SIGNAL: Tell producers there is space
        // TODO: Release a 'space' permit
        spaces.release(1);

    }

    public static void main(String[] args) throws InterruptedException {
        SemaphoreBuffer sb = new SemaphoreBuffer(5);
        Thread t1 = new Thread(()->{
            for (int i=1 ;i <= 5;i++){
                try {
                    sb.produce(i);
                }catch (InterruptedException ex){
                    throw new RuntimeException(ex);
                }
            }
        });

        Thread t2 = new Thread(()->{
            for (int i=1 ;i <= 5;i++){
                try {
                    sb.consume();
                }catch (InterruptedException ex){
                    throw new RuntimeException(ex);
                }
            }
        });
        t1.start();
        t2.start();
        t1.join();
        t2.join();
    }
}
