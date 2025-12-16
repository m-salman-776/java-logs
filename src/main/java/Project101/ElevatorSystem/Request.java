package Project101.ElevatorSystem;

import Project101.ElevatorSystem.DataType.Direction;

import java.time.Instant;

public class Request {
    public int floor;
    public Direction direction;
    long timestamp;
    public Request(int floor, Direction direction){
        this.floor = floor;
        this.direction = direction;
        this.timestamp = Instant.now().toEpochMilli();
    }
}
