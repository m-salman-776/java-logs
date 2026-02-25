package Project101.PaymentSystem;

import Project101.PaymentSystem.DTO.*;
import Project101.PaymentSystem.Gateway.RazorPayPaymentGateway;
import Project101.PaymentSystem.Gateway.StripPaymentGateway;

public class Main {
    public static void main(String[] args) {
        // 1. Use Stripe Gateway
        System.out.println("--- Using Stripe Gateway ---");
        PaymentService stripeService = new PaymentService(new StripPaymentGateway());
        
        // UPI Payment
        PaymentRequest upiRequest = new PaymentRequest(101, 500.0, 1, "http://callback", new UPIPaymentDetails("user@upi"));
        PaymentResponse stripeUpiResponse = stripeService.initiatePayment(upiRequest);
        System.out.println("Stripe UPI Response: " + stripeUpiResponse.getPaymentStatus());

        // Credit Card Payment
        PaymentRequest ccRequest = new PaymentRequest(102, 1000.0, 1, "http://callback", new CreditCardPaymentDetails("1234-5678-9012-3456", "123", "12/25"));
        PaymentResponse stripeCcResponse = stripeService.initiatePayment(ccRequest);
        System.out.println("Stripe CC Response: " + stripeCcResponse.getPaymentStatus());

        // 2. Use RazorPay Gateway
        System.out.println("\n--- Using RazorPay Gateway ---");
        PaymentService razorPayService = new PaymentService(new RazorPayPaymentGateway());
        
        PaymentRequest razorUpiRequest = new PaymentRequest(201, 750.0, 2, "http://callback", new UPIPaymentDetails("anotheruser@upi"));
        PaymentResponse razorUpiResponse = razorPayService.initiatePayment(razorUpiRequest);
        System.out.println("RazorPay UPI Response: " + razorUpiResponse.getPaymentStatus());
    }
}
