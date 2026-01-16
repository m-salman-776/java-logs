package Concurrency;

import java.util.LinkedList;
import java.util.Queue;

public class WaitNotify {
    final Queue<Integer> queue = new LinkedList<>();
    final int capacity = 1;
    public WaitNotify(){

    }
    public synchronized void produce(int item) throws InterruptedException {
        while (queue.size() == capacity){
            System.out.println("Queue is full");
            this.wait();
        }
        queue.add(item);
        this.notify();
    }
    public synchronized void consume() throws InterruptedException {
        while (queue.isEmpty()){
            System.out.println("Queue is full");
            wait();
        }
        int item = queue.poll();
        notify();
        System.out.println("value polled from queue "+item);
    }

    public static void main(String[] args) throws InterruptedException {
        WaitNotify waitNotify = new WaitNotify();
        Thread t1 = new Thread(() -> {
            for (int i=1; i<= 5; i++){
                try {
                    waitNotify.produce(i);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        Thread t2 = new Thread(() -> {
            for (int i=1; i<= 5; i++){
                try {
                    waitNotify.consume();
                }catch (InterruptedException e){
                    throw new RuntimeException(e);
                }
            }
        });
        t1.start();
        t2.start();

        t1.join();
        t1.join();
    }
}
