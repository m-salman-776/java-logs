package Project101.ElevatorSystem.Dispatcher;



import Project101.ElevatorSystem.ElevatorController;
import Project101.ElevatorSystem.Request;

import java.util.List;

public interface Dispatcher {
    ElevatorController findBestElevator(List<ElevatorController> controllers, Request request);
}
