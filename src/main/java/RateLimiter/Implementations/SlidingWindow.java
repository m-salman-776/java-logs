package RateLimiter.Implementations;

import RateLimiter.Interface.RateLimiter;
import RateLimiter.Interface.RateLimiterStorage;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class SlidingWindow implements RateLimiter {

    RateLimiterStorage storage;

    public SlidingWindow(RateLimiterStorage storage){
        this.storage = storage;
    }
    @Override
    public boolean allowRequest(String clientId, int rateLimit, int timeWindowInSeconds) {
        return false;
    }
}