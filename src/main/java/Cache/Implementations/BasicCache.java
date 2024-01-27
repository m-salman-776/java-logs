package Cache.Implementations;

import Cache.Interface.Cache;
import Cache.Interface.UpdatePolicy;

import java.util.Map;

public class BasicCache <K,V>implements Cache<K,V> {
    Map<K,V> cache;
    UpdatePolicy<K> evictionPolicy;

    Long capacity;
    public BasicCache(Map<K,V> cache, UpdatePolicy<K> evictionPolicy, Long capacity){
        this.evictionPolicy = evictionPolicy;
        this.cache = cache;
        this.capacity = capacity;
    }
    @Override
    public V get(K key) {
        V val = cache.getOrDefault(key,null);
        if (val == null) return null;
        evictionPolicy.onAccess(key);
        return val;
    }


    @Override
    public  void put(K key, V val) {
        evictionPolicy.onPut(key);
//        if (this.cache.size() > this.c)
    }
}
