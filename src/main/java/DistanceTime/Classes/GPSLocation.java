package DistanceTime.Classes;

import lombok.Getter;

@Getter
public class GPSLocation {
    Double latitude;
    Double longitude;

    public GPSLocation(Double latitude, Double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }
    private void validateLatitude(Double latitude) {
        if (latitude < -90.0 || latitude > 90.0) {
            throw new IllegalArgumentException("Invalid latitude value: " + latitude);
        }
    }

    private void validateLongitude(Double longitude) {
        if (longitude < -180.0 || longitude > 180.0) {
            throw new IllegalArgumentException("Invalid longitude value: " + longitude);
        }
    }
}
