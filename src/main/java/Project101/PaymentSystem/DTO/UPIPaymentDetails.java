package Project101.PaymentSystem.DTO;

public class UPIPaymentDetails implements PaymentMethodDetails {
    public final String vpa;

    public UPIPaymentDetails(String vpa) {
        this.vpa = vpa;
    }

    @Override
    public PaymentMethodType getType() {
        return PaymentMethodType.UPI;
    }
}
