package Project101.ElevatorSystemV2;

import java.util.PriorityQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ElevatorCar {
    int elevatorId;
    public int currentFloor;
    ElevatorPanel elevatorPanel ;
    Display display;
    Door door;
    public CarStatus status;
    public Direction direction;
    EmergencyButton emergencyButton;
    PriorityQueue<Integer> minHeap = new PriorityQueue<>();
    PriorityQueue<Integer> maxHeap = new PriorityQueue<>((a,b) -> Integer.compare(b,a));
    Logger logger = Logger.getLogger(ElevatorCar.class.getName());
    public ElevatorCar(int elevatorId, int floorCount){
        this.elevatorId = elevatorId;
        currentFloor = 0;
        this.emergencyButton = new EmergencyButton();
        this.elevatorPanel = new ElevatorPanel(floorCount);
        this.display = new Display();
        this.door = new Door();
        this.status = CarStatus.IDLE;
        this.direction = Direction.IDLE;
    }
    public synchronized void addRequest(int floor){
        if (floor > this.currentFloor) {
            this.minHeap.add(floor);
        }else{
            this.maxHeap.add(floor);
        }
        notifyAll();
    }
    public void processRequest(){
        while (true) {
            synchronized (this) {
                while (minHeap.isEmpty() && maxHeap.isEmpty()) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        return;
                    }
                }
            }

            if (this.direction == Direction.IDLE || this.direction == Direction.UP){
                processUpRequest(this.minHeap,Direction.UP.toString());
                processUpRequest(this.maxHeap,Direction.DOWN.toString());
            }else {
                processUpRequest(this.maxHeap,Direction.DOWN.toString());
                processUpRequest(this.minHeap,Direction.UP.toString());
            }
            this.status = CarStatus.IDLE;
        }
    }
    public void processUpRequest(PriorityQueue<Integer> priorityQueue,String direction){
        int nextFloor = 0;
        while (true) {
            synchronized (this) {
                if (priorityQueue.isEmpty()) {
                    break;
                }
                nextFloor = priorityQueue.poll();
            }

            logger.log(Level.FINE, "Moving elevator {0} to floor {1}", new Object[]{this.elevatorId, nextFloor});
            try {
                Thread.sleep(500); // Simulate travel time
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            logger.log(Level.FINE,"Elevator {0} reached to floor {1}",new Object[]{this.elevatorId,nextFloor});
            logger.log(Level.FINE,"Elevator {0} opens door at floor {1}",new Object[]{this.elevatorId,nextFloor});
            this.door.openDoor();
            logger.log(Level.FINE,"Elevator {0} closed door at floor {1}",new Object[]{this.elevatorId,nextFloor});
            System.out.println();
//            System.out.println("Elevator " + this.elevatorId + " processed floor " + nextFloor);
            this.door.openDoor();
            this.currentFloor = nextFloor; // Update current floor
            if (Direction.UP.toString().equals(direction)){
                this.direction = Direction.DOWN;
            }else{
                this.direction = Direction.UP;
            }
        }
    }
    public void updateCarStatus(CarStatus carStatus){
        this.status = carStatus;
    }
}
