package Project101.PaymentSystem.DTO;

public class Payment {
    public int id;
    int orderId;
    double amount;
    int userId;
    PaymentStatus status;
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
}
