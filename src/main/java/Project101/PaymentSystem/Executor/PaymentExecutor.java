package Project101.PaymentSystem.Executor;

import Project101.PaymentSystem.DTO.PaymentRequest;
import Project101.PaymentSystem.DTO.PaymentResponse;

public interface PaymentExecutor {
    PaymentResponse processPayment(double amount, PaymentRequest paymentDetail);
}
