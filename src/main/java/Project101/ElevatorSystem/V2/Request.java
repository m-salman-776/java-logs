package Project101.ElevatorSystem.V2;

import java.time.Instant;

public class Request {
    int floor;
    Direction direction;
    long timestamp;
    public Request(int floor, Direction direction){
        this.floor = floor;
        this.direction = direction;
        this.timestamp = Instant.now().toEpochMilli();
    }
}
