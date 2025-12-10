package Project101.ElevatorSystem.V2;
import java.util.PriorityQueue;

public class ElevatorController implements Runnable {
    HardwareInstructions hardwareInstructions = new HardwareInstructions();
    Elevator elevator;
    PriorityQueue<Integer> upRequests;
    PriorityQueue<Integer> downRequests;
    public ElevatorController(Elevator elevator){
        this.elevator = elevator;
    }
    public synchronized void  addStop(Request request){
        if (request.floor > this.elevator.currentFloor){
            this.upRequests.add(request.floor);
        }else{
            downRequests.add(request.floor);
        }
    }
    public void move(){
        // based on the optimized path move the elevator.
    }

    private void upRequests(){
        while (!this.upRequests.isEmpty() ){
            // System Handler;
            int nextFloor;
            synchronized (this){
                nextFloor = this.upRequests.poll();
            }
            hardwareInstructions.moveElevator(elevator.id, nextFloor, Direction.UP);
            this.elevator.currentFloor = nextFloor;
        }
        if (!this.downRequests.isEmpty()){
            this.elevator.elevatorStatus = ElevatorStatus.MOVING_DOWN;
        }else {
            this.elevator.elevatorStatus = ElevatorStatus.IDLE;
        }
    }
    private void downRequests(){
        while (!this.downRequests.isEmpty()){

            int nextFloor;
            synchronized (this){
                nextFloor = this.downRequests.poll();
            }
            hardwareInstructions.moveElevator(elevator.id, nextFloor, Direction.DOWN);
            this.elevator.currentFloor = nextFloor;
        }
        if (!this.upRequests.isEmpty()){
            this.elevator.elevatorStatus = ElevatorStatus.MOVING_DOWN;
        }else {
            this.elevator.elevatorStatus = ElevatorStatus.IDLE;
        }
    }

    @Override
    public void run() {
        while (true){
            if (this.elevator.elevatorStatus == ElevatorStatus.MOVING_UP || this.elevator.elevatorStatus == ElevatorStatus.IDLE){
                upRequests();
                downRequests();
            }else {
                downRequests();
                upRequests();
            }
        }
    }
}
