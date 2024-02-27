package LLD.MySql.Classes;

public abstract class DataType<T> {
    protected Class<T> dataType;
    protected DataType(Class<T> dataType){
        this.dataType = dataType;
    }
    public boolean validate(T val)  {
        if (!dataType.isInstance(val)){
            System.out.println("Invalid_data_point_type");
            return false;
        }
        return true;
    }
}
