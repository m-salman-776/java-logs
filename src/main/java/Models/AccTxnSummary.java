package Models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccTxnSummary {
    public double totalAmount;
    public double totalCount;
    public AccTxnSummary(){
        this.totalAmount = 0.0;
        this.totalCount = 0.0;
    }
    public void add(AccTxn txn){
        this.totalAmount += txn.amount;
        this.totalCount += 1;
    }
}
