package LLD.Payment;

import Common.Account;
import LLD.Payment.dto.TransactionResult;

public interface PaymentStrategy {
    TransactionResult pay(double amount, Account source, Account destination);
}
