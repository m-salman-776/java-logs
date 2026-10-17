package Project101.RateLimiter;

import Project101.RateLimiter.Implementations.TokenBucket;
import com.amazonaws.services.dynamodbv2.xspec.S;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String []args) {
        TokenBucket ratelimiter = new TokenBucket(10,6000);
        ratelimiter.allowRequest("key01",1);
    }
}
