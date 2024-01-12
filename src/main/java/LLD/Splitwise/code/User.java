package LLD.Splitwise.code;

public class User {
    long userId;
    String userName;
    BalanceSheet balanceSheet;
    public User(String userName){
        this.userName = userName;
        balanceSheet = new BalanceSheet();
    }
    public User(long userId ,String userName){
        this.userName = userName;
        this.userId = userId;
        balanceSheet = new BalanceSheet();
    }
    public long getUserId(){
        return this.userId;
    }
    public String getUserName(){
        return this.userName;
    }
    public BalanceSheet getBalanceSheet(){
        return this.balanceSheet;
    }

}
