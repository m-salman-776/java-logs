import java.util.*;

public class main {
    static Map<String,Integer> mp;
    public static int makeNumber(String num){
        String [] words = num.split(" ");
        Set<String> st = new HashSet<>();
        st.add("million");
        st.add("thousand");
        st.add("hundred");
        int val = 0;
        int ans = 0;
        for (String s : words){
            int intVal = mp.get(s);
            if (st.contains(s)){
               val *= intVal;
               ans += val;
               val = 0;
            }else{
                val += intVal;
            }
        }
        ans += val;
        return ans;
    }
    public static void main(String []args){
        mp = new HashMap<>();

        int size = 10;
        String str[] = new String[size];
        int arr[][] = new int[size][2];
        for (int i=0;i<size;i++){
            int val = makeNumber(str[i]);
            arr[i] = new int[]{val,i};
        }
        Arrays.sort(arr,(a,b)->Integer.compare(a[0],b[0]));
        for (int []e : arr){
            System.out.print(str[e[1]]+" ");
        }
        System.out.println("DINE");
    }
}

/*
zero->0
* one,two,three,four,five,six,seven,eight,nine
* ten,eleven,twelve,thirteen,fourteen,fifteen,sixteen,seventeen,eighteen,nineteen
* twenty,thirty,forty,fifty,sixty,seventy,eighty,ninty
* hundred,thousand
* million
*

five hundred thousand
[one, million, thirty, five, thousand, twelve] ->
num = 12
seventy five",
"two hundred forty one",
"three thousand",
* */


class Node{
    int key;
    Node next ;
    Node prev;
    Node(int key){
        this.key = key;
        this.next = this.prev = null;
    }
}
class NodeList {
    Node head;
    Node tail;
    Map<Integer,Node>mp;
    NodeList(){
        head = new Node(0);
        tail = new Node(0);
        head.next = tail;
        tail.prev = head;
        mp = new HashMap<>();
    }
    void add(int key){
        Node node = new Node(key);
        node.next = head.next;
        head.next.prev = node;
        node.prev = head;
        head.next = node;
        mp.put(key,node);
    }
    void remove(int key){
        if (mp.size() == 0) return ;
        Node node = mp.get(key);
        node.prev.next = node.next;
        node.next.prev = node.prev;
        mp.remove(node.key);
    }
    Node get(int key){
        return mp.get(key);
    }
    Node removeLast(){
//        if (mp.size() == 0) return ;
        Node node = tail.prev;
        remove(node.key);
        return node;
    }
    int size(){
        return this.mp.size();
    }
}
class LFUCache {
    Map<Integer,Integer>freq;
    Map<Integer, NodeList> listMap;
    Map<Integer,Integer> valMap;
    int cap;
    int lfuCount;
    public LFUCache(int capacity) {
        freq = new HashMap<>();
        listMap = new HashMap<>();
        valMap = new HashMap<>();
        this.cap = capacity;
        lfuCount = 0;
    }
    private void update(int key){
        int count = freq.getOrDefault(key,0);
        freq.put(key,count+1);
        listMap.get(count).remove(key);
        listMap.computeIfAbsent(count+1,k -> new NodeList()).add(key);
        if (this.lfuCount == count && listMap.get(count).size() == 0){
            this.lfuCount += 1;
        }
    }
    public int get(int key) {
        if (!valMap.containsKey(key)) return -1;
//        int count = freq.get(key);
        update(key);
        return this.valMap.get(key);
    }

    public void put(int key, int value) {
        if (this.cap == 0) return ;

        if (!valMap.containsKey(key) && this.valMap.size() == this.cap){
            Node node = this.listMap.get(this.lfuCount).removeLast();
            valMap.remove(node.key);
            freq.remove(node.key);
        }

        valMap.put(key,value);
        update(key);
        this.lfuCount = Math.min(this.lfuCount,freq.get(key));
    }
}

/**
 * Your LFUCache object will be instantiated and called as such:
 * LFUCache obj = new LFUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */