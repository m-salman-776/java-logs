package Project101.AuctionSystem;

import Project101.AuctionSystem.Enums.AuctionStatus;
import Project101.AuctionSystem.Enums.BidStatus;

import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AuctionService {
    Map<Integer,Auction> auctionMap;
    Map<Integer,AuctionSetting> auctionSettingMap;
    Map<Integer, List<Bid>> auctionBidList;
    int auctionId;
    int bidId;
    AuctionService(){
        auctionMap = new HashMap<>();
        auctionSettingMap = new HashMap<>();
        auctionBidList = new HashMap<>();
        auctionId = 0;
        bidId = 0;
    }
    public Auction getAuction(int auctionId){
        return auctionMap.get(auctionId);
    }
    public int createAuction(int sellerId, int itemId, double startPrice, double reservedPrice,double increment,Instant startTime,Instant endTime){
        this.auctionId += 1;
        Auction auction = new Auction(this.auctionId,sellerId,itemId,startTime,endTime,startPrice);
        AuctionSetting auctionSetting = new AuctionSetting(this.auctionId,reservedPrice,increment,120);

        auctionMap.put(auctionId,auction);
        auctionSettingMap.put(auctionId,auctionSetting);
        return auctionId;
    }
    public void startAuction(int auctionId){
        if (!this.auctionMap.containsKey(auctionId)){
            // todo
        }
        auctionMap.get(auctionId).status = AuctionStatus.ACTIVE;
    }
    public void completeAuction(int auctionId){
        if (!this.auctionMap.containsKey(auctionId)){
            // todo
        }
        auctionMap.get(auctionId).status = AuctionStatus.COMPLETED;
    }
    public BidStatus createBidding(int auctionId,double bidAmount){
        this.bidId += 1;
        Auction auction = auctionMap.get(auctionId);
        Bid bid = new Bid(this.bidId,auctionId,bidAmount);
        synchronized (auction){
            if (validateBid(bid)){
                bid.status = BidStatus.ACCEPTED;
                handleSnipping(auction);
                updateHighesBid(bid);
                notifyNewBid(bid);
            }else {
                bid.status = BidStatus.REJECTED;
            }
        }
        return bid.status;
    }
    private boolean validateBid(Bid bid){
        Auction auction = auctionMap.get(bid.getAuctionId());
        AuctionSetting auctionSetting = auctionSettingMap.get(bid.getAuctionId());
        return auction.getCurrentHighestBid() < bid.getAmount() + auctionSetting.getIncrementStep();
    }
    public void updateHighesBid(Bid bid){
        auctionMap.get(bid.getAuctionId()).currentHighestBid = bid.getAmount();
    }
    public void notifyNewBid(Bid bid){

    }
    public void handleSnipping(Auction auction){
        AuctionSetting auctionSetting = auctionSettingMap.get(auction.getId());
        Instant now = Instant.now();
        Instant endTime = auction.getEndTime();
        long secondLeft = Duration.between(endTime,now).getSeconds();
        if (secondLeft <= auctionSetting.getSnipping_extension_sec()){
            Instant newEndTime = endTime.plusSeconds(auctionSetting.getSnipping_extension_sec());
            auction.endTime = endTime;
        }
    }
}
