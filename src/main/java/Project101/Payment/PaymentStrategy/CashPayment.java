package Project101.Payment.PaymentStrategy;

import Common.Account;
import Project101.Payment.dto.TransactionResult;
import Project101.Payment.dto.TransactionStatus;

import java.util.UUID;

public class CashPayment implements PaymentStrategy {
    @Override
    public TransactionResult pay(double amount, Account source, Account destination) {
        System.out.println("Processing cash payment of " + amount);
        try {
            source.debit(amount);
            destination.credit(amount);
            return new TransactionResult(UUID.randomUUID().toString(), TransactionStatus.SUCCESS, "Cash payment successful");
        } catch (Exception e) {
            return new TransactionResult(null, TransactionStatus.FAILURE, e.getMessage());
        }
    }
}
