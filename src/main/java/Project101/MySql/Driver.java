package Project101.MySql;

import Project101.MySql.Classes.Schema;
import Project101.MySql.Classes.Table;
import Project101.MySql.Implementations.IntDataType;
import Project101.MySql.Implementations.StringDataType;

import java.util.Arrays;

public class Driver {
    public static void main(String[] args) {
        IntDataType id = new IntDataType("id",true,1,1000);
        StringDataType name = new StringDataType("name",true,10);
        Schema schema = new Schema(Arrays.asList(id,name));
        Table student = new Table("student","id",schema);
    }
}
