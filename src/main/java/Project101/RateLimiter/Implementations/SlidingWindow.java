package Project101.RateLimiter.Implementations;


import Project101.RateLimiter.Policy;
import Project101.RateLimiter.RateLimiterStorage;
import com.sun.source.tree.Tree;

import java.util.HashMap;
import java.util.SortedSet;
import java.util.TreeSet;

public class SlidingWindow implements RateLimitAlgorithm {
    RateLimiterStorage store;
    HashMap<String, SortedSet<Long>> storage;
    public SlidingWindow(){
        this.storage = new HashMap<>();
    }
    @Override
    public boolean isAllowed(String key, Policy policy) {
        long now = System.currentTimeMillis();
        long windowSizeMs = policy.getUnit().toMillis(1);
        long windowStart = now - windowSizeMs;

        this.storage.computeIfAbsent(key,k-> new TreeSet<>());

        SortedSet<Long> validTimeStamps = this.storage.get(key).tailSet(windowStart);
        this.storage.put(key,validTimeStamps);
        if (validTimeStamps.size() >= policy.getLimit()) {
            return false;
        }
        this.storage.get(key).add(now);
        return true;
    }
}