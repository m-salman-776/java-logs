package Project101.Payment.Services;
import Project101.Payment.PaymentStrategy.PaymentStrategy;
import Project101.Payment.PaymentStrategy.PaymentStrategyFactory;
import Project101.Payment.dto.PaymentEvent;
import Project101.Payment.dto.TransactionResult;
import Project101.Payment.dto.TransactionStatus;

public class PaymentExecutorService {
    PaymentExecutorService(){
    }
    public TransactionResult executePayment(PaymentEvent paymentEvent){
        try {
            PaymentStrategy paymentStrategy = PaymentStrategyFactory.getPaymentStrategy(paymentEvent);
            return paymentStrategy.pay(Double.parseDouble(paymentEvent.getAmount()),paymentEvent.getSourceAccount(),paymentEvent.getDestinationAccount());
        } catch (Exception e) {
            System.out.println("Payment failed: " + e.getMessage());
            return new TransactionResult(null, TransactionStatus.FAILURE, e.getMessage());
        }
    }
}
