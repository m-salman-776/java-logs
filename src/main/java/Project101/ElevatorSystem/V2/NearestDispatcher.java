package Project101.ElevatorSystem.V2;

import java.util.List;
import java.util.Map;

public class NearestDispatcher implements Dispatcher{
    @Override
    public ElevatorController findBestElevator(List<ElevatorController> controllers, Request request) {
        ElevatorController bestElevator = null;
        int max_distance = 1000000000;
        for (ElevatorController controller:controllers){
            int distance = Math.abs(controller.elevator.currentFloor- request.floor);
            if (distance < max_distance){
                max_distance = distance;
                bestElevator = controller;
            }
        }
        return bestElevator;
    }
}
