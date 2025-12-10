package Project101.RideShare.Interfaces;

import Project101.RideShare.Classes.RideOffer;
import Project101.RideShare.Classes.RideRequest;

import java.util.List;

public interface Filter {
    List<RideOffer> filer(RideRequest rideRequest,List<RideOffer> offerList);
}
