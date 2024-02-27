package LLD.RateLimiter.Implementations;

import LLD.RateLimiter.Interface.RateLimiter;
import LLD.RateLimiter.Interface.RateLimiterStorage;

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
