package Project101.ExpenseShare;

import java.util.Map;

public class Expense {
    int id;
    int payerId;
    double totalAmount;
    String description;
    SplitType splitType ;
    Map<Integer,Double> splits;
    public Expense(int id, int payerId,double totalAmount,String description,SplitType type,Map<Integer,Double>splits){
        this.id = id;
        this.payerId = payerId;
        this.totalAmount = totalAmount;
        this.description = description;
        this.splitType = type;
        this.splits = splits;
    }
    public double getTotalAmount(){
        return this.totalAmount;
    }
    public Map<Integer,Double> getSplits(){
        return this.splits;
    }
    public int getId(){
        return this.id;
    }
    public int getPayerId(){
        return this.payerId;
    }
}
