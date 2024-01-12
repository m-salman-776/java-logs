package RateLimiter.Implementations;

import RateLimiter.Interface.RateLimiter;
import RateLimiter.Interface.RateLimiterStorage;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ArrayBlockingQueue;

public class TokenBucket implements RateLimiter {
    RateLimiterStorage storage;
    public TokenBucket(RateLimiterStorage storage){
        this.storage = storage;
    }
    @Override
    public boolean allowRequest(String clientId, int rateLimit, int timeWindowInSeconds) {
        return false;
    }
}
