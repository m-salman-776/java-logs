package LLD.Splitwise.code;

import java.util.List;

public class Expense {
    long id;
    double amount;
    User paidBy ;
    SplitType splitType;
    List<Split> splitList;
    public Expense(double amount, User paidBy , List<Split> userSplit, SplitType splitType) {
        this.amount = amount;
        this.paidBy = paidBy;
        this.splitList = userSplit;
        this.splitType = splitType;
    }
}
