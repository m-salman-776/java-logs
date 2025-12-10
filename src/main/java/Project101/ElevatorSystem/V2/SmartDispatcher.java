package Project101.ElevatorSystem.V2;

import java.util.List;
import java.util.Map;

public class SmartDispatcher implements Dispatcher{
    @Override
    public ElevatorController findBestElevator(List<ElevatorController> controllers, Request request) {
        ElevatorController bestElevator = null;
        int max_distance = 1000000000;
        for (ElevatorController controller:controllers){
            int distance = calculateCost(controller,request);
            if (distance < max_distance){
                max_distance = distance;
                bestElevator = controller;
            }
        }
        return bestElevator;
    }
    int calculateCost(ElevatorController controller,Request request){
        int currentFloor = controller.elevator.currentFloor;
        ElevatorStatus elevatorStatus = controller.elevator.getElevatorStatus();
        int distance = Math.abs(currentFloor-request.floor);
        if (controller.elevator.elevatorStatus == ElevatorStatus.IDLE) {
            return distance;
        }
        boolean movingToward = elevatorStatus == ElevatorStatus.MOVING_UP && currentFloor < request.floor
                || elevatorStatus == ElevatorStatus.MOVING_DOWN && currentFloor > request.floor;

        boolean sameDirection = elevatorStatus == ElevatorStatus.MOVING_UP && request.direction == Direction.UP
                || elevatorStatus == ElevatorStatus.MOVING_DOWN && request.direction == Direction.DOWN;


        if (movingToward && sameDirection){
            return distance + 500;
        }
        if (movingToward & !sameDirection){
            return distance + 1000;
        }
        return distance + 10010;
    }
}
