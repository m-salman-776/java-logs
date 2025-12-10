package Project101.ElevatorSystem;

import java.util.ArrayList;
import java.util.List;

public class ElevatorControllers {
    List<Elevator> elevators;
    public ElevatorControllers(){
        elevators = new ArrayList<>();
    }
    public void addElevator(Elevator elevator){
        this.elevators.add(elevator);
    }

}
