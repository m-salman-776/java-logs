package LLD.ElevatorSystem;


import LLD.ElevatorSystem.DataType.Location;
import LLD.ElevatorSystem.DataType.Status;
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
