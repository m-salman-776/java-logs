package LLD.MySql.Classes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private void indexRow(Map<String,Column>row,int primaryKey){
        for (Map.Entry<String,Index> indexEntry : indexMap.entrySet()){
//            indexEntry.getValue().
        }
    }
}
