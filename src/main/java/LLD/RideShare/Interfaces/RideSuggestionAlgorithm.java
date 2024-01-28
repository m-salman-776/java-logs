package LLD.RideShare.Interfaces;

import LLD.RideShare.Classes.RideOffer;
import LLD.RideShare.Classes.RideRequest;
import LLD.RideShare.Classes.RideSuggestion;

import java.util.List;

public interface RideSuggestionAlgorithm {
    List<RideSuggestion> getSuggestion(RideRequest request, List<RideOffer> rideOfferList);
}
