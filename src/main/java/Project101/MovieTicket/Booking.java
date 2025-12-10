package Project101.MovieTicket;



import Project101.BookingSystem.MoviesBooking.Model.ShowSeat;
import Project101.MovieTicket.enums.BookingStatus;

import java.util.Date;
import java.util.List;

public class Booking {
    String bookingNumber;
    int numberOfSeat;
    Date createdOn;
    BookingStatus status;
    Show show;
    List<ShowSeat> showSeats;
    Payment payment;
    boolean makePayment(Payment payment){
        return true;
    }
    boolean cancel(){
        return true;
    }
    boolean assignSeat(List<ShowSeat> showSeats){
        this.showSeats = showSeats;
        return true;
    }
}
