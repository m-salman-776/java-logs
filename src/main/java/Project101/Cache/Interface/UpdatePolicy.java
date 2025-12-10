package Project101.Cache.Interface;

public interface UpdatePolicy<K> {
    void onPut(K key);
    K onAccess(K key);
}

