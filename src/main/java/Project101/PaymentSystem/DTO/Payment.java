package Project101.PaymentSystem.DTO;

import lombok.Getter;

public class Payment {
    public int id;
    int orderId;
    public double amount;
    int userId;
    @Getter
    public PaymentStatus status;
    public int gatewayTransactionId; // Store the ID returned by the gateway

    public Payment(int id,int orderId,double amount,int userId){
        this.id = id;
        this.orderId = orderId;
        this.amount = amount;
        this.userId = userId;
        this.status = PaymentStatus.CREATED;
    }
    public void updatePaymentStatus(PaymentStatus status){
        this.status = status;
    }
    public void setGatewayTransactionId(int gatewayTransactionId) {
        this.gatewayTransactionId = gatewayTransactionId;
    }
}
