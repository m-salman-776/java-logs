package Project101.ElevatorSystemV2;

import java.util.ArrayList;
import java.util.List;

public class Building {
    int floors;
    public List<ElevatorCar> elevatorCarList;
    public Building(int floorCount, int elevatorCount){
        this.floors = floorCount;
        this.elevatorCarList = new ArrayList<>();
        for (int i=1;i <= elevatorCount; i++){
            this.elevatorCarList.add(new ElevatorCar(i,floorCount));
        }
    }
    List<ElevatorCar> getOperationalElevator(){
        List<ElevatorCar> elevatorCarList = new ArrayList<>();
        for (ElevatorCar elevatorCar : this.elevatorCarList){
            if (elevatorCar.status != CarStatus.MAINTENANCE){
                elevatorCarList.add(elevatorCar);
            }
        }
        return elevatorCarList;
    }
}
