package LLD.RideShare.Classes;

public class RideSuggestion {
    int rideRequestId;
    int rideOfferId;

    int estimatedDuration;
    float estimatedDistance;
    String remark;
    public RideSuggestion(int rideRequestId,int rideOfferId,float estimatedDistance,float speed){
        this.rideRequestId = rideRequestId;
        this.rideOfferId = rideOfferId;
        this.estimatedDistance = estimatedDistance;
        this.estimatedDuration = (int)(estimatedDuration / speed);

    }
}
