package Models.TransactionAlerts;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
@NoArgsConstructor
public class Transaction {
    public String userId;
    public Status status;
    public int timestamp;
}


