package Project101.PaymentSystem.DTO;

import lombok.Getter;

@Getter
public class PaymentResponse {
    PaymentStatus paymentStatus;
    int paymentId;
    public PaymentResponse(PaymentStatus paymentStatus,int txnId){
        this.paymentStatus = paymentStatus;
        this.paymentId = txnId;
    }
}
