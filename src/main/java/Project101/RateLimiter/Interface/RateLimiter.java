package Project101.RateLimiter.Interface;

public interface RateLimiter {
    boolean allowRequest(String clientId, int rateLimit, int timeWindowInSeconds);
}
