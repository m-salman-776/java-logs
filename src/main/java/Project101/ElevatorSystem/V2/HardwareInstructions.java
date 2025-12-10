package Project101.ElevatorSystem.V2;

public class HardwareInstructions {
    public void moveElevator(String elevatorId, int floor, Direction direction){
        System.out.println("Moving elevator " + elevatorId + " to floor " + floor + " " + direction);
    }

    public void openDoor(String elevatorId, int floor){
        System.out.println("Opening door for elevator " + elevatorId + " at floor " + floor);
    }

    public void closeDoor(String elevatorId, int floor){
        System.out.println("Closing door for elevator " + elevatorId + " at floor " + floor);
    }
}
