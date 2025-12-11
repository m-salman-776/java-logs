package Project101.BookingSystem.MoviesBooking.Model;

import Project101.BookingSystem.MoviesBooking.Model.StaticModel.Movie;
import Project101.BookingSystem.MoviesBooking.enums.ShowSlot;

import java.util.List;

public class Ticket {
    Movie movie;
    ShowSlot slot;
    List<Long> seatNumber;

    public Ticket(Movie movie, ShowSlot showSlot, List<Long>seatNumer){
        this.movie = movie;
        this.slot = showSlot;
        this.seatNumber = seatNumer;
    }

}
