package Project101.RideShare.Implementations;

import Project101.RideShare.Classes.RideOffer;
import Project101.RideShare.Classes.RideRequest;
import Project101.RideShare.Interfaces.Filter;

import java.util.ArrayList;
import java.util.List;

public class OccupencyFilter implements Filter {
    @Override
    public List<RideOffer> filer(RideRequest rideRequest,List<RideOffer> offerList) {
        List<RideOffer> rideOfferList = new ArrayList<>();
        for (RideOffer  rideOffer : offerList){
            if (rideOffer.getNumberOfSeat() < rideRequest.getRequiredSeat()) continue;
            rideOfferList.add(rideOffer);
        }
        return rideOfferList;
    }
}
