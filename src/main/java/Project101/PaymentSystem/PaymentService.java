package Project101.PaymentSystem;

import Project101.PaymentSystem.DTO.*;
import Project101.PaymentSystem.Executor.PaymentExecutor;
import Project101.PaymentSystem.Executor.PaymentExecutorFactory;
import Project101.PaymentSystem.Gateway.PaymentGateway;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class PaymentService {
    private final PaymentGateway paymentGateway;
    private final Map<Integer, Payment> paymentMap;
    private final AtomicInteger paymentId = new AtomicInteger(0);

    public PaymentService(PaymentGateway gateway) {
        this.paymentMap = new ConcurrentHashMap<>();
        this.paymentGateway = gateway;
    }

    public PaymentResponse initiatePayment(PaymentRequest paymentRequest) {
        // Create a new internal payment record
        int id = paymentId.incrementAndGet();
        Payment payment = new Payment(id, paymentRequest.orderId, paymentRequest.amount, paymentRequest.customerId);
        paymentMap.put(id, payment);

        try {
            // Get the appropriate executor for the payment method
            PaymentExecutor executor = PaymentExecutorFactory.getExecutor(paymentRequest.getPaymentMethodType());
            
            // Execute the payment
            PaymentResponse response = executor.processPayment(paymentRequest, paymentGateway);
            
            // Update the internal payment status based on the response
            payment.updatePaymentStatus(response.getPaymentStatus());
            
            // Store the gateway's transaction ID for future reference (e.g., refunds)
            if (response.getPaymentStatus() == PaymentStatus.CAPTURED) {
                payment.setGatewayTransactionId(response.getPaymentId());
            }
            
            return response;
        } catch (Exception e) {
            System.err.println("Error processing payment: " + e.getMessage());
            payment.updatePaymentStatus(PaymentStatus.FAILED);
            return new PaymentResponse(PaymentStatus.FAILED, -1);
        }
    }

    public RefundResponse refund(int internalPaymentId, double amount) {
        Payment payment = paymentMap.get(internalPaymentId);
        
        // 1. Check if payment exists
        if (payment == null) {
            System.err.println("Payment not found for ID: " + internalPaymentId);
            return new RefundResponse(internalPaymentId, PaymentStatus.UNAVAILABLE);
        }

        // 2. Check if payment was successful (CAPTURED)
        if (payment.getStatus() != PaymentStatus.CAPTURED) {
            System.err.println("Cannot refund payment with status: " + payment.getStatus());
            return new RefundResponse(internalPaymentId, PaymentStatus.FAILED);
        }

        // 3. Check if refund amount is valid
        if (amount > payment.amount) {
            System.err.println("Refund amount " + amount + " exceeds original payment amount " + payment.amount);
            return new RefundResponse(internalPaymentId, PaymentStatus.FAILED);
        }

        // 4. Use the gateway transaction ID for the refund call
        RefundResponse response = paymentGateway.refund(amount, payment.gatewayTransactionId);
        
        if (response.getStatus() == PaymentStatus.REFUNDED) {
            payment.updatePaymentStatus(PaymentStatus.REFUNDED);
        }
        
        return response;
    }

    public PaymentResponse getPayment(int txnId) {
        Payment payment = paymentMap.get(txnId);
        if (payment == null) {
            return new PaymentResponse(PaymentStatus.UNAVAILABLE, txnId);
        }
        return new PaymentResponse(payment.getStatus(), payment.id);
    }

    public void handleCallback(int txnId, PaymentStatus status) {
        Payment payment = paymentMap.get(txnId);
        if (payment != null) {
            System.out.println("Callback received for payment " + txnId + ". Updating status to " + status);
            payment.updatePaymentStatus(status);
        } else {
            System.err.println("Callback received for unknown payment ID: " + txnId);
        }
    }
}
