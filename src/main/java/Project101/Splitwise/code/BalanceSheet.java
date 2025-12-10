package Project101.Splitwise.code;

import java.util.HashMap;

public class BalanceSheet {
    double totalGetBackAmount ;
    double totalOweAmount;
    double totalExpenditure ;
    HashMap<String,Balance> expenseLines ;
    public BalanceSheet(){
        totalExpenditure = 0;
        totalOweAmount = 0;
        totalGetBackAmount = 0;
        expenseLines = new HashMap<>();
    }
    public double getTotalGetBackAmount(){
        return totalGetBackAmount;
    }
    public double getTotalOweAmount (){
        return totalOweAmount;
    }
    public double getTotalExpenditure(){
        return totalExpenditure;
    }
    public HashMap<String,Balance> getExpenseLines(){
        return expenseLines;
    }
    public void addTotalGetBackAmount(double amount){
        this.totalGetBackAmount += amount;
    }
    public void addTotalExpenditure(double amount){
        this.totalExpenditure += amount;
    }
}
