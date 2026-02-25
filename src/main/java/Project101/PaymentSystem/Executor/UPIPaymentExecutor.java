package Project101.PaymentSystem.Executor;

import Project101.PaymentSystem.Gateway.PaymentGateway;
import Project101.PaymentSystem.DTO.PaymentRequest;
import Project101.PaymentSystem.DTO.PaymentResponse;
import Project101.PaymentSystem.DTO.UPIPaymentDetails;

public class UPIPaymentExecutor implements PaymentExecutor {
    @Override
    public PaymentResponse processPayment(PaymentRequest paymentRequest, PaymentGateway gateway) {
        if (!(paymentRequest.paymentMethodDetails instanceof UPIPaymentDetails)) {
            throw new IllegalArgumentException("Invalid payment details for UPI");
        }
        double amount = paymentRequest.getAmount();
        UPIPaymentDetails details = (UPIPaymentDetails) paymentRequest.paymentMethodDetails;
        System.out.println("Processing UPI Payment for VPA: " + details.vpa);
        
        // Add UPI specific logic here (e.g. VPA validation)
        return gateway.charge(amount, paymentRequest);
    }
}
