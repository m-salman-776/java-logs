package Project101.Payment.PaymentGatewayProviders;

import Project101.Payment.dto.PaymentType;
import Project101.Payment.dto.TransactionResult;
import Project101.Payment.dto.TransactionStatus;

import java.util.UUID;

public class MockPaymentGateway implements PaymentGateway {
    @Override
    public TransactionResult charge(double amount, PaymentType paymentType) {
        // In a real system, this would make an API call to a payment provider.
        // Here, we'll just simulate a successful charge.
        System.out.println("Charging " + amount + " to " + paymentType.toString());
        return new TransactionResult(UUID.randomUUID().toString(), TransactionStatus.SUCCESS, "Payment successful");
    }

    @Override
    public void refund(String transactionId, double amount) {
        // In a real system, this would make an API call to the payment provider.
        System.out.println("Refunding " + amount + " for transaction " + transactionId);
    }
}
