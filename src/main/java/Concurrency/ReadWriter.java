package Concurrency;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.*;

public class ReadWriter {

    static int check(char []str){
        Stack<Character> st = new Stack();
        for (char c : str){
            while (!st.isEmpty() && st.peek() > c) {
                System.out.println(st.pop() +" " + c);
            }
            st.add(c);
        }
        if (st.size() == str.length) return 1;
        return 0;
    }
    public static void main(String[]args){
        int t = check("0000011110".toCharArray());
        CountDownLatch latch = new CountDownLatch(1);
        SharedResource sharedResource = new SharedResource(latch);

//        Runnable runnable = ()->{
//          System.out.println("Lamda Runnable");
//          sharedResource.read();
//        };

        for (int i=0;i<100;i++){
            new Thread(sharedResource::read).start();
        }

        for(int i=0;i<100;i++){
            int finalI = i;
            new Thread(()->{
                sharedResource.write(finalI + (int)(Math.random() * 100));
            }).start();
        }
        sharedResource.latch.countDown();
    }
}


class SharedResource {
    Queue<Integer> queue;
    Lock readLock;
    Lock writeLock;
    Condition readCondition;
    Condition writeCondition;
    CountDownLatch latch;

    SharedResource(CountDownLatch latch) {
        queue = new LinkedList<>();
        this.latch = latch;
        readLock = new ReentrantLock(true);
        writeLock = new ReentrantLock(true);
        readCondition = readLock.newCondition();
        writeCondition = writeLock.newCondition();
    }

    public Integer read() {
        try {
            latch.await();

            readLock.lock();
            try {
                while (queue.isEmpty()) {
                    // Wait until there is data available
                    try {
                        readCondition.await();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        return null;  // Handle interruption by returning null (or handle as needed)
                    }
                }

                Integer data = queue.poll();
                System.out.println(Thread.currentThread().getName() + " : Reading , data => " + data);
                return data;
            } finally {
                readLock.unlock();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return null;  // Handle interruption by returning null (or handle as needed)
        }
    }

    void write(int val) {
        try {
            latch.await();  // Wait for the signal to start
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        writeLock.lock();
        try {
            queue.add(val);
            readCondition.signal();  // Signal readers
            System.out.println(Thread.currentThread().getName() + " : Writing , data => " + val);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            writeLock.unlock();
        }
    }
}