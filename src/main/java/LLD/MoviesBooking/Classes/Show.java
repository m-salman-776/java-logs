package LLD.MoviesBooking.Classes;

import LLD.MoviesBooking.enums.ShowFormat;
import LLD.MoviesBooking.enums.ShowSlot;

import java.util.ArrayList;
import java.util.List;

public class Show {
    String hallId;
    List<Seat> seatList;
    ShowSlot showSlot;
    Movie movie;
    ShowFormat showFormat;
    public Show(ShowSlot showSlot,ShowFormat showFormat,Movie movie){
        seatList = new ArrayList<>();
        this.movie = movie;
        this.showSlot = showSlot;
        this.showFormat = showFormat;
    }
}
