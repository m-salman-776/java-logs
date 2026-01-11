package Project101.ElevatorSystemV2;

public class ElevatorButton extends HallButton implements Button{
    int floor;
    boolean pressed;
    public ElevatorButton(int floor){
        super();
        this.floor = floor;
    }
}
