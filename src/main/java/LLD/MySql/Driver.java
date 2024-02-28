package LLD.MySql;

import LLD.MySql.Classes.Column;
import LLD.MySql.Classes.Schema;
import LLD.MySql.Classes.Table;
import LLD.MySql.Implementations.IntDataType;
import LLD.MySql.Implementations.StringDataType;
import software.amazon.awssdk.services.s3.endpoints.internal.Value;

import java.util.Arrays;

public class Driver {
    public static void main(String[] args) {
        IntDataType id = new IntDataType("id",true,1,1000);
        StringDataType name = new StringDataType("name",true,10);
        Schema schema = new Schema(Arrays.asList(id,name));
        Table student = new Table("student","id",schema);
    }
}
