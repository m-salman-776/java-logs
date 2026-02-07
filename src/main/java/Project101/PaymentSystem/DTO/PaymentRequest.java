package Project101.PaymentSystem.DTO;

public class PaymentRequest {
    public int orderId; // idempotency key;
    double amount;
    public int customerId;
    public String callbackUrl;
    public PaymentRequest(int orderId,double amount,int customerId,String callbackUrl){
        this.amount = amount;
        this.orderId = orderId;
        this.callbackUrl = callbackUrl;
        this.customerId = customerId;
    }

}
