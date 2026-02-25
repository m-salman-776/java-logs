package Project101.PaymentSystem.Gateway;

import Project101.PaymentSystem.DTO.*;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class RazorPayPaymentGateway implements PaymentGateway {
    Map<Integer, PaymentDetails> paymentDetailsMap;
    AtomicInteger paymentId = new AtomicInteger(0);

    public RazorPayPaymentGateway() {
        paymentDetailsMap = new ConcurrentHashMap<>();
    }

    @Override
    public PaymentResponse charge(double amount, PaymentRequest paymentRequest) {
        System.out.println("Charging via RazorPay...");
        PaymentDetails paymentDetail = new PaymentDetails(paymentId.incrementAndGet(), amount,
                paymentRequest.orderId, paymentRequest.customerId);
        
        paymentDetailsMap.put(paymentDetail.id, paymentDetail);
        
        // Simulate RazorPay specific logic

        return new PaymentResponse(PaymentStatus.CAPTURED, paymentDetail.id);
    }

    @Override
    public RefundResponse refund(double amount, int transactionId) {
        PaymentDetails paymentDetails = paymentDetailsMap.get(transactionId);
        if (paymentDetails == null){
            System.out.println("Refund failed no details found...");
            return new RefundResponse(transactionId,PaymentStatus.UNAVAILABLE);
        }
        System.out.println("Refunding via RazorPay...");
        return new RefundResponse(transactionId, PaymentStatus.REFUNDED);
    }
}
