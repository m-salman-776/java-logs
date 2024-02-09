package LLD.Cache.Interface;

public interface UpdatePolicy<K> {
    void onPut(K key);
    K onAccess(K key);
}

