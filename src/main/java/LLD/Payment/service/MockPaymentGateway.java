package LLD.Payment.service;

import LLD.Payment.dto.CreditCard;
import LLD.Payment.dto.TransactionResult;
import LLD.Payment.dto.TransactionStatus;

import java.util.UUID;

public class MockPaymentGateway implements PaymentGateway {
    @Override
    public TransactionResult charge(double amount, CreditCard creditCard) {
        // In a real system, this would make an API call to a payment provider.
        // Here, we'll just simulate a successful charge.
        System.out.println("Charging " + amount + " to credit card " + creditCard.getCardNumber());
        return new TransactionResult(UUID.randomUUID().toString(), TransactionStatus.SUCCESS, "Payment successful");
    }

    @Override
    public void refund(String transactionId, double amount) {
        // In a real system, this would make an API call to the payment provider.
        System.out.println("Refunding " + amount + " for transaction " + transactionId);
    }
}
