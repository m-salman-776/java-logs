package Project101.LoyaltyProgram;
public class TierEvaluator {
    boolean evaluate(TierRuleConfig config, BookingStats stats){
        int trueCount = 0;
        for (BookingType type : BookingType.values()){
            if (config.getConfig(type) <= stats.getConfig(type)){
                trueCount += 1;
            }
        }
        return trueCount >= BookingType.values().length;
    }
}
