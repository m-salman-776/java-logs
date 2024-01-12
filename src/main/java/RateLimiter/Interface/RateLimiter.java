package RateLimiter.Interface;

public interface RateLimiter {
    boolean allowRequest(String clientId, int rateLimit, int timeWindowInSeconds);
}
