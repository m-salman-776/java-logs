package LLD.Payment;

import Common.Account;
import LLD.Payment.dto.TransactionResult;
import LLD.Payment.dto.TransactionStatus;

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
