package Project101.BookingSystem.MoviesBooking.Model;

import Project101.BookingSystem.MoviesBooking.enums.PaymentStatus;

public class Payment {
    Double amount;
    PaymentStatus paymentStatus;

    public Payment(Double amount){
        this.amount = amount;
        this.paymentStatus = PaymentStatus.INITIATED;
    }
}
