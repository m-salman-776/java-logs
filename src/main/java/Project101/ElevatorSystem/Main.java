package Project101.ElevatorSystem;

import Project101.ElevatorSystem.DataType.Direction;
import Project101.ElevatorSystem.Dispatcher.Dispatcher;
import Project101.ElevatorSystem.Dispatcher.SmartDispatcher;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[]args){
        Dispatcher dispatcher = new SmartDispatcher();
        ElevatorSystem elevatorSystem = new ElevatorSystem(dispatcher);

        List<ElevatorController> elevatorControllers = new ArrayList<>();
        for (int i = 1; i <= 3; i++) { // Create 3 elevators
            Elevator elevator = new Elevator(i);
            elevatorSystem.elevatorMap.put(i, elevator);
            ElevatorController controller = new ElevatorController(elevator);
            Thread elevatorThread = new Thread(controller);
            elevatorThread.setName("Elevator-Controller-"+i);
            elevatorControllers.add(controller);
            elevatorThread.start();
        }
        elevatorSystem.elevatorControllerList = elevatorControllers;
        elevatorSystem.handleHallRequest(10, Direction.UP);
        elevatorSystem.handleHallRequest(1, Direction.DOWN);
        
        System.out.println("done");
    }
}
