package Project101.Splitwise.V2;

import lombok.Getter;

@Getter
public class SettlementTransaction {
    String payerId;
    String payeeId;
    double amount;

    public SettlementTransaction(String payerName, String payeeName, double amount) {
        this.payerId = payerName;
        this.payeeId = payeeName;
        this.amount = amount;
    }
}
