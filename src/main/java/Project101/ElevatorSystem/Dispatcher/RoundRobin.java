package Project101.ElevatorSystem.Dispatcher;

import Project101.ElevatorSystem.ElevatorController;
import Project101.ElevatorSystem.Request;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class RoundRobin implements Dispatcher {
    AtomicInteger index = new AtomicInteger(0);
    @Override
    public ElevatorController findBestElevator(List<ElevatorController> controllers, Request request) {
        if (controllers.isEmpty()) return null;
        // todo make direction of lift movement into consideration
        int idx = index.incrementAndGet() % controllers.size();
        return controllers.get(idx);
    }
}
