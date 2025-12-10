package Project101.ElevatorSystem.V2;

import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ElevatorSystem {
    Dispatcher dispatcher;
    Map<String,Elevator> elevatorMap;
    List<ElevatorController> elevatorControllerList;
    BlockingQueue<Request> requestQueue = new LinkedBlockingQueue<>();
    public ElevatorSystem(){
        this.dispatcher = new NearestDispatcher();
        new Thread(this::processRequest).start();
    }
    public void handleHallRequest(int floor,Direction direction){
        Request request = new Request(floor,direction);
        this.requestQueue.add(request);
    }
    public void processRequest(){
        while (true) {
            try {
                Request request = requestQueue.take();
                ElevatorController elevatorController = dispatcher.findBestElevator(elevatorControllerList, request);
                if (elevatorController != null){
                    elevatorController.addStop(request);
                }else{
                    this.requestQueue.add(request);
                    Thread.sleep(1000);
                }
            }catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
    public void handleCarRequest(int floor,String elevatorId){
        Elevator elevator = elevatorMap.get(elevatorId) ; // findElevator with this id;
        elevator.addInternalStop(floor);
    }
}
