//package Project101.BookingSystem.MoviesBooking.Pricing;
//
//import Project101.BookingSystem.MoviesBooking.Model.ShowSeat;
//
//public class WeekendPriceProvider extends PriceDecorator{
//    public WeekendPriceProvider(PriceProvider priceProvider) {
//        super(priceProvider);
//    }
//    @Override
//    public double calculatePrice(ShowSeat showSeat) {
//        double currentPrice = this.priceProvider.calculatePrice(showSeat);
//        if (showSeat.getShow().getStartTime().getDay() == 6 || showSeat.getShow().getStartTime().getDate() == 7){
//            return currentPrice + currentPrice * 0.20;
//        }
//        return currentPrice;
//    }
//}
