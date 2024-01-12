package RateLimiter;

import RateLimiter.Interface.RateLimiter;
import RateLimiter.Interface.RateLimiterStorage;

public class RateLimitService {
    private RateLimiter rateLimiter;
    public RateLimitService(RateLimiter rateLimiter){
        this.rateLimiter = rateLimiter;
    }
    public boolean allowed(String clientId, int rateLimit, int timeWindowInSeconds) {
        return rateLimiter.allowRequest(clientId,rateLimit,timeWindowInSeconds);
    }
}
