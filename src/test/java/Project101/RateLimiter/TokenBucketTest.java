package Project101.RateLimiter;

import Project101.RateLimiter.Implementations.TokenBucket;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

public class TokenBucketTest {

    private TokenBucket rateLimiter;
    private final long MAX_TOKENS = 5;
    private final long REFILL_PERIOD_MS = 100; // 1 token every 100ms

    @BeforeEach
    void setUp() {
        // Runs before each test to ensure a clean state
        rateLimiter = new TokenBucket(MAX_TOKENS, REFILL_PERIOD_MS);
    }

    @Test
    void testAllowRequest_WithinCapacity() {
        // Should successfully consume up to MAX_TOKENS
        for (int i = 0; i < MAX_TOKENS; i++) {
            assertTrue(rateLimiter.allowRequest("user1", 1), "Request " + (i + 1) + " should be allowed");
        }
    }

    @Test
    void testAllowRequest_RejectsWhenEmpty() {
        // Drain the bucket completely
        assertTrue(rateLimiter.allowRequest("user1", MAX_TOKENS), "Should allow consuming all tokens at once");

        // Next request must fail
        assertTrue(rateLimiter.allowRequest("user1", 1), "Request should be rejected when bucket is empty");
    }

    @Test
    void testTokenRefill_Partial() throws InterruptedException {
        // Empty the bucket
        rateLimiter.allowRequest("user1", MAX_TOKENS);
        assertFalse(rateLimiter.allowRequest("user1", 1));

        // Wait just long enough for EXACTLY 1 token to refill (100ms + 20ms buffer for OS thread scheduling)
        Thread.sleep(REFILL_PERIOD_MS + 20);

        assertTrue(rateLimiter.allowRequest("user1", 1), "Should allow 1 request after refill period");
        assertFalse(rateLimiter.allowRequest("user1", 1), "Should reject second request as only 1 token refilled");
    }

    @Test
    void testTokenCap_PreventsOverflow() throws InterruptedException {
        // Empty the bucket
        rateLimiter.allowRequest("user1", MAX_TOKENS);

        // Wait long enough to theoretically generate 8 tokens
        Thread.sleep((MAX_TOKENS + 3) * REFILL_PERIOD_MS);

        // Consume up to MAX_TOKENS
        assertTrue(rateLimiter.allowRequest("user1", MAX_TOKENS), "Should allow up to max tokens");

        // The very next request MUST fail, proving it capped at 5 and didn't accumulate 8
        assertFalse(rateLimiter.allowRequest("user1", 1), "Bucket should enforce maxToken limit");
    }

    @Test
    void testUserIsolation() {
        // User 1 consumes all their tokens
        rateLimiter.allowRequest("user1", MAX_TOKENS);
        assertFalse(rateLimiter.allowRequest("user1", 1));

        // User 2 should still have full tokens in their isolated bucket
        assertTrue(rateLimiter.allowRequest("user2", MAX_TOKENS), "User 2 should be unaffected by User 1");
        assertFalse(rateLimiter.allowRequest("user2", 1));
    }

    @Test
    void testConcurrency_ThreadSafety() throws InterruptedException {
        int numberOfThreads = 50;
        ExecutorService executor = Executors.newFixedThreadPool(numberOfThreads);

        // Use Latches to ensure all threads hit the allowRequest method at the exact same millisecond
        CountDownLatch startLatch = new CountDownLatch(1);
        CountDownLatch completionLatch = new CountDownLatch(numberOfThreads);

        AtomicInteger successfulRequests = new AtomicInteger(0);
        AtomicInteger failedRequests = new AtomicInteger(0);

        for (int i = 0; i < numberOfThreads; i++) {
            executor.submit(() -> {
                try {
                    startLatch.await(); // Block until startLatch reaches 0
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

        // Fire all threads simultaneously
        startLatch.countDown();

        // Wait for all threads to finish execution
        completionLatch.await();
        executor.shutdown();

        // Assert that the synchronized block perfectly prevented race conditions
        assertEquals(MAX_TOKENS, successfulRequests.get(), "Exactly " + MAX_TOKENS + " requests should succeed");
        assertEquals(numberOfThreads - MAX_TOKENS, failedRequests.get(), "The remaining requests should fail");
    }
}