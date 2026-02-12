package Project101.LoyaltyProgram;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class LoyaltyProgramVerificationTest {
    private LoyaltyService loyaltyService;

    @Before
    public void setUp() {
        loyaltyService = new LoyaltyService();
    }

    /**
     * PRD Requirement: GENIUS_L1 >= 2 completed stays (HOTEL)
     * Failure: The current logic grants the tier when the user has FEWER bookings than required.
     */
    @Test
    public void testTierQualification_LogicInverted_FailsWhenNotQualified() {
        // Configure L1 to require 2 HOTEL bookings
        TierRuleConfig l1Config = new TierRuleConfig(Tier.GENIUS_L1);
        l1Config.updateConfig(BookingType.HOTEL, 2);
        loyaltyService.addTierConfig(l1Config);

        int userId = 101;
        // User has 0 bookings.
        // Expected: DEFAULT (Not qualified yet)
        // Actual: GENIUS_L1 (Because 2 > 0 is True in TierEvaluator)
        Tier currentTier = loyaltyService.getUserTier(userId);
        
        // This assertion is expected to FAIL with the current code
        assertEquals("User with 0 bookings should NOT qualify for GENIUS_L1", Tier.DEFAULT, currentTier);
    }

    /**
     * PRD Requirement: GENIUS_L1 >= 2 completed stays (HOTEL)
     * Failure: The current logic denies the tier when the user HAS the required bookings.
     */
    @Test
    public void testTierQualification_LogicInverted_FailsWhenQualified() {
        // Configure L1 to require 2 HOTEL bookings
        TierRuleConfig l1Config = new TierRuleConfig(Tier.GENIUS_L1);
        l1Config.updateConfig(BookingType.HOTEL, 2);
        loyaltyService.addTierConfig(l1Config);

        int userId = 102;
        // User makes 2 HOTEL bookings
        loyaltyService.processBookingEvent(new BookingEvent(userId, BookingType.HOTEL, 100.0));
        loyaltyService.processBookingEvent(new BookingEvent(userId, BookingType.HOTEL, 100.0));

        // Expected: GENIUS_L1
        // Actual: DEFAULT (Because 2 > 2 is False in TierEvaluator)
        Tier currentTier = loyaltyService.getUserTier(userId);

        // This assertion is expected to FAIL with the current code
        assertEquals("User with 2 bookings SHOULD qualify for GENIUS_L1", Tier.GENIUS_L1, currentTier);
    }

    /**
     * PRD Requirement: GENIUS_L3 >= 15 bookings + >= €1000 spend
     * Failure: The current implementation has no way to configure or check spend requirements.
     */
    @Test
    public void testSpendRequirement_MissingFeature() {
        // Configure L3 to require 15 HOTEL bookings (Best effort since we can't configure spend)
        TierRuleConfig l3Config = new TierRuleConfig(Tier.GENIUS_L3);
        l3Config.updateConfig(BookingType.HOTEL, 15);
        loyaltyService.addTierConfig(l3Config);

        int userId = 103;
        // User makes 15 bookings but with 0 spend (e.g. using points or free stays)
        for (int i = 0; i < 15; i++) {
            loyaltyService.processBookingEvent(new BookingEvent(userId, BookingType.HOTEL, 0.0));
        }

        // Expected: DEFAULT (Because spend < 1000)
        // Actual: GENIUS_L3 (If logic was fixed to >=, it would pass bookings and ignore spend)
        // Or with current inverted logic: 15 > 15 is False -> DEFAULT.
        // But if user had 14 bookings: 15 > 14 -> True -> GENIUS_L3.
        
        // Let's test with 14 bookings to show it ignores spend AND has inverted logic
        int userId2 = 104;
        for (int i = 0; i < 14; i++) {
            loyaltyService.processBookingEvent(new BookingEvent(userId2, BookingType.HOTEL, 0.0));
        }
        
        Tier currentTier = loyaltyService.getUserTier(userId2);
        
        // Even if logic was inverted, it should NOT grant L3 because spend is 0.
        // But it grants L3 because spend is ignored.
        assertEquals("User with 0 spend should NOT qualify for GENIUS_L3", Tier.DEFAULT, currentTier);
    }
}
