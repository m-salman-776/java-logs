package Cache.Implementations;

import java.util.HashMap;
import java.util.Map;

public class LFU {
    int capacity;
    int prevLevel ;
    Map<Integer,LinkedList> keyToVal;
    Map<Integer,Integer> frequency;
    LFU(int capacity){
        this.capacity = capacity;
        prevLevel = 0;
        keyToVal = new HashMap<>();
        frequency = new HashMap<>();
    }
    int get(int key){
        if (!frequency.containsKey(key)) return -1;
        Node currentNode = keyToVal.get(prevLevel).remove(key);
        if (keyToVal.get(prevLevel).size == 0){
            this.prevLevel += 1;
        }
        keyToVal.get(prevLevel).add(key,currentNode.val);
        frequency.put(key,frequency.get(key)+1);
        return currentNode.val;
    }
    void put(int key, int val){
        if (frequency.containsKey(key)){
            int count = frequency.get(key);
            Node curr = keyToVal.get(prevLevel).remove(key);
            if (keyToVal.get(prevLevel).size == 0){
                this.prevLevel += 1;
            }
            frequency.put(key,count+1);
            keyToVal.get(prevLevel).add(key,val);
        }else {
            Node node = new Node(val);
            if (frequency.size() == capacity){
//                node = keyToVal.get(prevLevel).removeLast();
                if (keyToVal.get(prevLevel).size == 0){
                    this.prevLevel += 1;
                }
            }else {
                frequency.put(key,1);
                keyToVal.get(prevLevel).add(key,val);
            }
        }
    }
}
class LinkedList{
    private Map<Integer,Node> map;
    private Node head;
    private Node tail;
    int size ;
    LinkedList(){
        map = new HashMap<>();
        head = new Node(0);
        tail = new Node(0);
        head.next = tail;
        tail.prev = head;
        this.size = 0;
    }
    Node remove(int key){
        Node node = map.get(key);
        map.remove(key);
        //[--n---]
        node.prev.next = node.next;
        node.next.prev = node.prev;
        this.size -= 1;
        return node;
    }
    Node removeLast(){
        Node lastNode = tail.prev;
        lastNode.prev.next = tail;
        tail.prev = lastNode.prev;
        map.remove(lastNode.val);
        return lastNode;
    }
    void add(int key, int val){
        Node node = new Node(val);
        //[0-[node]--0]
        node.next = head.next;
        node.next.prev = node;

        node.prev = head;
        head.next = node;
        map.put(key,node);
        this.size += 1;
    }
}

class Node {
    int val;
    Node next;
    Node prev;
    Node(int val){
        this.val = val;
        this.next = this.prev = null;
    }
}

