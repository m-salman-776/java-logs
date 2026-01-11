package Project101.ElevatorSystemV2.Strategy;

import Project101.ElevatorSystemV2.Direction;
import Project101.ElevatorSystemV2.ElevatorCar;

import java.util.List;

public interface BestElevatorStrategy {
    ElevatorCar getBestElevator(List<ElevatorCar> elevatorCarList, int floor, Direction direction);
}
