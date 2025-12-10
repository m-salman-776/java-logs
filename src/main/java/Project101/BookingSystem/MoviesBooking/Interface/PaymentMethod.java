package Project101.BookingSystem.MoviesBooking.Interface;

import Project101.BookingSystem.MoviesBooking.Model.Payment;
import Project101.BookingSystem.MoviesBooking.enums.PaymentStatus;

public interface PaymentMethod {
    PaymentStatus processPayment(Payment payment);
}
