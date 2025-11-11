package LLD.Payment;

import Common.Account;
import LLD.Payment.dto.TransactionResult;
import LLD.Payment.dto.TransactionStatus;

import java.util.UUID;

public class UpiPayment implements PaymentStrategy {
    private String upiId;

    public UpiPayment(String upiId) {
        this.upiId = upiId;
    }

    @Override
    public TransactionResult pay(double amount, Account source, Account destination) {
        System.out.println("Processing UPI payment of " + amount + " from " + upiId);
        try {
            source.debit(amount);
            destination.credit(amount);
            return new TransactionResult(UUID.randomUUID().toString(), TransactionStatus.SUCCESS, "UPI payment successful");
        } catch (Exception e) {
            return new TransactionResult(null, TransactionStatus.FAILURE, e.getMessage());
        }
    }
}
