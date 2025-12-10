package Project101.Payment;

import Common.Account;
import Project101.Payment.PaymentStrategy.PaymentStrategy;
import Project101.Payment.dto.TransactionResult;
import lombok.Setter;

@Setter
public class PaymentProcessor {
    private PaymentStrategy paymentStrategy;

    public TransactionResult processPayment(double amount, Account source, Account destination) {
        if (paymentStrategy == null) {
            throw new IllegalStateException("Payment strategy not set");
        }
        return paymentStrategy.pay(amount, source, destination);
    }
}
