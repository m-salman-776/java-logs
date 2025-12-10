package Project101.Payment.PaymentStrategy;

import Common.Account;
import Project101.Payment.dto.TransactionResult;

public interface PaymentStrategy {
    TransactionResult pay(double amount, Account source, Account destination);
}
