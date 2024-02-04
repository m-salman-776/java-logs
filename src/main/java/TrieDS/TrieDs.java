package TrieDS;

import java.util.*;

public class TrieDs {
    Node root;
    TrieDs(){
        root = new Node();
    }
    void insert(String id){
        Node temp = root;
        for (char ch : id.toCharArray()){
            if (!temp.child.containsKey(ch)){
                temp.child.put(ch,new Node());
            }
            temp = temp.child.get(ch);
        }
        temp.complete = true;
        temp.id = id;
    }
    public List<String> getUniqueId(){
        Node temp = root;
        Queue<Node> q = new LinkedList<>();
        q.add(temp);
        TreeSet<String> uniqueId = new TreeSet<>();
        while (!q.isEmpty()){
            Node node = q.poll();
            if (!node.id.isEmpty())
                uniqueId.add(node.id);
            Set<Character> keySet = node.child.keySet();
            for (Character key : keySet){
                Node child = node.child.get(key);
                if (child != null) q.add(child);
            }
        }
        return new ArrayList<>(uniqueId);
    }
    void dfs(Node node){
        if (node == null) return;
        if (node.complete){
            //
        }
        for (Map.Entry<Character,Node> child : node.child.entrySet()){
            dfs(child.getValue());
        }
    }
}

class Node {
    String id;
    boolean complete;
    TreeMap<Character,Node> child;
    Node(){
        child = new TreeMap<>();
        complete = false;
        id = "";
    }
}

