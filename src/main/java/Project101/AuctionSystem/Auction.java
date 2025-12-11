package Project101.AuctionSystem;

import Project101.AuctionSystem.Enums.AuctionStatus;
import Project101.AuctionSystem.Enums.BidStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
@Getter
@Setter
public class Auction {
    int id;
    int sellerId;
    Instant startTime;
    Instant endTime;
    double startPrice;
    double currentHighestBid;
    int itemId;
    int version;
    AuctionStatus status;
    public Auction(int id, int sellerId,int itemId,Instant startTime,Instant endTime, double startPrice){
        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
        this.startPrice = startPrice;
        this.itemId = itemId;
        this.status = AuctionStatus.CREATED;
        this.sellerId = sellerId;
    }
}
