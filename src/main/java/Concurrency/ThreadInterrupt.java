package Concurrency;

public class ThreadInterrupt {

    public static void main(String[] args) throws InterruptedException {
        Thread sleepy = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("Sleeping thread");
                    Thread.sleep(1000 * 600);
                }catch (InterruptedException ex){
                    System.out.println("The interrupt flag is cleared : " + Thread.interrupted() + " " + Thread.currentThread().isInterrupted());
//                    Thread.currentThread().interrupt();
                    System.out.println("Oh someone woke me up ! ");
                    System.out.println("The interrupt flag is set now : " + Thread.currentThread().isInterrupted() + " " + Thread.interrupted());

                }
                catch (Exception ex) {
                    System.out.println("Interrupted ->" + ex.getCause());
                }
            }
        });
        sleepy.start();

        System.out.println("About to wake up the sleepy thread ...");
        sleepy.interrupt();
        System.out.println("Woke up sleepy thread ...");

        sleepy.join();
    }
}
