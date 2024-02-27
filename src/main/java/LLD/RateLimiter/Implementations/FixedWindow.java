package LLD.RateLimiter.Implementations;

import LLD.RateLimiter.Interface.RateLimiter;
import LLD.RateLimiter.Interface.RateLimiterStorage;

import java.util.HashMap;
import java.util.Map;

public class FixedWindow implements RateLimiter {
//    RateLimiterStorage storage;

    Map<String,Integer> requestCounter ;
    long maxAllowedRequest;
    long windowSizeInSecond;
    long lastResetTime;
    public FixedWindow(RateLimiterStorage storage){
        requestCounter = new HashMap<>();
        this.maxAllowedRequest = 10;
        this.windowSizeInSecond = 60;
        this.lastResetTime = System.currentTimeMillis() / 1000;
    }


    @Override
    public boolean allowRequest(String clientId, int rateLimit, int timeWindowInSeconds) {
        long currTime = System.currentTimeMillis() / 1000;
        if (currTime > lastResetTime + windowSizeInSecond){
            requestCounter.put(clientId,0);
        }
        if (requestCounter.getOrDefault(clientId,0) > maxAllowedRequest){
            return false;
        }
        requestCounter.put(clientId,requestCounter.get(clientId)+1);
        return true;
    }
}
