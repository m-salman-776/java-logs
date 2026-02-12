package Project101.LoyaltyProgram;

import Project101.BookingSystem.MoviesBooking.Model.Booking;

import java.util.HashMap;
import java.util.Map;

public class TierRuleConfig {
    public Tier tier;
    Map<BookingType,Integer> config ;
    public TierRuleConfig(Tier tier){
        this.tier = tier;
        config = new HashMap<>();
        for (BookingType type : BookingType.values()){
            config.put(type,0);
        }
    }
    public void updateConfig(BookingType key,int value){
        config.put(key,value);
    }
    public int getConfig(BookingType type){
        return this.config.get(type);
    }
}