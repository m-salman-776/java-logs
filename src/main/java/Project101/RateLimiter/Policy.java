package Project101.RateLimiter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.concurrent.TimeUnit;

@AllArgsConstructor
@Getter
@Setter
public class Policy {
    String policy_name;
    int limit;
    TimeUnit unit;
    RateLimiterAlgo algorithm = RateLimiterAlgo.SLIDING_WINDOW;
}
