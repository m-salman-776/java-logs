package Project101.AuctionSystem;

import Project101.AuctionSystem.Enums.AuctionStatus;

import java.time.Instant;
import java.util.List;

public class BidScheduler implements Runnable{
    List<Auction> auctionList;
    public BidScheduler(List<Auction> auctionList){
        this.auctionList=auctionList;
    }
    @Override
    public void run() {
        for (Auction auction:auctionList){
            if (auction.getStartTime().isAfter(Instant.now())){
                auction.status = AuctionStatus.ACTIVE;
            }
            if (auction.getEndTime().isAfter(Instant.now())){
                auction.status = AuctionStatus.COMPLETED;
            }
        }
    }
}
