package Project101.BookingSystem.MoviesBooking.Repository;

import Project101.BookingSystem.MoviesBooking.Model.Booking;
import Project101.BookingSystem.MoviesBooking.Model.StaticModel.Seat;
import Project101.BookingSystem.MoviesBooking.Model.User;
import Project101.BookingSystem.MoviesBooking.enums.BookingStatus;
import Project101.BookingSystem.MoviesBooking.enums.PaymentStatus;

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
    public void save(Booking booking){
        this.bookingMap.put(booking.getBookingId(),booking);
    }
}
