package Project101.RideShare.Implementations;

import Project101.RideShare.Classes.RideOffer;
import Project101.RideShare.Classes.RideRequest;
import Project101.RideShare.Classes.RideSuggestion;
import Project101.RideShare.Interfaces.RideSuggestionAlgorithm;

import java.util.List;

public class ShortestTime implements RideSuggestionAlgorithm {
    @Override
    public List<RideSuggestion> getSuggestion(RideRequest request, List<RideOffer> rideOfferList) {
        return null;
    }
}
