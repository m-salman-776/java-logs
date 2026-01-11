package Project101.ElevatorSystem;



import Project101.ElevatorSystem.DataType.Direction;
import Project101.ElevatorSystem.DataType.ElevatorStatus;
import lombok.Getter;

import java.util.PriorityQueue;
import java.util.Set;
@Getter
public class Elevator {
    public int id;
    public int currentFloor;
    public Direction direction;
    public ElevatorStatus elevatorStatus;
    HardwareInstructions hardwareInstructions = new HardwareInstructions();
    PriorityQueue<Integer> upRequestsMinHeap;
    PriorityQueue<Integer> downRequestsMaxHeap;

    public Elevator(int id){
        this.id = id;
        downRequestsMaxHeap = new PriorityQueue<>((a, b) -> b.compareTo(a));
        upRequestsMinHeap = new PriorityQueue<>();
    }
    public synchronized void  addStop(Request request){
        if (request.floor > this.currentFloor){
            this.upRequestsMinHeap.add(request.floor);
        }else{
            downRequestsMaxHeap.add(request.floor);
        }
        notifyAll();
    }

    public void openDoor(){
        this.hardwareInstructions.openDoor(this.id,this.currentFloor);
    }
    public void closeDoor(){
        this.hardwareInstructions.closeDoor(this.id,this.currentFloor);
    }
    public void move(int floor,Direction direction){
        openDoor();
        closeDoor();
        hardwareInstructions.moveElevator(this.id,floor,direction);
        this.currentFloor = floor;
        openDoor();
        closeDoor();

    }
}
