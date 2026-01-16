package Concurrency;

public class ClassLockBottleneck {

    private final Object key1 = new Object();
    private final Object key2 = new Object();

    // Uses the lock of 'this' instance
    public void printA() {
        synchronized (key1){
            System.out.println("Thread A: Started A (Holding 'this' key)");
            try { Thread.sleep(3000); } catch (Exception e) {}
            System.out.println("Thread A: Finished A");
        }
    }

    // Also uses the lock of 'this' instance
    public void printB() {
        synchronized (key2) {
            System.out.println("Thread B: Waiting for 'this' key...");
            System.out.println("Thread B: Started B");
            try { Thread.sleep(3000); } catch (Exception e) {}
            System.out.println("Thread B: Finished B");
        }
    }

    public static void main(String[] args) {
        ClassLockBottleneck obj = new ClassLockBottleneck();

        Thread t1 = new Thread(() -> obj.printA());
        Thread t2 = new Thread(() -> obj.printB());

        t1.start();
        t2.start();
    }
}