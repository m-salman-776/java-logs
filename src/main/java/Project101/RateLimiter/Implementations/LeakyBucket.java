package Project101.RateLimiter.Implementations;

import Project101.RateLimiter.Policy;
import Project101.RateLimiter.RateLimiterStorage;

public class LeakyBucket implements RateLimitAlgorithm {


    @Override
    public boolean isAllowed(String key, Policy policy) {
        return false;
    }
}
