package LLD.Splitwise.code;

public class Split {
    User user;
    double amount ;
    public Split(User user,double amount){
        this.amount = amount;
        this.user = user;
    }
    public double getAmount(){
        return this.amount;
    }
    public User getUser(){
        return this.user;
    }
}
