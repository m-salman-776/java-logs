package Project101.ElevatorSystem;
import Project101.ElevatorSystem.DataType.Status;
import Project101.ElevatorSystem.DataType.Location;

import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;


public class Elevator {
    int currentFloor;
    Status status;
    Queue<Request> upRequests; // min-Heap;
    Queue<Request> downRequests; // max-Heap;
    Set<Integer> stopSet;
    public Elevator(int currentFloor, Status direction){
        this.currentFloor = currentFloor;
        this.status = direction;
        upRequests = new PriorityQueue<>((a,b) -> {return a.getFloor()-b.getFloor();});
        downRequests = new PriorityQueue<>((a,b) -> {return b.getFloor()-a.getFloor();});
        stopSet = new HashSet<>();
    }
    public boolean setDirection(Status status){
        this.status = status;
        return true;
    }
    private void stopAndOpenLift(int currentFloor){
        System.out.println("Lift will be opening at :"+currentFloor);
    }
    public boolean addRequest(Request request){
        if (request.getLocation() == Location.INSIDE){
            if (request.getFloor() < this.currentFloor) // mean want to go down;
            this.downRequests.add(request);
            else this.upRequests.add(request);
        }else {
            stopSet.add(request.getFloor());
        }
        return true;
    }
    public void run(){
        while (!this.upRequests.isEmpty() || !this.downRequests.isEmpty()){
            this.processRequests();
        }
        this.status = Status.IDLE;
    }
    private void processRequests(){
        if(this.status == Status.MOVING_UP || this.status == Status.IDLE){
            upRequests();
            downRequests();
        }else {
            downRequests();
            upRequests();
        }
    }
    private void upRequests(){
        while (!this.upRequests.isEmpty() ){
            Request request = this.upRequests.poll();
            // System Handler;
            this.currentFloor = request.getFloor();
        }
        if (!this.downRequests.isEmpty()){
            this.status = Status.MOVING_DOWN;
        }else {
            this.status = Status.IDLE;
        }
    }
    private void downRequests(){
        while (!this.downRequests.isEmpty()){
            Request request = this.downRequests.poll();
            // System Hanlde
            this.currentFloor = request.floor;
        }
        if (!this.upRequests.isEmpty()){
            this.status = Status.MOVING_DOWN;
        }else {
            this.status = Status.IDLE;
        }
    }
}
