package LLD.MoviesBooking.Services;
import LLD.MoviesBooking.Classes.Booking;
import LLD.MoviesBooking.Classes.Seat;
import LLD.MoviesBooking.Classes.User;
import LLD.MoviesBooking.Repository.cinemaInventory;
import LLD.MoviesBooking.enums.BookingStatus;
import LLD.MoviesBooking.enums.PaymentStatus;
import LLD.MoviesBooking.enums.SeatStatus;
import LLD.MoviesBooking.enums.ShowSlot;
import java.util.List;
public class MovieBookingApp {
    UserService userService;
    cinemaInventory ticketInventory;
    PricingServing pricingServing;
    PaymentService paymentService;
    BookingService bookingService;
    public MovieBookingApp(UserService userService, cinemaInventory ticketInventory,
                           PaymentService paymentService, PricingServing pricingServing,
                           BookingService bookingService){
        this.userService = userService;
        this.ticketInventory = ticketInventory;
        this.paymentService = paymentService;
        this.pricingServing = pricingServing;
        this.bookingService = bookingService;
    }

    public Booking bookSeat(String cinemaId, ShowSlot showSlot, List<Integer> seatNumber, User user){
        List<Seat> seatList = this.ticketInventory.getSeat(cinemaId,showSlot,seatNumber);
        if (seatList.size() != seatNumber.size()){
            // todo;
        }
        for (Seat seat : seatList) seat.updateStatus(SeatStatus.INITIATED);
        Double amount = this.pricingServing.calculatePrince(seatList);
        Booking booking = this.bookingService.createBooking(seatList,amount,user);
        PaymentStatus paymentStatus = paymentService.makePayment(booking);
        if (paymentStatus == PaymentStatus.COMPLETED){
            seatList.forEach((seat -> seat.updateStatus(SeatStatus.BOOKED)));
        }
        booking.updatePaymentStatus(PaymentStatus.COMPLETED);
        booking.updateBookingStatus(BookingStatus.COMPLETED);
        return booking;
    }


}
