package Project101.BookingSystem.MoviesBooking.Model;

import Project101.BookingSystem.MoviesBooking.enums.SeatStatus;
import Project101.MovieTicket.Booking;
import Project101.MovieTicket.CinemaHallSeat;
import lombok.Getter;

@Getter
public class ShowSeat extends CinemaHallSeat {
    double price;
    String showId;
    Show show;
    SeatStatus status;
    String bookingId;
    public ShowSeat(double price, Show show){
        this.status = SeatStatus.AVAILABLE;
        this.price = price;
        this.show = show;
        this.bookingId = null;
    }
    public void updateStatus(SeatStatus seatStatus){
        this.status = seatStatus;
    }
    public void updateBookingId(String bookingId){
        this.bookingId = bookingId;
    }
    boolean isAvailable(){
        return this.status == SeatStatus.AVAILABLE;
    }
    public synchronized boolean lock(String bookingId){
        if (status != SeatStatus.AVAILABLE){
            return false;
        }
        this.status = SeatStatus.LOCKED;
        this.bookingId = bookingId;
        return true;
    }
    public boolean release(){
        if (status != SeatStatus.BOOKED){
            this.status = SeatStatus.AVAILABLE;
            this.bookingId = null;
            return true;
        }
        return false;
    }
}
