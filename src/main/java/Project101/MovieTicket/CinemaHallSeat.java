package Project101.MovieTicket;

import Project101.BookingSystem.MoviesBooking.enums.SeatStatus;
import Project101.BookingSystem.MoviesBooking.enums.SeatType;
import Project101.ElevatorSystem.DataType.Status;
import lombok.Getter;

@Getter
public class CinemaHallSeat {
    int rowNumber;
    int seatNumber;
    SeatStatus status;
    SeatType seatType;
}
