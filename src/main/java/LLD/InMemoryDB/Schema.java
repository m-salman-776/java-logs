package LLD.InMemoryDB;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Schema {
    Map<String,Column> columnMap;
    public Schema(List<Column> columnList){
        columnMap = new HashMap<>();
        for (Column c : columnList){
            if (columnMap.containsKey(c.name)) {
                // todo : duplicate name; handle properly
                continue;
            }
            columnMap.put(c.name,c);
        }
    }
    //todo  methods for add / remove columns
}
