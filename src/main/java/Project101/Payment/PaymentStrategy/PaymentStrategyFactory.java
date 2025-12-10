package Project101.Payment.PaymentStrategy;

import Project101.Payment.PaymentGatewayProviders.MockPaymentGateway;
import Project101.Payment.dto.CreditCard;
import Project101.Payment.dto.PaymentEvent;

import java.time.YearMonth;
import java.util.Map;

public class PaymentStrategyFactory {
    public static PaymentStrategy getPaymentStrategy( PaymentEvent paymentEvent){
        switch (paymentEvent.getPaymentType()){
            case UIP: return new UpiPayment(paymentEvent.getPaymentDetails().get("upiId"));
            case CREDIT_CARD: return new CreditCardPayment(makeCreditCard(paymentEvent.getPaymentDetails()),new MockPaymentGateway());
            case CASH: return new CashPayment();
            default: throw new IllegalArgumentException("Invalid payment type");
        }
    }
    private static CreditCard makeCreditCard(Map<String,String> paymentDetails){
        String creditCardNumber = paymentDetails.get("creditCard");
        String cvv = paymentDetails.get("cvv");
        String expiryDate = paymentDetails.get("expiryDate");
        String cardHolderName = paymentDetails.get("cardHolderName");
        return new CreditCard(creditCardNumber,cvv, YearMonth.of(2025, 12),cardHolderName);
    }
}
