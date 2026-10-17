package Project101.RateLimiter.Implementations;

import Project101.RateLimiter.RateLimitAlgorithm;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SlidingWindowCounter implements RateLimitAlgorithm {
    private final Map<String, UserWindow> map = new ConcurrentHashMap<>();
    private final long maxAllowedRequests;
    private final long windowSizeMs;

    public SlidingWindowCounter(long maxAllowedRequests, long windowSizeMs) {
        this.maxAllowedRequests = maxAllowedRequests;
        this.windowSizeMs = windowSizeMs;
    }

    @Override
    public boolean allowRequest(String key, long tokenRequested) {
        long currentTime = System.currentTimeMillis();
        UserWindow userWindow = this.map.computeIfAbsent(key, k -> new UserWindow(currentTime, windowSizeMs));
        return userWindow.tryConsume(currentTime, windowSizeMs, maxAllowedRequests, tokenRequested);
    }

    private static class UserWindow {
        private long currentWindowStart;
        private int previousWindowCount;
        private int currentWindowCount;

        public UserWindow(long startTime, long windowSizeMs) {
            this.currentWindowStart = (startTime / windowSizeMs) * windowSizeMs;
            this.previousWindowCount = 0;
            this.currentWindowCount = 0;
        }

        // Synchronizing per-instance guarantees strict atomicity per user key
        public synchronized boolean tryConsume(long currentTime, long windowSizeMs, long maxLimit, long requestToken) {
            long elapsedDuration = currentTime - this.currentWindowStart;

            // 1. Advance window if a new time block has started
            if (elapsedDuration >= windowSizeMs) {
                advanceWindow(currentTime, windowSizeMs);
                elapsedDuration = currentTime - this.currentWindowStart;
            }

            // 2. Calculate weighted estimate
            double weight = (double) (windowSizeMs - elapsedDuration) / windowSizeMs;
            int estimatedCount = (int) (this.previousWindowCount * weight + this.currentWindowCount);

            // 3. Admission check
            if (estimatedCount + requestToken > maxLimit) {
                return false; // Rate limit exceeded!
            }

            // 4. Increment count safely within synchronized context
            this.currentWindowCount += requestToken;
            return true;
        }

        private void advanceWindow(long currentTime, long windowSizeMs) {
            long newStart = (currentTime / windowSizeMs) * windowSizeMs;
            long skippedWindow = (newStart - this.currentWindowStart) / windowSizeMs;

            if (skippedWindow == 1) {
                this.previousWindowCount = this.currentWindowCount;
            } else {
                this.previousWindowCount = 0;
            }

            this.currentWindowCount = 0;
            this.currentWindowStart = newStart;
        }
    }
}