package Project101.PaymentSystem.DTO;

import lombok.Getter;

public class PaymentRequest {
    public int orderId; // idempotency key;
    @Getter
    public double amount;
    public int customerId;
    public String callbackUrl;
    public PaymentMethodDetails paymentMethodDetails;

    public PaymentRequest(int orderId, double amount, int customerId, String callbackUrl, PaymentMethodDetails paymentMethodDetails){
        this.amount = amount;
        this.orderId = orderId;
        this.callbackUrl = callbackUrl;
        this.customerId = customerId;
        this.paymentMethodDetails = paymentMethodDetails;
    }
    
    public PaymentMethodType getPaymentMethodType() {
        return paymentMethodDetails.getType();
    }
}
