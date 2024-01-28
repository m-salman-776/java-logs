package LLD.ImMemoryFileSystem;

import java.lang.reflect.Array;
import java.util.*;

public class TrieNode {
    String content;
    Map<String,TrieNode> children;
    TrieNode(){
        children = new HashMap<>();
    }
}
class Trie {
    TrieNode root;
    void mkdir(String path){
        TrieNode temp = root;
        String []paths = path.split("/");
        for (int i=1;i<paths.length;i++){
            String currString = paths[i];
            if (!temp.children.containsKey(currString)){
                temp.children.put(currString,new TrieNode());
            }
            temp = temp.children.get(currString);
        }
    }
    public List<String> ls (String path){
        TrieNode temp = root;
        if (!path.equals("/")){
            String paths [] = path.split("/");
            for (int i=1;i<paths.length;i++){
                String currString = paths[i];
                temp =temp.children.get(currString);
                if (i == paths.length-1 && temp.content != null){
                    return List.of(currString);
                }
            }
        }
        List<String> list = new ArrayList<>(temp.children.keySet());
        Collections.sort(list);
        return list;
    }
    void  addContent(String path, String content){
        TrieNode temp = root;
        String paths [] = path.split("/");
        for (int i=1;i<paths.length;i++){
            if (!temp.children.containsKey(paths[i])){
                temp.children.put(paths[i],new TrieNode());
            }
            temp = temp.children.get(paths[i]);
        }
        if (temp.content == null){
            temp.content  = content;
        }else {
            temp.content += content;
        }
    }
    String readContent(String path){
        String paths[] = path.split("/");
        TrieNode temp = root;
        for (int i=1;i<paths.length;i++){
            if (!temp.children.containsKey(paths[i])) {
                temp.children.put(paths[i],new TrieNode());
            }
            temp = temp.children.get(paths[i]);
        }
        return temp.content;
    }
}


