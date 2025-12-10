package Project101.BookingSystem.MoviesBooking.Model;

import Project101.BookingSystem.MoviesBooking.Interface.PaymentMethod;
import Project101.BookingSystem.MoviesBooking.enums.PaymentStatus;

public class OnlinePayment implements PaymentMethod {
    @Override
    public PaymentStatus processPayment(Payment payment) {
        return null;
    }
}
