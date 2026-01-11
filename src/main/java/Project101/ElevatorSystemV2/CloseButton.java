package Project101.ElevatorSystemV2;

public class CloseButton extends HallButton implements Button{
    DoorStatus status;
    boolean pressed;
    public CloseButton(){
        super();
    }
    void closeDoor(){
        System.out.println("Closing Door");
    }
}
