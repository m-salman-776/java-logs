package Project101.LoyaltyProgram;

import org.junit.Before;
import org.junit.Test;

import java.util.Collection;

import static org.junit.Assert.*;

public class LoyaltyServiceTest {
    private LoyaltyService loyaltyService;

    @Before
    public void setUp() {
        loyaltyService = new LoyaltyService();
    }

    @Test
    public void testFirstTimeUserDefaultTier() {
        Tier tier = loyaltyService.getUserTier(1);
        assertEquals(Tier.DEFAULT, tier);
        
        Benefits benefits = loyaltyService.getBenefits(tier);
        assertNotNull(benefits);
        assertEquals(0.0, benefits.getDiscount(), 0.01);
    }

    @Test
    public void testTierUpgradeToL1() {
        // Setup L1 Rule: 2 HOTEL bookings
        TierRuleConfig l1Config = new TierRuleConfig(Tier.GENIUS_L1);
        l1Config.updateConfig(BookingType.HOTEL, 2);
        loyaltyService.addTierConfig(l1Config);

        // User makes 1 booking
        loyaltyService.processBookingEvent(new BookingEvent(1, BookingType.HOTEL, 100.0));
        // Should still be DEFAULT
        assertEquals("User with 1 booking should be DEFAULT", Tier.DEFAULT, loyaltyService.getUserTier(1));

        // User makes 2nd booking
        loyaltyService.processBookingEvent(new BookingEvent(1, BookingType.HOTEL, 100.0));
        // Should be GENIUS_L1
        assertEquals("User with 2 bookings should be GENIUS_L1", Tier.GENIUS_L1, loyaltyService.getUserTier(1));
    }

    @Test
    public void testTierUpgradeToL2() {
        // Setup L2 Rule: 5 HOTEL bookings
        TierRuleConfig l2Config = new TierRuleConfig(Tier.GENIUS_L2);
        l2Config.updateConfig(BookingType.HOTEL, 5);
        loyaltyService.addTierConfig(l2Config);

        // Setup L1 Rule: 2 HOTEL bookings
        TierRuleConfig l1Config = new TierRuleConfig(Tier.GENIUS_L1);
        l1Config.updateConfig(BookingType.HOTEL, 2);
        loyaltyService.addTierConfig(l1Config);

        // User makes 5 bookings
        for (int i = 0; i < 5; i++) {
            loyaltyService.processBookingEvent(new BookingEvent(1, BookingType.HOTEL, 100.0));
        }

        // Should be L2
        assertEquals("User with 5 bookings should be GENIUS_L2", Tier.GENIUS_L2, loyaltyService.getUserTier(1));
    }

    @Test
    public void testConcurrentBookingUpdates() throws InterruptedException {
        int threadCount = 10;
        int bookingsPerThread = 10;
        int userId = 1;
        
        Thread[] threads = new Thread[threadCount];
        for (int i = 0; i < threadCount; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < bookingsPerThread; j++) {
                    loyaltyService.processBookingEvent(new BookingEvent(userId, BookingType.HOTEL, 10.0));
                }
            });
            threads[i].start();
        }
        
        for (Thread t : threads) {
            t.join();
        }
        
        // Total bookings should be 100
        // We verify this by setting a rule that requires 100 bookings
        TierRuleConfig highTier = new TierRuleConfig(Tier.GENIUS_L3);
        highTier.updateConfig(BookingType.HOTEL, 100);
        loyaltyService.addTierConfig(highTier);
        
        assertEquals("Should reach 100 bookings and qualify for GENIUS_L3", Tier.GENIUS_L3, loyaltyService.getUserTier(userId));
    }
}
