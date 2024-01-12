package DistanceTime.Interface;

import DistanceTime.Classes.GPSLocation;

public interface DistanceCalculator {
    Double calculate(GPSLocation location1 , GPSLocation location2);
}
