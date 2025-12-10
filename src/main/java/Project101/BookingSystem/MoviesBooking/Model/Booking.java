package Project101.BookingSystem.MoviesBooking.Model;

import Project101.BookingSystem.MoviesBooking.Model.StaticModel.Seat;
import Project101.BookingSystem.MoviesBooking.enums.BookingStatus;
import Project101.BookingSystem.MoviesBooking.enums.PaymentStatus;
import lombok.Getter;
import org.joda.time.LocalDateTime;

import java.util.List;
@Getter
public class Booking {
    String bookingId;
    List<Seat> seatList;
    BookingStatus status;
    PaymentStatus paymentStatus;
    Double paymentAmount;
    User user;
    LocalDateTime createdAt;
    public Booking(List<Seat> seatList,Double paymentAmount,User user){
        this.seatList = seatList;
        this.paymentAmount = paymentAmount;
        this.user = user;
        this.paymentStatus = PaymentStatus.INITIATED;
        this.status = BookingStatus.INITIATED;
        this.createdAt = LocalDateTime.now();
    }
    public void updateBookingStatus(BookingStatus bookingStatus){
        this.status = bookingStatus;
    }
    public void updatePaymentStatus(PaymentStatus paymentStatus){
        this.paymentStatus = paymentStatus;
    }
}
