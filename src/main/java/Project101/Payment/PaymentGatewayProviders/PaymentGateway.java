package Project101.Payment.PaymentGatewayProviders;

import Project101.Payment.dto.PaymentType;
import Project101.Payment.dto.TransactionResult;

public interface PaymentGateway {
    TransactionResult charge(double amount, PaymentType paymentType);
    void refund(String transactionId, double amount);
}
