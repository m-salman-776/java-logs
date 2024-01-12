package LLD.MoviesBooking.Services;

import LLD.MoviesBooking.Classes.Booking;
import LLD.MoviesBooking.Classes.Payment;
import LLD.MoviesBooking.Classes.Seat;
import LLD.MoviesBooking.Interface.PaymentMethod;
import LLD.MoviesBooking.enums.PaymentStatus;

import java.util.List;

public class PaymentService {
    PaymentMethod paymentMethod;
    public PaymentService(PaymentMethod paymentMethod){
        this.paymentMethod = paymentMethod;
    }
    PaymentStatus makePayment(Booking booking){
        Payment payment = new Payment(booking.getPaymentAmount());
        PaymentStatus paymentStatus = this.paymentMethod.processPayment(payment);
        return paymentStatus;
    }

}
