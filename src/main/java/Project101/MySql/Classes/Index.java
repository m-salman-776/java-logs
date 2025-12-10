package Project101.MySql.Classes;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

public class Index {
    Map<String,TreeSet<Integer>> index;// column-[pri]
    Index(){
        index = new HashMap<>();
    }
    public void addPrimaryKey(String columnName,int primaryKey){
        this.index.computeIfAbsent(columnName,k-> new TreeSet<>()).add(primaryKey);
    }
}
