package LLD.Payment;

import Common.Account;
import LLD.Payment.dto.CreditCard;
import LLD.Payment.dto.TransactionResult;
import LLD.Payment.dto.TransactionStatus;
import LLD.Payment.service.PaymentGateway;

public class CreditCardPayment implements PaymentStrategy {
    private final CreditCard creditCard;
    private final PaymentGateway paymentGateway;

    public CreditCardPayment(CreditCard creditCard, PaymentGateway paymentGateway) {
        this.creditCard = creditCard;
        this.paymentGateway = paymentGateway;
    }

    @Override
    public TransactionResult pay(double amount, Account source, Account destination) {
        try {
            // 1. Validate the credit card details
            creditCard.validate();
        } catch (IllegalArgumentException e) {
            System.out.println("Payment failed: " + e.getMessage());
            return new TransactionResult(null, TransactionStatus.FAILURE, e.getMessage());
        }

        // 2. Charge the card via the payment gateway
        TransactionResult result = paymentGateway.charge(amount, creditCard);

        // 3. If payment is successful, update internal accounts
        if (result.getStatus() == TransactionStatus.SUCCESS) {
            try {
                source.debit(amount);
                destination.credit(amount);
            } catch (Exception e) {
                // If internal update fails, issue a refund
                System.out.println("Internal account update failed. Refunding the payment.");
                paymentGateway.refund(result.getTransactionId(), amount);
                return new TransactionResult(result.getTransactionId(), TransactionStatus.FAILURE, "Internal system error. Payment refunded.");
            }
        }

        return result;
    }
}
