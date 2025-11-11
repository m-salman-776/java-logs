package LLD.Payment;

import Common.Account;
import LLD.Payment.dto.TransactionResult;
import LLD.Payment.dto.TransactionStatus;

import java.util.UUID;

public class BarcodePayment implements PaymentStrategy {
    private final String barcode;

    public BarcodePayment(String barcode) {
        this.barcode = barcode;
    }

    @Override
    public TransactionResult pay(double amount, Account source, Account destination) {
        System.out.println("Processing barcode payment of " + amount + " from barcode " + barcode);
        try {
            source.debit(amount);
            destination.credit(amount);
            return new TransactionResult(UUID.randomUUID().toString(), TransactionStatus.SUCCESS, "Barcode payment successful");
        } catch (Exception e) {
            return new TransactionResult(null, TransactionStatus.FAILURE, e.getMessage());
        }
    }
}
