package Project101.ElevatorSystem.Dispatcher;

import Project101.ElevatorSystem.ElevatorController;
import Project101.ElevatorSystem.Request;

import java.util.List;

public class NearestDispatcher implements Dispatcher {
    @Override
    public ElevatorController findBestElevator(List<ElevatorController> controllers, Request request) {
        ElevatorController bestElevator = null;
        int max_distance = 1000000000;
        for (ElevatorController controller:controllers){
            // TODO make direction into consideration
            int distance = Math.abs(controller.elevator.currentFloor- request.floor);
            if (distance < max_distance){
                max_distance = distance;
                bestElevator = controller;
            }
        }
        return bestElevator;
    }
}
