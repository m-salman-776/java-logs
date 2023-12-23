package Concurrency;

import lombok.AllArgsConstructor;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class PubSub {
    public static void main(String []args){
        SharedBuffer sharedBuffer = new SharedBuffer(5);
        Thread producer = new Thread(new Producer(sharedBuffer));
        Thread consumer = new Thread(new Consumer(sharedBuffer));
        producer.start();
        consumer.start();
    }
}

class SharedBuffer{
    BlockingQueue<Message> queue ;

    public SharedBuffer(int size){
        queue = new ArrayBlockingQueue<>(size,false);
    }
    public void produce(Message message){
        queue.add(message);
    }
    public Message consume() throws InterruptedException {
        return queue.take();
    }
}
class Producer implements Runnable{
    SharedBuffer sharedBuffer;
    @Override
    public void run(){
        try {
            for(int i=0;i<10;i++){
                Message message = new Message("Name :" + i ,"Address :"+i);
                sharedBuffer.produce(message);
                Thread.sleep(1000);
                if (i % 5 == 0){
                    Thread.sleep(2000);
                }
            }
            System.out.println("producing done");
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    public Producer(SharedBuffer sharedBuffer){
        this.sharedBuffer = sharedBuffer;
    }
}

class Consumer implements Runnable{
    SharedBuffer sharedBuffer;
    @Override
    public void run() {
        try {
            while (true){
                Message message = sharedBuffer.consume();
                System.out.println(message);
                Thread.sleep(2000);
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    public Consumer(SharedBuffer sharedBuffer){
        this.sharedBuffer = sharedBuffer;
    }
}
@AllArgsConstructor
class Message {
    public String name;
    public String address;
    @Override
    public String toString(){
        return this.name + "#" + this.address;
    }
}
