package Concurrency;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TokenBucketOptimised {
    private final int defaultMaxCapacity;
    private final long defaultRefillIntervalMs;
    // Map to hold a separate bucket for each user
    private final Map<String, UserBucket> userBuckets = new ConcurrentHashMap<>();

    public TokenBucketOptimised(int maxCapacity, long refillIntervalMs) {
        this.defaultMaxCapacity = maxCapacity;
        this.defaultRefillIntervalMs = refillIntervalMs;
    }

    public void getToken(String userId) throws InterruptedException {
        // Atomically get or create the bucket for the user
        UserBucket bucket = userBuckets.computeIfAbsent(userId, k -> new UserBucket(defaultMaxCapacity, defaultRefillIntervalMs));
        // Delegate to the specific user's bucket
        bucket.consume();
    }

    public static void main(String[] args) throws InterruptedException {
        // Max capacity 1 (no burst allowed), 1 token every 5 seconds
        TokenBucketOptimised tokenBucket = new TokenBucketOptimised(1, 5000); 
        
        int size = 5;
        Thread[] threads = new Thread[size];
        
        for (int i = 0; i < size; i++) {
            // Simulate 2 different users (User-0 and User-1)
            final String userId = "User-" + (i % 2);
            threads[i] = new Thread(() -> {
                try {
                    tokenBucket.getToken(userId);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }, "Req-" + i);
        }

        // Start all threads at once to simulate concurrent requests
        for (int i = 0; i < size; i++) {
            threads[i].start();
            // Small delay to ensure order in output for demo purposes, 
            // but the logic works without it.
            Thread.sleep(100); 
        }
        
        for (int i = 0; i < size; i++) {
            threads[i].join();
        }
        System.out.println("DONE");
    }
}
