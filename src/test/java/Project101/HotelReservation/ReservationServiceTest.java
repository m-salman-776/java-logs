package Project101.HotelReservation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

class ReservationServiceTest {

    private InventoryService inventoryService;
    private ReservationService reservationService;

    @BeforeEach
    void setUp() {
        // Initialize dependencies
        inventoryService = new InventoryService();
        reservationService = new ReservationService(inventoryService);
    }

    @Test
    void testCreateReservationSuccess() {
        int roomType = 1;
        int capacity = 10;
        inventoryService.updateRooms(roomType, capacity);

        LocalDate start = LocalDate.now();
        LocalDate end = start.plusDays(2);

        // Create reservation for 2 rooms
        Reservation res = reservationService.createReservation(start, end, roomType, 2);

        assertNotNull(res);
        assertEquals(ReservationStatus.CREATED, res.getReservationStatus());
        assertEquals(2, res.getQuantity());
        
        // Verify inventory is deducted (10 - 2 = 8)
        int available = inventoryService.getAvailableInventory(start, roomType);
        assertEquals(8, available);
    }

    @Test
    void testCreateReservationInsufficientInventory() {
        int roomType = 1;
        int capacity = 1; // Only 1 room available
        inventoryService.updateRooms(roomType, capacity);

        LocalDate start = LocalDate.now();
        LocalDate end = start.plusDays(2);

        // Try to reserve 2 rooms
        Reservation res = reservationService.createReservation(start, end, roomType, 2);

        assertNull(res, "Should fail to create reservation when inventory is insufficient");
    }

    @Test
    void testConfirmReservation() {
        int roomType = 1;
        inventoryService.updateRooms(roomType, 10);
        LocalDate start = LocalDate.now();
        LocalDate end = start.plusDays(1);

        Reservation res = reservationService.createReservation(start, end, roomType, 1);
        reservationService.confirmReservation(res.id);

        assertEquals(ReservationStatus.CONFIRMED, res.getReservationStatus());
    }

    @Test
    void testCancelReservationRestoresInventory() {
        int roomType = 1;
        inventoryService.updateRooms(roomType, 10);
        LocalDate start = LocalDate.now();
        LocalDate end = start.plusDays(1);

        Reservation res = reservationService.createReservation(start, end, roomType, 2);
        assertEquals(8, inventoryService.getAvailableInventory(start, roomType));

        reservationService.cancelReservation(res.id);

        assertEquals(ReservationStatus.CANCELLED, res.getReservationStatus());
        // Inventory should be restored to 10
        assertEquals(10, inventoryService.getAvailableInventory(start, roomType));
    }

    @Test
    void testConcurrencyBooking() throws InterruptedException {
        int roomType = 1;
        int capacity = 50;
        inventoryService.updateRooms(roomType, capacity);

        int threadCount = 100; // More requests than capacity
        ExecutorService executor = Executors.newFixedThreadPool(20);
        CountDownLatch latch = new CountDownLatch(threadCount);
        AtomicInteger successCount = new AtomicInteger(0);

        LocalDate start = LocalDate.now();
        LocalDate end = start.plusDays(1);

        for (int i = 0; i < threadCount; i++) {
            executor.submit(() -> {
                try {
                    Reservation res = reservationService.createReservation(start, end, roomType, 1);
                    if (res != null) {
                        successCount.incrementAndGet();
                    }
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await(10, TimeUnit.SECONDS);
        executor.shutdown();

        // Should exactly match capacity, no overbooking
        assertEquals(capacity, successCount.get());
        assertEquals(0, inventoryService.getAvailableInventory(start, roomType));
    }

    @Test
    void testDoubleCancellationRaceCondition() throws InterruptedException {
        int roomType = 1;
        inventoryService.updateRooms(roomType, 10);
        LocalDate start = LocalDate.now();
        LocalDate end = start.plusDays(1);

        Reservation res = reservationService.createReservation(start, end, roomType, 1);
        assertEquals(9, inventoryService.getAvailableInventory(start, roomType));

        int threadCount = 20;
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(threadCount);

        // Try to cancel the SAME reservation from multiple threads simultaneously
        for (int i = 0; i < threadCount; i++) {
            executor.submit(() -> {
                try {
                    reservationService.cancelReservation(res.id);
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await(5, TimeUnit.SECONDS);
        executor.shutdown();

        assertEquals(ReservationStatus.CANCELLED, res.getReservationStatus());
        // Inventory should be restored exactly once (9 + 1 = 10). 
        // If race condition exists, it might be > 10.
        assertEquals(10, inventoryService.getAvailableInventory(start, roomType));
    }

    @Test
    void testConfirmPreventsAutoCancellation() throws InterruptedException {
        int roomType = 1;
        inventoryService.updateRooms(roomType, 10);
        LocalDate start = LocalDate.now();
        LocalDate end = start.plusDays(1);

        Reservation res = reservationService.createReservation(start, end, roomType, 1);
        reservationService.confirmReservation(res.id);
        
        // Wait for auto-cancel timer (10s) to expire
        // Note: This test takes >10s to run.
        Thread.sleep(10500);

        assertEquals(ReservationStatus.CONFIRMED, res.getReservationStatus());
        // Inventory should still be held (10 - 1 = 9)
        assertEquals(9, inventoryService.getAvailableInventory(start, roomType));
    }
}