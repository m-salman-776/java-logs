package Project101.BookingSystem.MoviesBooking.Model.StaticModel;

import Project101.BookingSystem.MoviesBooking.enums.SeatStatus;
import Project101.BookingSystem.MoviesBooking.enums.SeatType;
import lombok.Getter;

@Getter
public class Seat {
    Character row;
    Integer number;
    SeatStatus status;
    SeatType seatType;
    public Seat(Character row,Integer number,SeatType type){
        this.number = number;
        this.row = row;
        this.seatType = type;
        this.status = SeatStatus.AVAILABLE;
    }

    public void updateStatus(SeatStatus seatStatus){
        this.status = seatStatus;
    }

}
