package Project101.RateLimiter.Implementations;

import Project101.RateLimiter.RateLimiterAlgo;

public class RateLimiterStrategy {
    public static RateLimitAlgorithm getRateLimiter(RateLimiterAlgo type){
        switch (type){
            case FIXED_WINDOW: return new LeakyBucket();
            default: return new SlidingWindow();
        }
    }
}
