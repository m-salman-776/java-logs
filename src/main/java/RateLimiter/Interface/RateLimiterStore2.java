package RateLimiter.Interface;

import java.util.List;

public interface RateLimiterStore2 {
    List<Integer> getTimestamp(String userKey);
    void addTimestamp(String userKey,Integer timestamp);
    void removeAllTimestamp(String userKey);
}
