package Project101.RateLimiter;

import Project101.RateLimiter.Interface.RateLimiter;

public class RateLimitService {
    private RateLimiter rateLimiter;
    public RateLimitService(RateLimiter rateLimiter){
        this.rateLimiter = rateLimiter;
    }
    public boolean allowed(String clientId, int rateLimit, int timeWindowInSeconds) {
        return rateLimiter.allowRequest(clientId,rateLimit,timeWindowInSeconds);
    }
}
