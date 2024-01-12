package DistanceTime.Classes;

import lombok.Getter;

@Getter
public class DeliveryPartner {
    String name;
    GPSLocation location;
    public DeliveryPartner(String name, GPSLocation location){
        this.name = name;
        this.location = location;
    }
}
