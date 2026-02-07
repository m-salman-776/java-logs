package Project101.PaymentSystem;
import Project101.PaymentSystem.DTO.*;
import Project101.PaymentSystem.Executor.PaymentExecutor;
import Project101.PaymentSystem.Executor.UPIPaymentExecutor;
import Project101.PaymentSystem.Gateway.PaymentGateway;
import Project101.PaymentSystem.Gateway.StripPaymentGateway;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class PaymentService {
    PaymentExecutor paymentExecutor;
    PaymentGateway paymentGateway = new StripPaymentGateway();
    Map<Integer, Payment> paymentMap;
    AtomicInteger paymentId = new AtomicInteger(0);
    public PaymentService(){
        paymentMap = new ConcurrentHashMap<>();
        paymentExecutor = new UPIPaymentExecutor(paymentGateway);
    }
    public PaymentResponse initiatePayment(double amount, PaymentRequest paymentRequest){
        Payment payment = new Payment(paymentId.incrementAndGet(),paymentRequest.orderId,amount,paymentRequest.customerId);
        paymentMap.put(payment.id,payment);
        PaymentResponse response = paymentExecutor.processPayment(amount,paymentRequest);
        payment.updatePaymentStatus(response.getPaymentStatus());
        return response;
    }
    public RefundResponse refund(String internalPaymentId, double amount){
        return null;
    }
    public PaymentResponse getPayment(int txnId){
        return null;
    }
    public void handleCallback(int txnId, PaymentStatus status){

    }
}
