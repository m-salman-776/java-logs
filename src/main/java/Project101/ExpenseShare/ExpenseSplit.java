package Project101.ExpenseShare;

public class ExpenseSplit {
    int expenseId;
    int userId;
    double netAmount;
    // UI only
    ExpenseType expenseType;
//    double inputValue;

    public ExpenseSplit(int expenseId,int userId,double netAmount,ExpenseType expenseType){
        this.expenseId = expenseId;
        this.userId = userId;
        this.netAmount = netAmount;
        this.expenseType = expenseType;
    }
}
