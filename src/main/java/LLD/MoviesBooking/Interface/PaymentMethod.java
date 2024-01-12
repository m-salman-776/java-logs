package LLD.MoviesBooking.Interface;

import LLD.MoviesBooking.Classes.Payment;
import LLD.MoviesBooking.enums.PaymentStatus;

public interface PaymentMethod {
    PaymentStatus processPayment(Payment payment);
}
