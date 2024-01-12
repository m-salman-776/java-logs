package LLD.MoviesBooking.Classes;

import LLD.MoviesBooking.Interface.PaymentMethod;
import LLD.MoviesBooking.enums.PaymentStatus;

public class OnlinePayment implements PaymentMethod {
    @Override
    public PaymentStatus processPayment(Payment payment) {
        return null;
    }
}
