package Project101.AuctionSystem;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AuctionSetting {
    int auctionId;
    double reservedPrice;
    double incrementStep;
    int snipping_extension_sec;
    public AuctionSetting(int auctionId,double reservedPrice,double incrementStep,int snipping_extension_sec){
        this.auctionId = auctionId;
        this.reservedPrice = reservedPrice;
        this.incrementStep = incrementStep;
        this.snipping_extension_sec = snipping_extension_sec;
    }
}
