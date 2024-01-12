package RateLimiter.Interface;
public interface RateLimiterStorage {
    void incrementCounter(String key);

    int getCounter(String key);

    void setTimestamp(String key, long timestamp);

    long getTimestamp(String key);
}
