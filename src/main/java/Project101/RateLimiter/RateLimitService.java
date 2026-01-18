package Project101.RateLimiter;
import Project101.RateLimiter.Implementations.RateLimitAlgorithm;
import Project101.RateLimiter.Implementations.RateLimiterStrategy;
import Project101.RateLimiter.Implementations.SlidingWindow;
import java.util.List;

public class RateLimitService {
    private final RateLimitConfig config;
    RateLimitAlgorithm rateLimiterAlgo ;
    public RateLimitService(){
        this.config = new RateLimitConfig();
        this.rateLimiterAlgo = new SlidingWindow();
    }
    public void setRateLimitAlgorithm(RateLimitAlgorithm rateLimiterAlgo){
        this.rateLimiterAlgo = rateLimiterAlgo;
    }
    public void addConfig(String domain,String key,String value,Policy policy){
        this.config.addDomainConfig(domain,key,value,policy);
    }
    public boolean isAllowed(String domain, List<Request> requests){
        DomainConfig domainConfig = this.config.getDomainConfig(domain);
        if (domainConfig == null)  {
            return true;
        }
        for (Request request : requests){
            String key = String.format("%s:%s",request.getKey(),request.getValue());
            Rule rule = domainConfig.getDescriptor(key);
            if (rule == null) continue;
            Policy policy = rule.getPolicy();
            if (policy == null) continue;
//            RateLimitAlgorithm algorithm = RateLimiterStrategy.getRateLimiter(policy.getAlgorithm());
            if (!rateLimiterAlgo.isAllowed(key,policy)) {
                return false;
            }
        }
        return true;
    }
}
