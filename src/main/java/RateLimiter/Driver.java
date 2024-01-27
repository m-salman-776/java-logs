package RateLimiter;

import RateLimiter.Implementations.FixedWindow;
import RateLimiter.Implementations.RedisStorage;
import RateLimiter.Interface.RateLimiter;
import RateLimiter.Interface.RateLimiterStorage;

public class Driver {
    public static void main(String []args){
//        RateLimiterStorage storage = new RedisStorage("redis",6357,345);
//        RateLimiter rateLimiter = new FixedWindow(storage);
//        RateLimitService service = new RateLimitService(rateLimiter);
//        service.allowed("s",1,123);
    }
}
