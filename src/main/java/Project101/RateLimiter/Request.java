package Project101.RateLimiter;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Request {
    String key;
    String value;
}
