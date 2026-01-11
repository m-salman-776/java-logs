package Project101.ElevatorSystem;
import Project101.ElevatorSystem.DataType.Direction;
import Project101.ElevatorSystem.DataType.ElevatorStatus;

public class ElevatorController implements Runnable {
    public Elevator elevator;
    public ElevatorController(Elevator elevator){
        this.elevator = elevator;
    }
    public void move(){
        // based on the optimized path move the elevator.
        
    }

    private void upRequests(){
        while (!this.elevator.upRequestsMinHeap.isEmpty() ){
            int nextFloor;
            synchronized (this.elevator){
                nextFloor = this.elevator.upRequestsMinHeap.poll();
            }
            this.elevator.move(nextFloor,Direction.UP);
        }
        if (!this.elevator.downRequestsMaxHeap.isEmpty()){
            this.elevator.elevatorStatus = ElevatorStatus.MOVING_DOWN;
        }else {
            this.elevator.elevatorStatus = ElevatorStatus.IDLE;
        }
    }
    private void downRequests(){
        while (!this.elevator.downRequestsMaxHeap.isEmpty()){

            int nextFloor;
            synchronized (this.elevator){
                nextFloor = this.elevator.downRequestsMaxHeap.poll();
            }
            this.elevator.move(nextFloor,Direction.DOWN);
        }
        if (!this.elevator.upRequestsMinHeap.isEmpty()){
            this.elevator.elevatorStatus = ElevatorStatus.MOVING_DOWN;
        }else {
            this.elevator.elevatorStatus = ElevatorStatus.IDLE;
        }
    }

    @Override
    public void run() {
        while (true){
            synchronized (this.elevator) {
                while (this.elevator.upRequestsMinHeap.isEmpty() && this.elevator.downRequestsMaxHeap.isEmpty()) {
                    try {
                        // Wait until the elevator notifies us (when a request is added)
                        this.elevator.wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        return;
                    }
                }
            }
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
