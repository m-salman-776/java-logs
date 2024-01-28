package LLD.RideShare.Classes;

import lombok.Getter;
import org.joda.time.DateTime;
@Getter
public class RideRequest {
    int requestId;
    int userId;
    DateTime preferredTime;
    Location pickUpLocation;
    Location dropLocation;
    String status;
    String preference; // requested , matched , cancelled
    int requiredSeat;
    float maxDistance;
    int maxTimeInSecond;
}
