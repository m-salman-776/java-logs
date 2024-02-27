package LLD.MySql.Classes;

import javax.xml.crypto.Data;

public abstract class Column {
    String name;
    boolean nullable;
    boolean required;
    public Column(String name, boolean required){
        this.name = name;
        this.required = required;
        this.nullable = false;
    }
    public abstract boolean validate();
}
