package LLD.RideShare.Implementations;

import LLD.RideShare.Classes.RideOffer;
import LLD.RideShare.Classes.RideRequest;
import LLD.RideShare.Classes.RideSuggestion;
import LLD.RideShare.Interfaces.RideSuggestionAlgorithm;

import java.util.List;

public class ShortestTime implements RideSuggestionAlgorithm {
    @Override
    public List<RideSuggestion> getSuggestion(RideRequest request, List<RideOffer> rideOfferList) {
        return null;
    }
}
