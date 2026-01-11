package Project101.ElevatorSystemV2;

public class Door {
    DoorStatus status ;
    public Door(){
        status = DoorStatus.CLOSED;
    }
    public void openDoor(){
        this.status = DoorStatus.OPEN;
//        System.out.println("Opening Door");
    }

    public void closeDoor(){
        this.status = DoorStatus.CLOSED;
//        System.out.println("Closing Door");
    }

}
