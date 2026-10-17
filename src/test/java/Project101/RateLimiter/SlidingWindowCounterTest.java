package Project101.RateLimiter;

import Project101.RateLimiter.Implementations.SlidingWindowCounter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

public class SlidingWindowCounterTest {
    SlidingWindowCounter rateLimiter;
    private final long MAX_REQUESTS = 5;
    private final long WINDOW_SIZE_MS = 100;
    @BeforeEach
    void setUp(){
        rateLimiter = new SlidingWindowCounter(MAX_REQUESTS,WINDOW_SIZE_MS);
    }
    @Test
    void testAllowRequest_WithinCapacity() {
        // Should successfully consume up to MAX_REQUESTS
        for (int i = 0; i < MAX_REQUESTS; i++) {
            assertTrue(rateLimiter.allowRequest("user1", 1), "Request " + (i + 1) + " should be allowed");
        }
    }
    @Test
    void testAllowRequest_RejectsWhenEmpty(){
        // Exhaust the window capacity
        rateLimiter.allowRequest("user1",MAX_REQUESTS);

        // The very next request must fail
        assertFalse(rateLimiter.allowRequest("user1",1),"should fail");
    }

    @Test
    void testWindowAdvancement_PartialRecovery() throws InterruptedException{
        // Exhaust the current window
        assertTrue(rateLimiter.allowRequest("user1", MAX_REQUESTS));
        assertFalse(rateLimiter.allowRequest("user1", 1));

        // Sleep just long enough to cross into the NEXT window, but not enough to clear the previous window.
        // Waiting 1 full window size guarantees we are in the next window, where the old requests carry a weight < 1.

        Thread.sleep(WINDOW_SIZE_MS + 20);
        assertTrue(rateLimiter.allowRequest("user1", 1), "Should recover partial capacity in the next window");

    }
    @Test

    void testMultipleWindowsSkipped_FullRecovery() throws InterruptedException{
        // Exhaust the current window
        assertTrue(rateLimiter.allowRequest("user1", MAX_REQUESTS));

        // Sleep for MORE than 2 windows. This forces 'skippedWindow > 1' in the advanceWindow() method,
        // resetting both previousWindowCount and currentWindowCount to 0.
        Thread.sleep(WINDOW_SIZE_MS * 3);

        // Should have 100% full capacity again
        assertTrue(rateLimiter.allowRequest("user1", MAX_REQUESTS), "Should fully reset after multiple windows pass");
        assertFalse(rateLimiter.allowRequest("user1", 1));
    }

    @Test
    void testUserIsolation() {
        // User 1 exhausts their limit
        assertTrue(rateLimiter.allowRequest("user1", MAX_REQUESTS));
        assertFalse(rateLimiter.allowRequest("user1", 1));

        // User 2 should be completely unaffected
        assertTrue(rateLimiter.allowRequest("user2", MAX_REQUESTS), "User 2 should have independent capacity");
        assertFalse(rateLimiter.allowRequest("user2", 1));
    }

    @Test
    void testConcurrency_ThreadSafety() throws InterruptedException {
        int numberOfThreads = 50;
        ExecutorService executor = Executors.newFixedThreadPool(numberOfThreads);

        CountDownLatch startLatch = new CountDownLatch(1);
        CountDownLatch completionLatch = new CountDownLatch(numberOfThreads);

        AtomicInteger successfulRequests = new AtomicInteger(0);
        AtomicInteger failedRequests = new AtomicInteger(0);

        for (int i = 0; i < numberOfThreads; i++) {
            executor.submit(() -> {
                try {
                    startLatch.await(); // Wait for the green light
                    if (rateLimiter.allowRequest("concurrent_user", 1)) {
                        successfulRequests.incrementAndGet();
                    } else {
                        failedRequests.incrementAndGet();
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    completionLatch.countDown();
                }
            });
        }

        // Release all 50 threads at the exact same time
        startLatch.countDown();
        completionLatch.await();
        executor.shutdown();

        // The synchronized block should ensure exactly MAX_REQUESTS succeed, no more.
        assertEquals(MAX_REQUESTS, successfulRequests.get(), "Exactly " + MAX_REQUESTS + " requests should succeed");
        assertEquals(numberOfThreads - MAX_REQUESTS, failedRequests.get(), "The remaining requests should fail");
    }

}
