package DistanceTime.Classes;
import lombok.Getter;

@Getter
public class DeliveryStep {
    String text;
    GPSLocation location;
    public DeliveryStep(String description, GPSLocation location) {
        this.text = description;
        this.location = location;
    }
}
