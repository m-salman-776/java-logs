package Project101.PaymentSystem.Executor;

import Project101.PaymentSystem.DTO.PaymentMethod;

public class PaymentExecutorFactory {
    public static PaymentExecutor getExecutor(PaymentMethod type) {
        switch (type) {
            case UPI:
                return new UPIPaymentExecutor();
            case CREDIT_CARD:
                return new CreditCardPaymentExecutor();
            default:
                throw new IllegalArgumentException("Unknown payment method: " + type);
        }
    }
}
