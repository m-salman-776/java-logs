package Project101.Cache.Interface;

public interface Cache<K,V> {
    V get(K key);
    void put(K key,V val);

}
