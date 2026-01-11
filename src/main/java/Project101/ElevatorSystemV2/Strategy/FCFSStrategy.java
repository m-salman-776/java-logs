package Project101.ElevatorSystemV2.Strategy;

import Project101.ElevatorSystemV2.CarStatus;
import Project101.ElevatorSystemV2.Direction;
import Project101.ElevatorSystemV2.ElevatorCar;

import java.util.List;
import java.util.Random;

public class FCFSStrategy implements BestElevatorStrategy {
    public FCFSStrategy(){

    }
    @Override
    public ElevatorCar getBestElevator(List<ElevatorCar> elevatorCarList, int floor, Direction direction) {
        for (ElevatorCar elevatorCar : elevatorCarList){
            if (elevatorCar.status == CarStatus.IDLE) return elevatorCar;
        }
        if (elevatorCarList.isEmpty()) return null;
        int random = new Random().nextInt(elevatorCarList.size());
        return elevatorCarList.get(random);
    }
}
