//package Project101.BookingSystem.MoviesBooking.Services;
//import Project101.BookingSystem.MoviesBooking.Model.Booking;
//import Project101.BookingSystem.MoviesBooking.Model.StaticModel.Seat;
//import Project101.BookingSystem.MoviesBooking.Model.User;
//import Project101.BookingSystem.MoviesBooking.Repository.cinemaInventory;
//import Project101.BookingSystem.MoviesBooking.enums.BookingStatus;
//import Project101.BookingSystem.MoviesBooking.enums.PaymentStatus;
//import Project101.BookingSystem.MoviesBooking.enums.SeatStatus;
//import Project101.BookingSystem.MoviesBooking.enums.ShowSlot;
//import java.util.List;
//public class MovieBookingApp {
//    UserService userService;
//    cinemaInventory ticketInventory;
//    PricingServing pricingServing;
//    PaymentService paymentService;
//    BookingService bookingService;
//    public MovieBookingApp(UserService userService, cinemaInventory ticketInventory,
//                           PaymentService paymentService, PricingServing pricingServing,
//                           BookingService bookingService){
//        this.userService = userService;
//        this.ticketInventory = ticketInventory;
//        this.paymentService = paymentService;
//        this.pricingServing = pricingServing;
//        this.bookingService = bookingService;
//    }
//
//    public Booking bookSeat(String cinemaId, ShowSlot showSlot, List<Integer> seatNumber, User user){
//        List<Seat> seatList = this.ticketInventory.getSeat(cinemaId,showSlot,seatNumber);
//        if (seatList.size() != seatNumber.size()){
//            // todo;
//        }
//        for (Seat seat : seatList) seat.updateStatus(SeatStatus.INITIATED);
//        Double amount = this.pricingServing.calculatePrince(seatList);
//        Booking booking = this.bookingService.createBooking(seatList,amount,user);
//        PaymentStatus paymentStatus = paymentService.makePayment(booking);
//        if (paymentStatus == PaymentStatus.COMPLETED){
//            seatList.forEach((seat -> seat.updateStatus(SeatStatus.BOOKED)));
//        }
//        booking.updatePaymentStatus(PaymentStatus.COMPLETED);
//        booking.updateBookingStatus(BookingStatus.COMPLETED);
//        return booking;
//    }
//
//
//}
