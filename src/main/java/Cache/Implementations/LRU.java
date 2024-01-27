package Cache.Implementations;

import Cache.Interface.UpdatePolicy;

import java.util.*;

public class LRU <K,V> implements UpdatePolicy<K> {

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
