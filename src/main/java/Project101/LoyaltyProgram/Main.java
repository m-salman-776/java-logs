package Project101.LoyaltyProgram;

public class Main {
    public static void main(String[] args) {
        TierRuleConfig config = new TierRuleConfig(Tier.GENIUS_L2);
        config.updateConfig(BookingType.HOTEL,2);

        LoyaltyService loyaltyService = new LoyaltyService();

        loyaltyService.addTierConfig(config);

        loyaltyService.processBookingEvent(new BookingEvent(1,BookingType.HOTEL,123));
        loyaltyService.processBookingEvent(new BookingEvent(1,BookingType.HOTEL,1232));

        Tier tier = loyaltyService.getUserTier(1);
        System.out.println("DONE");
    }
}
