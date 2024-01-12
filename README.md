# DistanceTime Package

## Classes

### 1. Consumer

- `name`: String
- `location`: GPSLocation

### 2. DeliveryPartner

- `name`: String
- `location`: GPSLocation

### 3. DeliveryRoute

- `distanceCalculator`: DistanceCalculator

### 4. DeliveryStep

- `text`: String
- `location`: GPSLocation

### 5. GPSLocation

- `latitude`: double
- `longitude`: double

### 6. Order

- `restaurant`: Restaurant
- `consumer`: Consumer

### 7. Restaurant

- `name`: String
- `location`: GPSLocation
- `preparationTime`: int

### 8. HaversineDistance

- `earthRadius`: Double

## Interfaces

### 1. DistanceCalculator

- `calculate(location1: GPSLocation, location2: GPSLocation): Double`


## Assumption
- There will be an Order Service which took necessary parameters and create order.
- A Delivery Partner Service which choose best delivery Aman in this case
- This Service (DeliveryRoute planner may be) take input of Aman's current location , average speed , and order list
- Generate and optimise path and return the list the path list
- Orchestrator may take this path and convey it to relevant delivery person
