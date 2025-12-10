package Project101.MySql.Classes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Schema {
    Map<String,Column> columnMap;
    public Schema(List<Column> columnList){
        columnMap = new HashMap<>();
        addColumn(columnList);
    }
    public boolean addColumn (List<Column> columnList) {
        for (Column column:columnList){
            if (columnMap.containsKey(column.name)) {
                System.out.println("Column Name already exists");
                return false;
            }
            this.columnMap.put(column.name,column);
        }
        return true;
    }
    public boolean validate(Map<String,Column>columnMap){
        for (Map.Entry<String,Column> k : columnMap.entrySet()){
            if (!k.getValue().validate()) return false;
        }
        return true;
    }
}
