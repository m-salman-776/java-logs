package Project101.OrderInventory;

import org.junit.jupiter.api.Test;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

class InventoryServiceTest {

    @Test
    void testReserveAndComplete() {
        InventoryService service = new InventoryService();
        Product p1 = new Product(1, "Laptop");
        service.addProduct(p1, 10);

        assertEquals(10, service.getAvailableQuantity(1), "Initial quantity should be 10");

        Order order = service.createOrder(100, 1, 2);
        assertNotNull(order, "Order should be created");
        assertEquals(8, service.getAvailableQuantity(1), "Quantity should be 8 after reservation");

        service.completeOrder(order.id);
        assertEquals(8, service.getAvailableQuantity(1), "Quantity should remain 8 after completion (deducted from stock)");
    }

    @Test
    void testAutoRelease() throws InterruptedException {
        InventoryService service = new InventoryService();
        Product p1 = new Product(1, "Phone");
        service.addProduct(p1, 5);

        Order order = service.createOrder(101, 1, 2);
        assertEquals(3, service.getAvailableQuantity(1), "Quantity should be 3 after reservation");

        // Wait for timeout (3s configured in InventoryService + buffer)
        Thread.sleep(4000);

        assertEquals(5, service.getAvailableQuantity(1), "Quantity should return to 5 after auto-release");
    }

    @Test
    void testConcurrentOverselling() throws InterruptedException {
        InventoryService service = new InventoryService();
        Product p1 = new Product(1, "Console");
        int initialStock = 20;
        service.addProduct(p1, initialStock);

        int numThreads = 100;
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);
        CountDownLatch startLatch = new CountDownLatch(1);
        AtomicInteger successfulOrders = new AtomicInteger(0);

        for (int i = 0; i < numThreads; i++) {
            int userId = i;
            executor.submit(() -> {
                try {
                    startLatch.await();
                    Order order = service.createOrder(userId, 1, 1);
                    if (order != null) {
                        successfulOrders.incrementAndGet();
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }

        startLatch.countDown(); // Start all threads
        executor.shutdown();
        boolean finished = executor.awaitTermination(5, TimeUnit.SECONDS);
        assertTrue(finished, "Test timed out");

        assertEquals(initialStock, successfulOrders.get(), "Should only sell exactly the available stock");
        assertEquals(0, service.getAvailableQuantity(1), "Inventory should be 0");
    }

    @Test
    void testRaceConditionCompleteVsRelease() throws InterruptedException {
        // Run multiple times to increase chance of collision
        for (int i = 0; i < 50; i++) {
            runSingleRaceTest();
        }
    }

    private void runSingleRaceTest() throws InterruptedException {
        InventoryService service = new InventoryService();
        Product p1 = new Product(1, "Item");
        service.addProduct(p1, 1);

        Order order = service.createOrder(999, 1, 1);
        
        ExecutorService executor = Executors.newFixedThreadPool(2);
        CountDownLatch latch = new CountDownLatch(1);

        // Thread A: Complete
        executor.submit(() -> {
            try { latch.await(); } catch (Exception e) {}
            service.completeOrder(order.id);
        });

        // Thread B: Release (simulate timeout)
        executor.submit(() -> {
            try { latch.await(); } catch (Exception e) {}
            service.releaseInventory(order.id);
        });

        latch.countDown();
        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.SECONDS);

        int avail = service.getAvailableQuantity(1);
        // It must be either 0 (sold) or 1 (released). Any other value implies corruption.
        assertTrue(avail == 0 || avail == 1, "Available quantity must be 0 or 1, found: " + avail);
    }
}
