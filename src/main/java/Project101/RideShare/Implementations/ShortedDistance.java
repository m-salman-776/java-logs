package Project101.RideShare.Implementations;

import Project101.RideShare.Classes.Location;
import Project101.RideShare.Classes.RideOffer;
import Project101.RideShare.Classes.RideRequest;
import Project101.RideShare.Classes.RideSuggestion;
import Project101.RideShare.Interfaces.RideSuggestionAlgorithm;

import java.util.ArrayList;
import java.util.List;

public class ShortedDistance implements RideSuggestionAlgorithm {
    @Override
    public List<RideSuggestion> getSuggestion(RideRequest request, List<RideOffer> rideOfferList) {
        List<RideSuggestion> suggestionList = new ArrayList<>();
        for (RideOffer rideOffer:rideOfferList){
            float dryDistance =calculateDistance(request.getPickUpLocation(),rideOffer.getSource());
            float runDistance = calculateDistance(request.getPickUpLocation(),request.getDropLocation());
            suggestionList.add(new RideSuggestion(request.getRequestId(),rideOffer.getId(),dryDistance+runDistance,100));
        }
        return suggestionList;
    }

    float calculateDistance(Location location1,Location location2){
        return location1.distance(location2);
    }
}
