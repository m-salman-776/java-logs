package Cache.Interface;

public interface EvictionPolicy<K> {
    void onPut(K key);
    K onAccess(K key);
}

