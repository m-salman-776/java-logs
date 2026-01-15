package Project101.RateLimiter.Implementations;
import Project101.RateLimiter.Policy;

public interface RateLimitAlgorithm {
    boolean isAllowed(String key, Policy policy);
}
