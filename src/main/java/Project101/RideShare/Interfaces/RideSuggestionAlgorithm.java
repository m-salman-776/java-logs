package Project101.RideShare.Interfaces;

import Project101.RideShare.Classes.RideOffer;
import Project101.RideShare.Classes.RideRequest;
import Project101.RideShare.Classes.RideSuggestion;

import java.util.List;

public interface RideSuggestionAlgorithm {
    List<RideSuggestion> getSuggestion(RideRequest request, List<RideOffer> rideOfferList);
}
