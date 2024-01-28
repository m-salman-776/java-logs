package LLD.RideShare.Interfaces;

import LLD.RideShare.Classes.Ride;
import LLD.RideShare.Classes.RideOffer;
import LLD.RideShare.Classes.RideRequest;
import LLD.RideShare.Classes.RideSuggestion;

import java.util.List;

public interface Filter {
    List<RideOffer> filer(RideRequest rideRequest,List<RideOffer> offerList);
}
