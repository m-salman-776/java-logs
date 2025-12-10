package Project101.BookingSystem.MoviesBooking.Services;

import Project101.BookingSystem.MoviesBooking.Model.Booking;
import Project101.BookingSystem.MoviesBooking.Model.Payment;
import Project101.BookingSystem.MoviesBooking.Interface.PaymentMethod;
import Project101.BookingSystem.MoviesBooking.enums.PaymentStatus;

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
