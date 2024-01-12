package LLD.MoviesBooking.Services;

import LLD.MoviesBooking.Classes.Booking;
import LLD.MoviesBooking.Classes.Seat;
import LLD.MoviesBooking.Classes.User;
import LLD.MoviesBooking.Repository.BookingRepository;

import java.util.List;

public class BookingService {

    private BookingRepository bookingRepository;

    public BookingService(BookingRepository bookingRepository){
        this.bookingRepository = bookingRepository;
    }

    public synchronized Booking createBooking(List<Seat>seatList,Double amount,User user){
        Booking booking = this.bookingRepository.createBooking(seatList,amount,user);
        return booking;
    }
}
