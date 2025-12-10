package Project101.BookingSystem.MoviesBooking.Pricing;

import Project101.BookingSystem.MoviesBooking.Model.ShowSeat;

public class BasePrice implements PriceProvider {

    @Override
    public double calculatePrice(ShowSeat showSeat) {
        return showSeat.getPrice();
    }
}
