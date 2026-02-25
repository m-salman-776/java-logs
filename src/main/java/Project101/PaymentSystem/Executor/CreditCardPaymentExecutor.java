package Project101.PaymentSystem.Executor;

import Project101.PaymentSystem.Gateway.PaymentGateway;
import Project101.PaymentSystem.DTO.CreditCardPaymentDetails;
import Project101.PaymentSystem.DTO.PaymentRequest;
import Project101.PaymentSystem.DTO.PaymentResponse;

public class CreditCardPaymentExecutor implements PaymentExecutor {
    @Override
    public PaymentResponse processPayment(PaymentRequest paymentRequest, PaymentGateway gateway) {
        if (!(paymentRequest.paymentMethodDetails instanceof CreditCardPaymentDetails)) {
            throw new IllegalArgumentException("Invalid payment details for Credit Card");
        }
        double amount = paymentRequest.getAmount();
        CreditCardPaymentDetails details = (CreditCardPaymentDetails) paymentRequest.paymentMethodDetails;
        System.out.println("Processing Credit Card Payment for card number: " + details.cardNumber);

        // Add Credit Card specific logic here (e.g. Luhn check, CVV validation)
        return gateway.charge(amount, paymentRequest);
    }
}
