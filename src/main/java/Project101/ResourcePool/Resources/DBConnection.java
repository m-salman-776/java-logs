package Project101.ResourcePool.Resources;

public class DBConnection {
    String address;
    boolean isValid;
    public DBConnection(String address){
        this.address = address;
        this.isValid = true;
    }
    public boolean ping(){
        return true;
    }
    public void close(){
        return;
    }
    public void setIsValid(boolean isValid){
        this.isValid = isValid;
    }
    public boolean getIsValid(){
        return this.isValid;
    }
}
