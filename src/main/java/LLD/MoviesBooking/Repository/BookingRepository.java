package LLD.MoviesBooking.Repository;

import LLD.MoviesBooking.Classes.Booking;
import LLD.MoviesBooking.Classes.Seat;
import LLD.MoviesBooking.Classes.User;
import LLD.MoviesBooking.Services.PricingServing;
import LLD.MoviesBooking.enums.BookingStatus;
import LLD.MoviesBooking.enums.PaymentStatus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookingRepository {
    Map<String, Booking> bookingMap;
    public BookingRepository(){
        this.bookingMap = new HashMap<>();
    }
    public Booking createBooking(List<Seat> seatList, Double paymentAmount, User user){
        String bookingId = "";
        Booking booking = new Booking( seatList, paymentAmount, user);
        bookingMap.put(bookingId,booking);
        return booking;
    }
    public Booking getBooking(String bookingId){
        return this.bookingMap.get(bookingId);
    }

    public void updateBookingStatus(String bookingId,BookingStatus bookingStatus){
        this.bookingMap.get(bookingId).updateBookingStatus(bookingStatus);
    }
    public void updateBookingPaymentStatus(String bookingId, PaymentStatus paymentStatus){
        this.bookingMap.get(bookingId).updatePaymentStatus(paymentStatus);
    }
}
