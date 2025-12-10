package Assingment;

import lombok.Getter;

import java.util.List;
@Getter
public class Expense {
    int id;
    double amount;
    User payer;
    List<Split> splitList;
    public Expense(int id, User payer,double amount,List<Split> splitList){
        this.id = id;
        this.payer = payer;
        this.amount = amount;
        this.splitList = splitList;
    }
}
