package LLD.MoviesBooking.Classes;

import LLD.MoviesBooking.enums.ShowSlot;
import com.amazonaws.services.dynamodbv2.xspec.L;

import java.util.List;

public class Ticket {
    Movie movie;

    ShowSlot slot;
    List<Long> seatNumer;

    public Ticket(Movie movie, ShowSlot showSlot, List<Long>seatNumer){
        this.movie = movie;
        this.slot = showSlot;
        this.seatNumer = seatNumer;
    }

}
