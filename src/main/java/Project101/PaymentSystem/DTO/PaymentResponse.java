package Project101.PaymentSystem.DTO;

import org.springframework.web.bind.annotation.ResponseStatus;

public class PaymentResponse {
    PaymentStatus paymentStatus;
    int paymentId;
    public PaymentResponse(PaymentStatus paymentStatus,int txnId){
        this.paymentStatus = paymentStatus;
        this.paymentId = txnId;
    }
    public int getPaymentId(){
        return this.paymentId;
    }
    public PaymentStatus getPaymentStatus(){
        return this.paymentStatus;
    }
}
