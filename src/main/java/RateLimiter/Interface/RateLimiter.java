package RateLimiter.Interface;

public interface RateLimiter {
    boolean allowRequest(Long epoch);
}
