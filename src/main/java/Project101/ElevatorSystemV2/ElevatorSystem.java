package Project101.ElevatorSystemV2;
import Project101.ElevatorSystemV2.Strategy.FCFSStrategy;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ElevatorSystem {
    Building building ;
    BlockingQueue<CarRequest> requestQueue = new LinkedBlockingQueue<>();
    public ElevatorSystem(Building building){
        this.building = building;
        new Thread(this::assignRequestToCar).start();
        new Thread(this::requestProcessor).start();
    }
    void assignRequestToCar()  {
        while (true){
            try {
                // take() blocks until an element is available, avoiding busy waiting
                CarRequest carRequest = this.requestQueue.take();
                ElevatorCar elevatorCar = this.getBestCar(carRequest.floor,carRequest.direction);
                elevatorCar.addRequest(carRequest.floor);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }

    }
    public void addElevatorRequest(int floor, Direction direction){
        this.requestQueue.add(new CarRequest(floor,direction));
    }

    ElevatorCar getBestCar(int floor, Direction direction){
        List<ElevatorCar> carList = this.building.getOperationalElevator();
        return new FCFSStrategy().getBestElevator(carList,floor,direction);
    }
    void requestProcessor(){
        List<ElevatorCar> carList = this.building.getOperationalElevator();
        for (ElevatorCar elevatorCar : carList){
            new Thread(elevatorCar::processRequest).start();
        }
    }
}
