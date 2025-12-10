package Project101.BookingSystem.MoviesBooking.Services;

import Project101.BookingSystem.MoviesBooking.Model.StaticModel.Seat;

import java.util.List;

public class PricingServing {

    public PricingServing(){

    }
    public Double calculatePrince(List<Seat> seatList){
        Double price = 0.0;
        for (Seat seat : seatList) price += 10.9;
        return price;
    }
}
