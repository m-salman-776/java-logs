package Project101.ElevatorSystem;

import Project101.ElevatorSystem.DataType.Direction;
import Project101.ElevatorSystem.DataType.ElevatorStatus;
import Project101.ElevatorSystem.Dispatcher.SmartDispatcher;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ElevatorSystemTest {

    @Test
    public void testElevatorMovesToRequestedFloor() throws InterruptedException {
        // 1. Setup System
        SmartDispatcher dispatcher = new SmartDispatcher();
        ElevatorSystem system = new ElevatorSystem(dispatcher);

        // 2. Setup Elevator and Controller
        Elevator elevator = new Elevator(1);
        elevator.currentFloor = 0;
        elevator.elevatorStatus = ElevatorStatus.IDLE; // Initialize status to avoid NPE in Controller

        ElevatorController controller = new ElevatorController(elevator);
        List<ElevatorController> controllers = new ArrayList<>();
        controllers.add(controller);

        // Manually inject dependencies since ElevatorSystem constructor doesn't do it
        system.elevatorControllerList = controllers;
        system.elevatorMap.put(1, elevator);

        // 3. Start the controller thread
        Thread controllerThread = new Thread(controller);
        controllerThread.start();

        // 4. Act: Request floor 5 (Hall Request)
        system.handleHallRequest(5, Direction.UP);

        // 5. Wait for async processing
        // Flow: RequestQueue -> Dispatcher Thread -> Elevator Heap -> Controller Thread -> Move
        Thread.sleep(1000);

        // 6. Assert
        assertEquals(5, elevator.currentFloor, "Elevator should have moved to floor 5");

        // Cleanup
        controllerThread.interrupt();
    }

    @Test
    public void testSmartDispatcherSelection() throws InterruptedException {
        // Setup System with SmartDispatcher
        SmartDispatcher dispatcher = new SmartDispatcher();
        ElevatorSystem system = new ElevatorSystem(dispatcher);
        List<ElevatorController> controllers = new ArrayList<>();

        // Elevator 1 at Floor 0 (Closer to target)
        Elevator e1 = new Elevator(1);
        e1.currentFloor = 0;
        e1.elevatorStatus = ElevatorStatus.IDLE;
        ElevatorController c1 = new ElevatorController(e1);
        controllers.add(c1);

        // Elevator 2 at Floor 10 (Farther from target)
        Elevator e2 = new Elevator(2);
        e2.currentFloor = 10;
        e2.elevatorStatus = ElevatorStatus.IDLE;
        ElevatorController c2 = new ElevatorController(e2);
        controllers.add(c2);

        system.elevatorControllerList = controllers;
        system.elevatorMap.put(1, e1);
        system.elevatorMap.put(2, e2);

        Thread t1 = new Thread(c1);
        Thread t2 = new Thread(c2);
        t1.start();
        t2.start();

        // Act: Request Floor 2. SmartDispatcher should pick Elevator 1 (Cost: 2) over Elevator 2 (Cost: 8).
        system.handleHallRequest(2, Direction.UP);

        Thread.sleep(1000);

        // Assert
        assertEquals(2, e1.currentFloor, "Elevator 1 should take the request as it is closer");
        assertEquals(10, e2.currentFloor, "Elevator 2 should remain at floor 10");

        t1.interrupt();
        t2.interrupt();
    }
}