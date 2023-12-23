package Cache.Implementations;

import Cache.Interface.EvictionPolicy;
import ch.qos.logback.core.joran.sanity.Pair;

import java.util.*;

public class LRU <K,V> implements EvictionPolicy<K> {

    List<K> list;
    Long size;
    public LRU(List<K> list){
        this.list = list;
        this.size = 0L;
    }
    @Override
    public void onPut(K key) {
        if (this.list.size() >= size){

        }
    }

    @Override
    public K onAccess(K key) {
        return null;
    }
}
