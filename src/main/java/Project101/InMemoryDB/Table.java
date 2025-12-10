package Project101.InMemoryDB;

import java.util.*;

public class Table {
    String name;
    Schema schema;
    Map<String,Index> indexMap ;
    Integer autoIncrement;
    Map<Integer,List<Column>> dataStore; // primary-key to list of columns
    Table(String name,List<Column> columnList){
        this.name = name; // todo duplicate name check
        this.autoIncrement = 0;
        indexMap = new HashMap<>();
        this.schema = new Schema(columnList);
        dataStore = new HashMap<>();
    }

    public void createIndex(String columnName){
        Index index = new Index();
        // todo
        this.indexMap.put(columnName,index);
    }

    public synchronized int insertRow(List<Column>columnList){
        for (Column column : columnList){
            if (!column.validate()) return 0;
        }

        autoIncrement += 1; // todo add synchronise
        this.dataStore.put(this.autoIncrement,columnList);
        // todo update index index
        updateIndex(columnList,this.autoIncrement);
        return this.autoIncrement;
    }

    private void updateIndex(List<Column>columnList,int primaryKey){
        for (Column column : columnList){
            if (indexMap.containsKey(column.name)){
                this.indexMap.get(column.name).indexData(column,primaryKey);
            }
        }
    }

    public synchronized int update(Map<String,Column>updates,int primaryKey){

        // locking

        // lock (primary)
        if (!this.dataStore.containsKey(primaryKey)) return 0;
        List<Column> data = this.dataStore.get(primaryKey);
        // [name,age,address]
        // [age:12,address:'xyz'] : updated
        for (Column column : data){
            if (updates.containsKey(column.name)){
                column.val = updates.get(column.name).val;
            }
        }
        this.dataStore.put(primaryKey,data);
        updateIndex(data,primaryKey);
        return 1;
    }
    public void delete(List<Integer> ids){
        for (int id : ids){
            List<Column> columnList = this.dataStore.get(id);
            this.dataStore.remove(id);
            updateIndex(columnList,id);
        }
    }

    List<List<Column>> getDataByCondition(Map<String,Column> condition){
        // name
        // age ,
        // address,

        // condition
        // age = 12
        // name = xyz

        List<List<Column>> ans = new ArrayList<>();
        List<Column> indexColumns = new ArrayList<>();
        for (Map.Entry<String,Index> indexEntry : this.indexMap.entrySet()){
            if (condition.containsKey(indexEntry.getKey())) {
                indexColumns.add(condition.get(indexEntry.getKey()));
            }
        }
        // indexColumns > 0
        Column firstColumn = indexColumns.get(0);
        List<Integer> primaryKey = this.indexMap.get(firstColumn).getPrimaryKeys(firstColumn);
        for (int id : primaryKey){
            List<Column> data = findById(id);
            boolean valid = true;
            for (Column column : data){
                if (condition.containsKey(column.name) && !condition.get(column.name).equals(column.val)){
                    valid = false;
                    break;
                }
            }
            if (valid){
                ans.add(data);
            }
        }
        return ans;
    }

    List<Column> findById(int id){
        return this.dataStore.get(id);
    }
}
