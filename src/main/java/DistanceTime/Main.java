package DistanceTime;

import DistanceTime.Classes.*;
import DistanceTime.Implementations.HaversineDistance;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        GPSLocation initialLocation = new GPSLocation(0.0, 0.0);

        DeliveryPartner aman = new DeliveryPartner("aman",initialLocation);

        GPSLocation restaurant1Location = new GPSLocation(12.9279, 77.6271);
        Restaurant restaurant1 = new Restaurant("R1", restaurant1Location, 15);

        GPSLocation consumer1Location = new GPSLocation(12.9716, 77.5946);
        Consumer consumer1 = new Consumer("C1", consumer1Location);

        GPSLocation restaurant2Location = new GPSLocation(12.9352, 77.6245);
        Restaurant restaurant2 = new Restaurant("R2", restaurant2Location, 20000);

        GPSLocation consumer2Location = new GPSLocation(12.9200, 77.6250);
        Consumer consumer2 = new Consumer("C2", consumer2Location);

        List<Order> orders = new ArrayList<>();
        orders.add(new Order(restaurant1, consumer1));
        orders.add(new Order(restaurant2, consumer2));

        DeliveryRoute deliveryService = new DeliveryRoute(new HaversineDistance());
        List<DeliveryStep> optimizedDeliveryPath = deliveryService.calculateOptimizedDeliveryPath(aman.getLocation(), 3.14,orders);

        System.out.println("Optimized Delivery Path:");
        for (DeliveryStep step : optimizedDeliveryPath) {
            System.out.println(step.getText() + " at " + step.getLocation().getLatitude() + ", " + step.getLocation().getLongitude());
        }
    }
}
