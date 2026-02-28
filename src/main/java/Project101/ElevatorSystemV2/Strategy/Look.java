package Project101.ElevatorSystemV2.Strategy;

import Project101.ElevatorSystemV2.CarStatus;
import Project101.ElevatorSystemV2.Direction;
import Project101.ElevatorSystemV2.ElevatorCar;

import java.util.*;

public class Look implements BestElevatorStrategy{
    @Override
    public ElevatorCar getBestElevator(List<ElevatorCar> elevatorCarList, int floor, Direction direction) {
        int cost = 0;
        Map<Integer, TreeMap<Integer,String>> map = new HashMap<>();
        map.putIfAbsent(1,new TreeMap<>());
        map.get(1).put(1,"dfas");
        Map.Entry<Integer,String> entry = map.get(1).floorEntry(1);

        return null;
    }
    private int calculateCost(ElevatorCar elevatorCar,int requestFloor,Direction direction){
        int cost = Math.abs(elevatorCar.currentFloor - requestFloor);
        if (elevatorCar.status == CarStatus.IDLE) return cost;
        boolean movingToward = requestFloor > elevatorCar.currentFloor;
        return 0;
    }
}
