package DistanceTime.Implementations;
import DistanceTime.Classes.GPSLocation;
import DistanceTime.Interface.DistanceCalculator;


public class HaversineDistance implements DistanceCalculator {
    Double earthRadius = 6371000 * 1.0;
    @Override
    public Double calculate(GPSLocation location1, GPSLocation location2) {
        Double lat1 = location1.getLatitude(),  long1 = location1.getLongitude();
        Double lat2 = location2.getLatitude() , long2 = location2.getLongitude();
        lat1 = toRadians(lat1);
        long1 = toRadians(long1);
        lat2 = toRadians(lat2);
        long2 = toRadians(long2);
        Double dlong = long2 - long1;
        Double dlat = lat2 - lat1;
        Double ans = Math.pow(Math.sin(dlat / 2), 2) + Math.cos(lat1) * Math.cos(lat2) * Math.pow(Math.sin(dlong / 2), 2);
        ans = 2 * Math.asin(Math.sqrt(ans));
        ans = ans * this.earthRadius;
        return ans;
    }
    private Double toRadians(Double degree) {
        Double one_deg = (Math.PI) / 180;
        return (one_deg * degree);
    }
}