package Project101.PaymentSystem.Executor;

import Project101.PaymentSystem.DTO.PaymentRequest;
import Project101.PaymentSystem.DTO.PaymentResponse;
import Project101.PaymentSystem.Gateway.PaymentGateway;

public interface PaymentExecutor {
    PaymentResponse processPayment(PaymentRequest paymentDetail, PaymentGateway gateway);
}
