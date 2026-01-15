package Project101.RateLimiter;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;
@Getter
@Setter
public class DomainConfig {
    // rule_key:rule_value -> Rule
    Map<String, Rule> descriptor = new HashMap<>();
    public DomainConfig(){
    }
    public Rule getDescriptor(String key){
        return this.descriptor.get(key);
    }
    public void upsertPolicy(String key,String value,Policy policy){
        String mergedKey = String.format("%s:%s",key,value);
        this.descriptor.put(mergedKey,new Rule(key,value,policy));
    }

}
