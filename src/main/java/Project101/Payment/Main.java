package Project101.Payment;

import Common.Account;
import Project101.Payment.Repository.InMemoryDb;
import Project101.Payment.Services.LedgerService;
import Project101.Payment.Services.PaymentService;
import Project101.Payment.dto.CreditCard;
import Project101.Payment.dto.PaymentEvent;
import Project101.Payment.dto.PaymentType;
import Project101.Payment.dto.TransactionResult;
import Project101.Payment.PaymentGatewayProviders.MockPaymentGateway;
import Project101.Payment.PaymentGatewayProviders.PaymentGateway;

import java.time.YearMonth;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        // Create accounts
        Account userAccount = new Account("user123", 1000.0);
        Account merchantAccount = new Account("merchant456", 5000.0);
        Account brokeUserAccount = new Account("user789", 50.0);


        InMemoryDb inMemoryDb = new InMemoryDb();
        LedgerService ledgerService = new LedgerService(inMemoryDb);
        PaymentService paymentService = new PaymentService(ledgerService, inMemoryDb);
        List<TransactionResult> results = paymentService.processPayment(List.of(new PaymentEvent(userAccount, merchantAccount, "100", PaymentType.CREDIT_CARD, Map.of("creditCard","1234567812345678","cvv","123","expiryDate","2025-12","cardHolderName","John Doe"))));



        // Create a payment processor
        PaymentProcessor paymentProcessor = new PaymentProcessor();

        // --- Use Credit Card for payment ---
        CreditCard card = new CreditCard("1234567812345678", "John Doe", YearMonth.of(2025, 12), "123");
        PaymentGateway gateway = new MockPaymentGateway();
//        PaymentStrategy creditCardPayment = new CreditCardPayment(card, gateway);

//        paymentProcessor.setPaymentStrategy(creditCardPayment);
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
