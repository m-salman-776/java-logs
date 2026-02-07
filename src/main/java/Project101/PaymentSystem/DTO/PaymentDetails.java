package Project101.PaymentSystem.DTO;

public class PaymentDetails {
    public int id;
    double amount;
    int orderId;
    int clientId;
    public PaymentStatus paymentStatus;
    public PaymentDetails(int id,double amount,int orderId,int clientId){
        this.id = id;
        this.amount = amount;
        this.orderId = orderId;
        this.clientId = clientId;
        this.paymentStatus = PaymentStatus.CAPTURED;
    }
    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

}
