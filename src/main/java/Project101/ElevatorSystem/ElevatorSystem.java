package Project101.ElevatorSystem;

import Project101.ElevatorSystem.DataType.Direction;
import Project101.ElevatorSystem.Dispatcher.Dispatcher;
import Project101.ElevatorSystem.Dispatcher.NearestDispatcher;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class ElevatorSystem {
    Dispatcher dispatcher;
    Map<Integer,Elevator> elevatorMap = new HashMap<>();
    List<ElevatorController> elevatorControllerList;
    BlockingQueue<Request> requestQueue = new LinkedBlockingQueue<>();
    public ElevatorSystem(Dispatcher dispatcher){
        this.dispatcher = dispatcher;
        new Thread(this::processRequest).start();
    }
    public void handleHallRequest(int floor, Direction direction){
        Request request = new Request(floor,direction);
        this.requestQueue.add(request);
    }
    private void processRequest(){
        while (true) {
            try {
                Request request = requestQueue.poll(1,TimeUnit.SECONDS);
                if (request == null) continue;
                ElevatorController elevatorController = dispatcher.findBestElevator(elevatorControllerList, request);
                if (elevatorController != null){
                    elevatorController.elevator.addStop(request);
                }else{
                    this.requestQueue.add(request);
                    Thread.sleep(1000);
                }
            }catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
    public void handleCarRequest(int floor,int elevatorId){

        Elevator elevator = elevatorMap.get(elevatorId) ; // findElevator with this id;
        Direction  direction = Direction.DOWN;
        if (elevator.currentFloor > floor && elevator.direction == Direction.UP){
            direction = Direction.UP;
        }
        Request request = new Request(floor,direction);
        elevator.addStop(request);
    }
}
