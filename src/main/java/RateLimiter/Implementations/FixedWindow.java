package RateLimiter.Implementations;

import RateLimiter.Interface.RateLimiter;
import RateLimiter.Interface.RateLimiterStorage;

public class FixedWindow implements RateLimiter {
    RateLimiterStorage storage;

    public FixedWindow(RateLimiterStorage storage){
        this.storage = storage;
    }
    @Override
    public boolean allowRequest(String clientId, int rateLimit, int timeWindowInSeconds) {
        return false;
    }
}
