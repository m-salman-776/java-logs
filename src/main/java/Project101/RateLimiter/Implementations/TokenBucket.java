package Project101.RateLimiter.Implementations;

import Project101.RateLimiter.Interface.RateLimiter;
import Project101.RateLimiter.Interface.RateLimiterStorage;

public class TokenBucket implements RateLimiter {
    RateLimiterStorage storage;
    public TokenBucket(RateLimiterStorage storage){
        this.storage = storage;
    }
    @Override
    public boolean allowRequest(String clientId, int rateLimit, int timeWindowInSeconds) {
        return false;
    }
}
