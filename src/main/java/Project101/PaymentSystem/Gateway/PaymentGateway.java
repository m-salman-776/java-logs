package Project101.PaymentSystem.Gateway;

import Project101.PaymentSystem.DTO.PaymentRequest;
import Project101.PaymentSystem.DTO.PaymentResponse;
import Project101.PaymentSystem.DTO.RefundResponse;

public interface PaymentGateway {
    PaymentResponse charge(double amount, PaymentRequest paymentDetail);
    RefundResponse refund(double amount, String transactionId);
}
