package Project101.RateLimiter;

import com.amazonaws.services.dynamodbv2.xspec.S;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String []args) throws InterruptedException {
        String domain01 = "domain02";
        String key = "user";
        String value = "user01";
        Policy policy = new Policy("user_policy_limit",1, TimeUnit.SECONDS,RateLimiterAlgo.SLIDING_WINDOW);
        RateLimitService rateLimitService = new RateLimitService();
        rateLimitService.addConfig(domain01,key,value,policy);
        Request request01 = new Request("user","user01");
        Request request02 = new Request("user","user01");
        Request request03 = new Request("user","user01");
        System.out.println(rateLimitService.isAllowed(domain01,List.of(request01)));
        Thread.sleep(500);
        System.out.println(rateLimitService.isAllowed(domain01,List.of(request02)));
        System.out.println("DONE");
    }
}
