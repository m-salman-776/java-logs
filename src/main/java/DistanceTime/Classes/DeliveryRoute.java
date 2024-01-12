package DistanceTime.Classes;
import DistanceTime.Interface.DistanceCalculator;

import java.util.ArrayList;
import java.util.List;
public class DeliveryRoute {
    DistanceCalculator distanceCalculator;
    public DeliveryRoute(DistanceCalculator distanceCalculator) {
        this.distanceCalculator = distanceCalculator;
    }
    public List<DeliveryStep> calculateOptimizedDeliveryPath(GPSLocation initialLocation, Double avgSpeed, List<Order> orders) {
        if (initialLocation == null || orders == null) {
            throw new IllegalArgumentException("Initial location and orders cannot be null.");
        }
        List<DeliveryStep> path = new ArrayList<>();
        List<Order> unvisitedOrders = new ArrayList<>(orders);
        GPSLocation currentLocation = initialLocation;
        path.add(new DeliveryStep("Starting point", currentLocation));

        while (!unvisitedOrders.isEmpty()) {
            Order bestOrder = findBestOrder(currentLocation, unvisitedOrders,avgSpeed);
            GPSLocation restaurantLocation = bestOrder.restaurant.location;
            path.add(new DeliveryStep("Pick up from " + bestOrder.restaurant.name, restaurantLocation));
            currentLocation = bestOrder.consumer.location;
            path.add(new DeliveryStep("Deliver to " + bestOrder.consumer.name, currentLocation));
            unvisitedOrders.remove(bestOrder);
        }
        return path;
    }

    private Order findBestOrder(GPSLocation currentLocation, List<Order> unvisitedOrders, Double speed) {
        double minTotalTime = Double.MAX_VALUE;
        Order bestOrder = null;
        for (Order order : unvisitedOrders) {
            GPSLocation restaurantLocation = order.restaurant.getLocation();
            double distanceToRestaurant = this.distanceCalculator.calculate(currentLocation, restaurantLocation);
            double distanceToConsumer = this.distanceCalculator.calculate(restaurantLocation, order.consumer.location);
            double totalDeliveryTime = distanceToRestaurant / speed + order.restaurant.preparationTime + distanceToConsumer / speed;
            if (totalDeliveryTime < minTotalTime) {
                minTotalTime = totalDeliveryTime;
                bestOrder = order;
            }
        }

        return bestOrder;
    }
}

