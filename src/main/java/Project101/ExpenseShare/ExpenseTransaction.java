package Project101.ExpenseShare;

public class ExpenseTransaction {
    int borrowerId;
    int lenderId;
    double amount;
    ExpenseTransaction(int borrowerId,int lenderId,double amount){
        this.borrowerId = borrowerId;
        this.lenderId = lenderId;
        this.amount = amount;
    }
}
