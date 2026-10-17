package Project101.RateLimiter;

import Project101.RateLimiter.Implementations.TokenBucket;
import Project101.RateLimiter.Implementations.SlidingWindowCounter;

public class RateLimiterStrategy {
    public static RateLimitAlgorithm getRateLimiter(RateLimiterAlgo type){
        switch (type){
            case SLIDING_WINDOW_COUNTER: return new SlidingWindowCounter(10,6000);
//            case TOKEN_BUCKET: return new LockFreeTokenBucket(100,6000);
            default: return new TokenBucket(100,6000);
        }
    }
}
