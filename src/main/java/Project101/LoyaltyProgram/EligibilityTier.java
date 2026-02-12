package Project101.LoyaltyProgram;

public interface EligibilityTier {
    Tier getTier();
    boolean isEligible(BookingStats bookingStats);
}
