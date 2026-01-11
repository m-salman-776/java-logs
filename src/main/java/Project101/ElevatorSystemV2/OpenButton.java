package Project101.ElevatorSystemV2;

public class OpenButton extends HallButton implements Button{
    DoorStatus status = DoorStatus.CLOSED;
    boolean pressed = false;
    public OpenButton(){
        super();
    }
    public void openDoor(){
        this.status = DoorStatus.OPEN;
        System.out.println("Opening Door");
    }
}
