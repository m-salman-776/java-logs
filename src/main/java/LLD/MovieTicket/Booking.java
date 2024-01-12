package LLD.MovieTicket;



import LLD.MovieTicket.enums.BookingStatus;

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
