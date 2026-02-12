package Project101.LoyaltyProgram;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

public class LoyaltyService {
    Map<Integer,Tier> userTier;
//    Map<Tier, EligibilityTier> eligibilityRuleMap;
    Map<Tier,Benefits> tierBenefitsMap;
    // userId -> BookingStats
    Map<Integer, BookingStats> userBookingStats;

    // Priority Order -> Eligibility Rule Evaluation
    TreeMap<Integer, EligibilityTier> eligibilityTierTreeMap;

    List<TierRuleConfig> ruleConfigList = new ArrayList<>();

    TierEvaluator evaluator = new TierEvaluator();

    public LoyaltyService(){
        userTier = new ConcurrentHashMap<>();
        tierBenefitsMap = new ConcurrentHashMap<>();
        userBookingStats = new ConcurrentHashMap<>();
        eligibilityTierTreeMap = new TreeMap<>();
    }
    public void processBookingEvent(BookingEvent bookingEvent){
        int userId = bookingEvent.userId;
        this.userBookingStats.putIfAbsent(userId,new BookingStats());
        this.userBookingStats.get(userId).updateBookingCount(bookingEvent);

    }
    public Tier getUserTier(int userid){
        BookingStats bookingStats = userBookingStats.get(userid);
        if (bookingStats == null){
            return Tier.DEFAULT;
        }
        for (TierRuleConfig tierRuleConfig: ruleConfigList){
            if (evaluator.evaluate(tierRuleConfig,bookingStats)){
                return tierRuleConfig.tier;
            }
        }
        return userTier.getOrDefault(userid,Tier.DEFAULT);
    }

    public Benefits getBenefits(Tier tier){
        return tierBenefitsMap.getOrDefault(tier,new Benefits());
    }

    public void updateTierBenefits(Tier tier,Benefits benefits){
        this.tierBenefitsMap.get(tier).updateBenefits(benefits);
    }

    public void addTierConfig(TierRuleConfig config){
        this.ruleConfigList.add(config);
    }
}
