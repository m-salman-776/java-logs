package Project101.RateLimiter.Implementations;

import Project101.RateLimiter.Policy;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TokenBucket implements RateLimitAlgorithm {
    // key could usedId / combination of anything
    Map<String, Bucket> bucketMap = new ConcurrentHashMap<>();
    public TokenBucket(){

    }
    public synchronized void addBucket(String key,int maxToken,long refillMs){
        this.bucketMap.computeIfAbsent(key,k -> new Bucket(maxToken,refillMs));
    }
    @Override
    public boolean isAllowed(String key, Policy policy) {
//        Bucket bucket =
        return false;
//        Bucket bucket = this.bucketMap.putIfAbsent();
    }

}
class Bucket {
    long refillMs;
    long lastRefillTime;
    int currentToken;
    int maxToken;
    public Bucket(int maxToken, long refillMs){
        this.maxToken = maxToken;
        this.refillMs = refillMs;
        this.currentToken = maxToken;
        this.lastRefillTime = System.currentTimeMillis();
    }
    public synchronized void consume() throws InterruptedException {
        refill();
        while (currentToken == 0){
            long elapsedTime = System.currentTimeMillis() - lastRefillTime;
            if (refillMs > elapsedTime) { // refil ms yet to be reached;
                this.wait(refillMs-elapsedTime);
            }
            refill();
        }
        currentToken -= 1;
    }
    private void refill(){
        long elapsedTime = System.currentTimeMillis() - lastRefillTime;
        if (elapsedTime < refillMs) {
            return;
        }
        int tokenToAdd = (int) (elapsedTime / refillMs);
        currentToken = Math.min(currentToken+tokenToAdd,maxToken);
        lastRefillTime += tokenToAdd * refillMs;
    }

}