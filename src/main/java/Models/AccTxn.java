package Models;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AccTxn {
    public String accountId;
    public String txnId;
    public double amount ;
    public int timestamp;
}