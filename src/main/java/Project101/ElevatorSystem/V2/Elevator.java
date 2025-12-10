package Project101.ElevatorSystem.V2;



import lombok.Getter;

import java.util.Set;
@Getter
public class Elevator {
    String id;
    int currentFloor;
    Direction direction;
    ElevatorStatus elevatorStatus;
    Set<Integer> internalStop;

    public Elevator(){

    }

    public void addInternalStop(int currentFloor){
        this.internalStop.add(currentFloor);
    }

    public void openDoor(){
        // hardware
    }
    public void closeDoor(){

    }
    public void move(Direction direction){
        this.currentFloor += direction == Direction.DOWN ? 1 : -1;
    }
}
