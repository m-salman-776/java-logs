package Project101.RideShare.Classes;

import lombok.Getter;

@Getter
public class RideOffer {
    int id;
    int driverId;
    Location source;
    Location destination;
    String status; // available / unavailable
    int numberOfSeat;

}
