package LLD.RateLimiter.Implementations;

import LLD.RateLimiter.Interface.RateLimiter;
import LLD.RateLimiter.Interface.RateLimiterStorage;

import java.util.*;


public class SlidingWindow implements RateLimiter {

//    RateLimiterStorage storage;

    Map<String,TreeSet<Long>> stringTreeSetMap ;

    public SlidingWindow(RateLimiterStorage storage){
//        this.storage = storage;
        stringTreeSetMap = new HashMap<>();
    }
    @Override
    public boolean allowRequest(String clientId, int rateLimit, int timeWindowInSeconds) {

        long currentTime = System.currentTimeMillis() / 1000;
        TreeSet<Long> count = stringTreeSetMap.getOrDefault(clientId,new TreeSet<>());
        count = new TreeSet<>( count.tailSet(currentTime,true));
        stringTreeSetMap.put(clientId,count);
        stringTreeSetMap.get(clientId).add(currentTime);
        if (stringTreeSetMap.get(clientId).size() > rateLimit){
            return false;
        }
        return false;
    }
}