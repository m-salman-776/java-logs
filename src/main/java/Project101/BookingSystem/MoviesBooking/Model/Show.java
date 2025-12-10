package Project101.BookingSystem.MoviesBooking.Model;

import Project101.BookingSystem.MoviesBooking.Model.StaticModel.Movie;
import Project101.BookingSystem.MoviesBooking.enums.ShowFormat;
import Project101.BookingSystem.MoviesBooking.enums.ShowSlot;

public class Show {
    String hallId;
    ShowSlot showSlot;
    Movie movie;
    ShowFormat showFormat;
    double basePrice;
    public Show(ShowSlot showSlot,ShowFormat showFormat,Movie movie){
        this.movie = movie;
        this.showSlot = showSlot;
        this.showFormat = showFormat;
    }
    double getBasePrice(){
        return this.basePrice;
    }
}
