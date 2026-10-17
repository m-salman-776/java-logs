package Project101.RateLimiter.Implementations;

import Project101.RateLimiter.RateLimitAlgorithm;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TokenBucket implements RateLimitAlgorithm {
    private final Map<String,Bucket> bucketMap = new ConcurrentHashMap<>();

    private final long defaultMaxToken ;
    private final long defaultRefillPeriodMs;

    public TokenBucket(long defaultMaxToken, long defaultRefillPeriodMs){
        this.defaultMaxToken = defaultMaxToken;
        this.defaultRefillPeriodMs = defaultRefillPeriodMs;
    }


    @Override
    public boolean allowRequest(String key, long tokenRequested) {
        Bucket bucket = bucketMap.computeIfAbsent(key,k -> new Bucket(defaultMaxToken,defaultRefillPeriodMs));
        return bucket.tryConsume(tokenRequested);
    }


    private static class Bucket {
        private final long maxToken;
        private final long refillPeriodMs;

        // Primitives replace Atomics
        private long currentToken;
        private long lastFillTimestamp;

        private Bucket(long maxToken, long refillPeriodMs) {
            this.maxToken = maxToken;
            this.refillPeriodMs = refillPeriodMs;
            this.currentToken = maxToken;
            this.lastFillTimestamp = System.currentTimeMillis();
        }

        // Synchronized guarantees strict atomicity for both variables at once
        public synchronized boolean tryConsume(long requestedToken) {
            refill(); // Safe to call here; we already hold the lock

            if (this.currentToken < requestedToken) {
                return false;
            }

            this.currentToken -= requestedToken;
            return true;
        }

        private void refill() {
            long now = System.currentTimeMillis();
            long elapsedDuration = now - this.lastFillTimestamp;

            if (elapsedDuration < refillPeriodMs) {
                return;
            }

            long tokensToAdd = elapsedDuration / refillPeriodMs;

            this.lastFillTimestamp += tokensToAdd * refillPeriodMs;
            this.currentToken = Math.min(this.currentToken + tokensToAdd, this.maxToken);

            /*
             * refilMs = 1000ms
             * elapsedTime = 2500ms
             * tokenToAdd = 2500ms / 1000ms = 2
             * timeConsumed = 2 * 1000 = 2000ms
             * remaining time = 2500-2000 = 500ms
             * lastRefillTime += 2 * 1000 (500ms save)
             * if simply do lastRefillTime = System.currentTimeMillis() which wrong
             * */
        }
    }
}