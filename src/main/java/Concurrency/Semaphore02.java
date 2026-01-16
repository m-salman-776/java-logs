package Concurrency;

import java.util.concurrent.Semaphore;

public class Semaphore02 {
    Semaphore zero = new Semaphore(1);
    Semaphore odd = new Semaphore(0);
    Semaphore even = new Semaphore(0);
    public void printZero() throws InterruptedException {
        for (int i=1; i <= 10;i++){
            zero.acquire();
            System.out.printf("%d ",0);
            if (i % 2 == 1){
                odd.release(1);
            }else {
                even.release(1);
            }
        }
    }
    public void printOdd() throws InterruptedException {
        for (int i=1; i <= 10;i+=2){
            odd.acquire();
            System.out.printf("%d ",i);
            zero.release(1);
        }
    }
    public void printEvent() throws InterruptedException {
        for (int i=2; i <= 10;i+=2){
            even.acquire();
            System.out.printf("%d ",i);
            zero.release(1);
        }
    }

    public static void main(String[] args) {
        Semaphore02 sm = new Semaphore02();
        new Thread(() -> {
            try {
                sm.printZero();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
        new Thread(() -> {
            try {
                sm.printEvent();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();

        new Thread(() -> {
            try {
                sm.printOdd();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }
}
