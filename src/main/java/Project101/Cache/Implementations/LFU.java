package Project101.Cache.Implementations;

import Project101.Cache.Interface.Cache;

import java.util.HashMap;
import java.util.Map;

public class LFU<K,V> implements Cache<K,V> {
    Map<K,Integer> countMap;
    Map<K,V> valMap;
    Map<Integer,NodeList<K>> listMap;
    int cap;
    int lfuCount;
    public LFU(int cap){
        this.countMap = new HashMap<>();
        this.valMap = new HashMap<>();
        this.listMap = new HashMap<>();
        this.cap = cap;
        this.lfuCount = 0;
    }

    private void update(K key){
        int count = this.countMap.getOrDefault(key,1);
        this.listMap.getOrDefault(count,new NodeList<K>()).remove(key);
        this.listMap.computeIfAbsent(count+1,k-> new NodeList<>()).add(key);
        this.countMap.put(key,count+1);
        if (lfuCount == count && listMap.getOrDefault(count,new NodeList<>()).size() == 0){
            this.lfuCount += 1;
        }

    }
    @Override
    public V get(K key) {
        if (!this.valMap.containsKey(key)) return null;
        update(key);
        return this.valMap.get(key);
    }

    @Override
    public void put(K key, V val) {
        if (this.cap == 0) return;

        if (!valMap.containsKey(key) && this.listMap.get(lfuCount).size() == cap){
            Node<K> node = this.listMap.get(lfuCount).removeLast();
            this.countMap.remove(node.key);
            this.valMap.remove(node.key);
        }

        valMap.put(key,val);
        update(key);
        lfuCount = Math.min(lfuCount,this.countMap.get(key));
    }
}

class Node<K> {
    K key;
    Node<K> next;
    Node<K> prev;
    Node(K key){
        this.key = key;
    }
}
class NodeList <K>{
    Node<K> head;
    Node<K> tail;
    Map<K,Node<K>> map;
    NodeList(){
        map = new HashMap<>();
        head = new Node<>(null);
        tail = new Node<>(null);
        head.next = tail;
        tail.prev = head;
    }
    void  add(K key){
        Node<K> node = new Node<>(key);
        node.next = head.next;
        head.next.prev = node;

        node.prev = head;
        head.next = node;
        map.put(key,node);
    }
    int size(){
        return this.map.size();
    }
    void remove(K key){
        if (!map.containsKey(key)) return ;
        Node<K> node = map.get(key);
        node.prev.next = node.next;
        node.next.prev = node.prev;
        map.remove(node.key);
    }
    Node<K> removeLast(){
        Node<K> node = tail.prev;
        remove(node.key);
        return node;
    }
}