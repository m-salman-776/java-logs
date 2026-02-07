package Project101.PaymentSystem.Gateway;

import Project101.PaymentSystem.DTO.*;
import software.amazon.awssdk.services.s3.endpoints.internal.Value;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class StripPaymentGateway implements PaymentGateway {
    Random random = new Random();
    Map<Integer, PaymentDetails> paymentDetailsMap ;
    AtomicInteger paymentId = new AtomicInteger(0);
    public StripPaymentGateway(){
        paymentDetailsMap = new ConcurrentHashMap<>();
    }
    @Override
    public PaymentResponse charge(double amount, PaymentRequest paymentRequest) {
        PaymentDetails paymentDetail = new PaymentDetails(paymentId.incrementAndGet(),amount,
                paymentRequest.orderId, paymentRequest.customerId);
        try {
            paymentDetailsMap.put(paymentDetail.id,paymentDetail);
            // 1. Simulate Network Latency (System state is PENDING)

            Thread.sleep(500);
//
//            // 2. Simulate Authorization (Bank checks funds)
//            // Let's simulate a 20% chance of failure (e.g., Insufficient Funds)
//            if (random.nextInt(10) < 2) {
//                return new PaymentResponse(PaymentStatus.FAILED, random.nextInt(10000));
//            }
//
//            // 3. Simulate Capture (Money is moved to merchant account)
//            Thread.sleep(500);
            return new PaymentResponse(paymentDetail.paymentStatus, paymentDetail.id);

        } catch (Exception e) {
            Thread.currentThread().interrupt();
            return new PaymentResponse(PaymentStatus.FAILED,paymentDetail.id);
        }

    }

    @Override
    public RefundResponse refund(double amount, String transactionId) {
        return null;
    }
    public PaymentResponse getPaymentStatus(int paymentId){
        PaymentDetails paymentDetails = paymentDetailsMap.get(paymentId);
        if (paymentDetails == null){
            return new PaymentResponse(PaymentStatus.UNAVAILABLE,paymentId);
        }
        return new PaymentResponse(paymentDetails.paymentStatus,paymentDetails.id);
    }
}
