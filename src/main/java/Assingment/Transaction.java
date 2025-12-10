package Assingment;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
@Getter

public class Transaction {
    User payer;
    List<Split> borrowers;
    public Transaction(User payer){
        this.payer = payer;
        this.borrowers = new ArrayList<>();
    }
    public void addSplit(Split split){
        this.borrowers.add(split);
    }
}
