package DesignPatterns.Command.VendorClasses;

public class Door {
    public void open(){
        System.out.println("Opening Door");
    }
    public void close(){
        System.out.println("Closing Door");
    }

    public void stop(){
        System.out.println("Stopping Actions");
    }
}
