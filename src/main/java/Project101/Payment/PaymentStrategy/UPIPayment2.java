package Project101.Payment.PaymentStrategy;

import Project101.Payment.dto.TransactionResult;

public class UPIPayment2 implements PaymentStrategy2<UPIPaymentDetails>{

    @Override
    public TransactionResult pay(double amount, UPIPaymentDetails PaymentDetails) {
        return null;
    }
}
