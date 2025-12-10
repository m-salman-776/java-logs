package Project101.Payment.PaymentStrategy;

import Common.Account;
import Project101.Payment.dto.TransactionResult;
import Project101.Payment.dto.TransactionStatus;
import lombok.Setter;

import java.util.UUID;
@Setter
public class UpiPayment implements PaymentStrategy {
    private String upiId;

    public UpiPayment(String upiId) {
        this.upiId = upiId;
    }
    @Override
    public TransactionResult pay(double amount, Account source, Account destination) {
        System.out.println("Processing UPI payment of " + amount + " from " + upiId);
        try {
            source.debit(amount); // this is for marking database only only actual transaction happens at Provider level
            destination.credit(amount);
            return new TransactionResult(UUID.randomUUID().toString(), TransactionStatus.SUCCESS, "UPI payment successful");
        } catch (Exception e) {
            return new TransactionResult(null, TransactionStatus.FAILURE, e.getMessage());
        }
    }
}
