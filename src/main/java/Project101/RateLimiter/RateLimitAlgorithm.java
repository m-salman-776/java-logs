package Project101.RateLimiter;

public interface RateLimitAlgorithm {
    boolean allowRequest(String key , long tokenRequested);
}
