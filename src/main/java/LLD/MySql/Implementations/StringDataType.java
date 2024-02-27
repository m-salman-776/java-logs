package LLD.MySql.Implementations;

import LLD.MySql.Classes.Column;
import LLD.MySql.Classes.DataType;

public class StringDataType extends Column {

    private final int maxLength;
    String val;
    public StringDataType(String name , boolean required, int maxLength) {
        super(name,required);
        this.maxLength = maxLength;
    }

    @Override
    public boolean validate() {
        if (val.length() > maxLength) {
            System.out.println("string_len_out_of_range: Length should be at most " + maxLength);
            return false;
        }
        return true;
    }
}
