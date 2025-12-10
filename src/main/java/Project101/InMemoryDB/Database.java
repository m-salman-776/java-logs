package Project101.InMemoryDB;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Database {
    String name;
    Map<String,Table> tableMap;
    public Database(String name){
        this.name= name;
        tableMap = new HashMap<>();
    }
    public void createTable(String tableName, List<Column> columnList){
        Table table = new Table(tableName,columnList);
        tableMap.put(tableName,table);
    }

    public int insertRow(String tableName , List<Column>columnList){
        if (this.tableMap.containsKey(tableName)){
            return this.tableMap.get(tableName).insertRow(columnList);
        }
        return 0;
    }

    public void update(String tableName,Map<String,Column>columnMap,int primaryKey){
        if (this.tableMap.containsKey(tableName)){
            this.tableMap.get(tableName).update(columnMap,primaryKey);
        }
    }

    public int deleteRow(String tableNam,Map<String,Column>condition){
        if (this.tableMap.containsKey(tableNam)){
            // figure out ids
            this.tableMap.get(tableNam).delete(Arrays.asList(1234));
        }
        return 0;
    }

    public int deleteByPrimaryKey(String tableName,List<Integer>primaryKeys){
        if (this.tableMap.containsKey(tableName)){
            for (int id : primaryKeys)
                this.tableMap.get(tableName).delete(List.of(id));
        }
        return 0;
    }

    public void createIndex(String tableName,String column){
        if (this.tableMap.containsKey(tableName)){
            this.tableMap.get(tableName).createIndex(column);
        }
    }
}
