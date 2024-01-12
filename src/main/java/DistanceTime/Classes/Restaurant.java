package DistanceTime.Classes;
import lombok.Getter;

@Getter
public class Restaurant {
    String name;
    GPSLocation location;
    int preparationTime;  // in minutes

    public Restaurant(String name, GPSLocation location, int preparationTime) {
        this.name = name;
        this.location = location;
        this.preparationTime = preparationTime;
    }
}
