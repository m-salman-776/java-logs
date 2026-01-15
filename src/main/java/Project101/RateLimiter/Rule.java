package Project101.RateLimiter;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Rule {
    String key;   // user , message_type
    String value; // uAad , marketing
    Policy policy;
}
