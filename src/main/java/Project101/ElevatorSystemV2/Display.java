package Project101.ElevatorSystemV2;

public class Display {
    int floor = 0;
    int weight = 0;
    Direction direction = Direction.IDLE;
    public Display(){
    }
    public void updateFloor(int floor){
        this.floor = floor;
    }
    public void updateDirection(Direction direction){
        this.direction = direction;
    }
}
