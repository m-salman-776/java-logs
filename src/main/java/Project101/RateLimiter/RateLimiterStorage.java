package Project101.RateLimiter;

import java.util.List;

public interface RateLimiterStorage {
    // Basic Counter Ops (For Fixed Window)
    long increment(String key);
    void expire(String key, int seconds);

    // Sorted Set Ops (For Sliding Window Log)
    void addSortedSet(String key, double score, String member);
    long removeRangeByScore(String key, double min, double max);
    long countSortedSet(String key, double min, double max);

    // Scripting Ops (For Token Bucket / Advanced Logic)
    // Runs a Lua script atomically on the Redis server
    Object eval(String script, List<String> keys, List<String> args);
}
