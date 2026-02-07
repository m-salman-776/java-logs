package Project101.PaymentSystem.Executor;

import Project101.PaymentSystem.Gateway.PaymentGateway;
import Project101.PaymentSystem.DTO.PaymentRequest;
import Project101.PaymentSystem.DTO.PaymentResponse;

public class UPIPaymentExecutor implements PaymentExecutor {
    PaymentGateway paymentGateway;
    public UPIPaymentExecutor(PaymentGateway paymentGateway){
        this.paymentGateway = paymentGateway;
    }
    @Override
    public PaymentResponse processPayment(double amount, PaymentRequest paymentDetail) {

        return paymentGateway.charge(amount,paymentDetail);
    }
    public void setPaymentGateway(PaymentGateway paymentGateway){
        this.paymentGateway = paymentGateway;
    }
}
