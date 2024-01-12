package LLD.Splitwise.code;

public class Balance {
    long id;
    double getBackAmount;
    double oweAmount;
    public Balance(){

    }
    public void  setGetBackAmount(double getBack){
        this.getBackAmount = getBack;
    }
    public void setOweAmount(double owe){
        this.oweAmount = owe;
    }
    public double getBackAmount(){
        return this.getBackAmount;
    }
    public double getOweAmount (){
        return this.getBackAmount;
    }
}
