package Project101.ElevatorSystem.V2;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class RoundRobin implements Dispatcher{
    AtomicInteger index = new AtomicInteger(0);
    @Override
    public ElevatorController findBestElevator(List<ElevatorController> controllers, Request request) {
        if (controllers.isEmpty()) return null;
        int idx = index.incrementAndGet() % controllers.size();
        return controllers.get(idx);
    }
}
