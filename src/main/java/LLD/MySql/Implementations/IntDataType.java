package LLD.MySql.Implementations;

import LLD.MySql.Classes.Column;
import LLD.MySql.Classes.DataType;

public class IntDataType extends Column {
    private final int minValue;
    private final int maxValue;
    int val;
    public IntDataType(String name , boolean required,int minVal,int maxVal) {
        super(name,required);
        this.minValue = minVal;
        this.maxValue = maxVal;
    }
    public void setVal(int val){
        this.val = val;
    }

    @Override
    public boolean validate() {
        if (this.val < minValue || this.val > maxValue) {
            System.out.println("int_out_of_range: Value should be between " + minValue + " and " + maxValue);
            return false;
        }
        return true;
    }
}
