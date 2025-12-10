package Project101.MySql.Classes;

import java.util.*;

public class Table {
    String name;
    Schema schema;
    String primaryKeyColumn;
    Integer autoIncrement;
    Map<String,Index> indexMap;
    Map<Integer,Map<String,Column>> tableData;
    public Table(String name, String primaryKey, Schema schema){
        this.indexMap = new HashMap<>();
        this.schema = schema;
        this.primaryKeyColumn = primaryKey;
        this.name = name;
    }

    public boolean insert(Map<String,Column> rowData){
        if (!this.schema.validate(rowData)) return false;
        this.autoIncrement += 1;
        this.tableData.put(this.autoIncrement,rowData);
        return true;
    }

    public List<Map<String,Column>> getDataByPrimaryKey(int []primary){
        List<Map<String,Column>> ls = new ArrayList<>();
        for (int a : primary){
            if (this.tableData.containsKey(a)){
                ls.add(this.tableData.get(a));
            }
        }
        return ls;
    }
    private void indexRow(Map<String,Column>row,int primaryKey){
        for (Map.Entry<String,Index> indexEntry : indexMap.entrySet()){
            Index index = indexEntry.getValue();
            for (Map.Entry<String,Column> data : row.entrySet()){
                Column column = data.getValue();
                if (!index.index.containsKey(column.name)) continue;
                index.index.computeIfAbsent(column.name,k->new TreeSet<>()).add(primaryKey);
            }
        }
    }
}
