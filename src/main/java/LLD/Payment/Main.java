package LLD.Payment;

import Common.Account;
import LLD.Payment.dto.CreditCard;
import LLD.Payment.dto.TransactionResult;
import LLD.Payment.service.MockPaymentGateway;
import LLD.Payment.service.PaymentGateway;

import java.time.YearMonth;

public class Main {
    public static void main(String[] args) {
        // Create accounts
        Account userAccount = new Account("user123", 1000.0);
        Account merchantAccount = new Account("merchant456", 5000.0);
        Account brokeUserAccount = new Account("user789", 50.0);

        // Create a payment processor
        PaymentProcessor paymentProcessor = new PaymentProcessor();

        // --- Use Credit Card for payment ---
        CreditCard card = new CreditCard("1234567812345678", "John Doe", YearMonth.of(2025, 12), "123");
        PaymentGateway gateway = new MockPaymentGateway();
        PaymentStrategy creditCardPayment = new CreditCardPayment(card, gateway);

        paymentProcessor.setPaymentStrategy(creditCardPayment);
        System.out.println("--- Processing payment of 100 via Credit Card (Success) ---");
        TransactionResult result = paymentProcessor.processPayment(100.0, userAccount, merchantAccount);
        System.out.println(result);

        System.out.println("\n");

        // --- Use Credit Card for payment (Failure) ---
        System.out.println("--- Processing payment of 100 via Credit Card (Failure) ---");
        result = paymentProcessor.processPayment(100.0, brokeUserAccount, merchantAccount);
        System.out.println(result);

    }
}
