package Project101.BookingSystem.MoviesBooking.Pricing;

import Project101.BookingSystem.MoviesBooking.Model.ShowSeat;

public abstract class PriceDecorator implements PriceProvider {
    PriceProvider priceProvider;
    public PriceDecorator(PriceProvider priceProvider){
        this.priceProvider = priceProvider;
    }
    @Override
    public double calculatePrice(ShowSeat showSeat) {
        return priceProvider.calculatePrice(showSeat);
    }
}
