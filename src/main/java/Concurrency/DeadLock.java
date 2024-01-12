package Concurrency;

interface A{
    void m();
}
interface B{
    void m();
}
public class DeadLock implements A,B {
    @Override
    public void m() {

    }

    public static void main(String []args){

        ResourceA resourceA = new ResourceA(3);
        ResourceB resourceB = new ResourceB(4);
        Runnable r1 = new Runnable() {
            @Override
            public void run() {
                synchronized (resourceA){
                    System.out.println("Entered into block 0");
                    try {
                        Thread.sleep(100);
                    }catch (Exception e){
                        System.out.println(e.getStackTrace());
                    }
                    synchronized (resourceB){
                        System.out.println("block 1");
                    }
                }
            }
        };

        Runnable r2 = new Runnable() {
            @Override
            public void run() {
//                synchronized (resourceB){ // todo changed the order of resource access to resolve deadlock
                synchronized (resourceA){
                    System.out.println("Entered into block 2");
//                    synchronized (resourceA){
                        synchronized (resourceB){

                        System.out.println("block 3");
                    }
                }
            }
        };

        new Thread(r1).start();
        new Thread(r2).start();
    }
}

class ResourceA{
    int a;
    public ResourceA(int a){
        this.a = a;
    }
    void setA(){
        this.a = 1;
    }
    int getA(){
        return this.a;
    }
}
class ResourceB{
    int a;
    public ResourceB(int a){
        this.a = a;
    }
    void setA(){
        this.a = 1;
    }
    int getA(){
        return this.a;
    }
}
