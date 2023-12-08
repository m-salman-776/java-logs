package Models.TransactionAlerts;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Alert {
    public String userId;
    public int count;
    public long wStart;
    public long sEnd ;
}
