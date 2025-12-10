package Project101.ElevatorSystem.V2;



import java.util.List;

public interface Dispatcher {
    ElevatorController findBestElevator(List<ElevatorController> controllers, Request request);
}
