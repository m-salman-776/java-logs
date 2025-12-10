package Project101.Payment.PaymentStrategy;

import Project101.Payment.dto.TransactionResult;

public interface PaymentStrategy2 <T extends PaymentDetails> {
    TransactionResult pay(double amount,T PaymentDetails);
}
