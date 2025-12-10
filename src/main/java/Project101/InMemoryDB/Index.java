package Project101.InMemoryDB;

import java.util.*;

public class Index {
    Map<String, TreeSet<Integer>> index; // user [name,age]
     // name -> list primary
    Index(){
        index = new HashMap<>();
    }

    public void indexData(Column column,int primaryKey){
        this.index.computeIfAbsent(column.val,k -> new TreeSet<>()).add(primaryKey);
    }

    public List<Integer> getPrimaryKeys(Column column){
        return new ArrayList<>(index.get(column.name));
    }
}
