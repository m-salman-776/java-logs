package Project101.AuctionSystem;

import Project101.AuctionSystem.Enums.BidStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Bid {
    int id;
    int auctionId;
    double amount;
    BidStatus status;
    public Bid(int id,int auctionId,double amount){
        this.auctionId = auctionId;
        this.id = id;
        this.amount = amount;
        this.status = BidStatus.RECEIVED;
    }
}
