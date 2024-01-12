package LLD.MoviesBooking.Classes;

import LLD.MoviesBooking.enums.PaymentStatus;

public class Payment {
    Double amount;
    PaymentStatus paymentStatus;

    public Payment(Double amount){
        this.amount = amount;
        this.paymentStatus = PaymentStatus.INITIATED;
    }
}
