package Project101.Payment.PaymentStrategy;

import Common.Account;
import Project101.Payment.dto.CreditCard;
import Project101.Payment.dto.PaymentType;
import Project101.Payment.dto.TransactionResult;
import Project101.Payment.dto.TransactionStatus;
import Project101.Payment.PaymentGatewayProviders.PaymentGateway;
import lombok.Setter;

@Setter
public class CreditCardPayment implements PaymentStrategy {
    CreditCard creditCard;
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
        TransactionResult result = paymentGateway.charge(amount, PaymentType.CREDIT_CARD);

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
