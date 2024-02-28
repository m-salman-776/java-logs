package LLD.InMemoryDB;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        Database db = new Database("user_db");
        Column name = new Column("name","abc",true);
        Column address = new Column("address","xyz",true);
        List<Column> columnList = Arrays.asList(name,address);
        db.createTable("student", columnList);
        db.insertRow("student",columnList);
        // updates
        Column updatedName = new Column("name","new Name",true);
        Column updateAddress = new Column("address","new Address",true);
        Map<String,Column> updates = new HashMap<>(Map.of("name",updatedName,"address",updateAddress));
        db.update("student",updates,1);

        // delete
        db.deleteByPrimaryKey("student",Arrays.asList(1));
        // create index
        db.createIndex("student","name");


        System.out.println("DONE");
    }
}
