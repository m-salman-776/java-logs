package RateLimiter.Implementations;

import RateLimiter.Interface.RateLimiterStorage;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;

public class RedisStorage implements RateLimiterStorage {
    @Override
    public void incrementCounter(String key) {

    }

    @Override
    public int getCounter(String key) {
        return 0;
    }

    @Override
    public void setTimestamp(String key, long timestamp) {

    }

    @Override
    public long getTimestamp(String key) {
        return 0;
    }
//    private final JeRdisPool jedisPool;
//    private int timeWindowInSeconds;
//
//    public RedisStorage(String redisHost, int redisPort,int timeWindowInSeconds) {
//        JedisPoolConfig poolConfig = new JedisPoolConfig();
//        this.timeWindowInSeconds = timeWindowInSeconds;
//        this.jedisPool = new JedisPool(poolConfig, redisHost, redisPort);
//    }
//
//    @Override
//    public void incrementCounter(String key) {
//        try (RedisProperties.Jedis jedis = jedisPool.getResource()) {
//            jedis.incr(key);
//            jedis.expire(key, timeWindowInSeconds);
//        }
//    }
//
//    @Override
//    public int getCounter(String key) {
//        try (Jedis jedis = jedisPool.getResource()) {
//            String value = jedis.get(key);
//            return value != null ? Integer.parseInt(value) : 0;
//        }
//    }
//
//    @Override
//    public void setTimestamp(String key, long timestamp) {
//        try (Jedis jedis = jedisPool.getResource()) {
//            jedis.setex(key, timeWindowInSeconds, String.valueOf(timestamp));
//        }
//    }
//
//    @Override
//    public long getTimestamp(String key) {
//        try (Jedis jedis = jedisPool.getResource()) {
//            String value = jedis.get(key);
//            return value != null ? Long.parseLong(value) : 0L;
//        }
//    }

}
