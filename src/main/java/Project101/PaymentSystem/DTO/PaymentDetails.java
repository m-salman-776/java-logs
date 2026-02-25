package Project101.PaymentSystem.DTO;

import lombok.Setter;
// to be used at Gateway end
public class PaymentDetails {
    public int id;
    double amount;
    int orderId;
    int clientId;
    @Setter
    public PaymentStatus paymentStatus;
    public PaymentDetails(int id,double amount,int orderId,int clientId){
        this.id = id;
        this.amount = amount;
        this.orderId = orderId;
        this.clientId = clientId;
        this.paymentStatus = PaymentStatus.CAPTURED;
    }

}
