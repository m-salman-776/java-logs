package Project101.PaymentSystem.DTO;

public class CreditCardPaymentDetails implements PaymentMethodDetails {
    public final String cardNumber;
    public final String cvv;
    public final String expiry;

    public CreditCardPaymentDetails(String cardNumber, String cvv, String expiry) {
        this.cardNumber = cardNumber;
        this.cvv = cvv;
        this.expiry = expiry;
    }

    @Override
    public PaymentMethodType getType() {
        return PaymentMethodType.CREDIT_CARD;
    }
}
