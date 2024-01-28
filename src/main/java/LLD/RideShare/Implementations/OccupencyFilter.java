package LLD.RideShare.Implementations;

import LLD.RideShare.Classes.RideOffer;
import LLD.RideShare.Classes.RideRequest;
import LLD.RideShare.Interfaces.Filter;

import java.util.ArrayList;
import java.util.Arrays;
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
