package Project101.ExpenseShare;

public class ExpenseTransaction {
    int from;
    int to;
    double amount;
    ExpenseTransaction(int borrowerId,int lenderId,double amount){
        this.from = borrowerId;
        this.to = lenderId;
        this.amount = amount;
    }
    @Override
    public String toString(){
        return System.out.printf("User %d pay %.2f to %d",from,amount,to).toString();
    }
}
