package Project101.RateLimiter;

import java.util.HashMap;
import java.util.Map;

public class RateLimitConfig {
    // domain_name -> Domain Config;
    Map<String,DomainConfig> domainConfigMap = new HashMap<>();
    public RateLimitConfig(){}
    public DomainConfig getDomainConfig(String domain){
        return this.domainConfigMap.get(domain);
    }
    public void addDomainConfig(String domain,String key,String value,Policy policy){
        this.domainConfigMap.putIfAbsent(domain,new DomainConfig());
        this.domainConfigMap.get(domain).upsertPolicy(key,value,policy);
    }
}
