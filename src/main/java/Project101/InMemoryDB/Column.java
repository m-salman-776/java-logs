package Project101.InMemoryDB;

class Column {
    String name;
    boolean required;
    String val;
    public Column(String name,String val,boolean required){
        this.name  = name;
        this.required = required;
        this.val = val;
    }
    public boolean validate(){
        if (required) {
            return !this.val.isEmpty();
        }
        return true;
    }
}

//class StringDt extends Column {
//
//    String val;
//
//    StringDt(String name,String val,boolean required){
//        super(name,required);
//        this.validate();
//        this.val = val;
//    }
//    @Override
//    boolean validate() {
//        return true;
//    }
//}


