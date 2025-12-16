package Project101.ElevatorSystem;

import Project101.ElevatorSystem.DataType.Direction;

public class HardwareInstructions {
    public void moveElevator(int elevatorId, int floor, Direction direction){
        System.out.println("Moving elevator " + elevatorId + " to floor " + floor + " " + direction);
    }

    public void openDoor(int elevatorId, int floor){
        System.out.println("Opening door for elevator " + elevatorId + " at floor " + floor);
    }

    public void closeDoor(int elevatorId, int floor){
        System.out.println("Closing door for elevator " + elevatorId + " at floor " + floor);
    }
}
