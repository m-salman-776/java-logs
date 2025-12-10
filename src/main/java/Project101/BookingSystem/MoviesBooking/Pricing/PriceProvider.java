package Project101.BookingSystem.MoviesBooking.Pricing;

import Project101.BookingSystem.MoviesBooking.Model.ShowSeat;

public interface PriceProvider {
    double calculatePrice(ShowSeat showSeat);
}
