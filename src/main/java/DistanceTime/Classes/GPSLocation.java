package DistanceTime.Classes;

import lombok.Getter;

@Getter
public class GPSLocation {
    double latitude;
    double longitude;

    public GPSLocation(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
