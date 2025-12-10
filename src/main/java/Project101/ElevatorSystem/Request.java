package Project101.ElevatorSystem;


import Project101.ElevatorSystem.DataType.Location;
import Project101.ElevatorSystem.DataType.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Request {
    Status direction;
    Location location;
    int floor;
}
