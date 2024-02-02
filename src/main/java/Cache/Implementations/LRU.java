package Cache.Implementations;

import Cache.Interface.Cache;
import Cache.Interface.UpdatePolicy;

import java.util.*;

public class LRU <K,V> implements Cache<K,V> {
    List<K> list;
    Long size;
    public LRU(List<K> list){
        this.list = list;
        this.size = 0L;
    }

    @Override
    public V get(K key) {
        return null;
    }

    @Override
    public void put(K key, V val) {

    }
}
