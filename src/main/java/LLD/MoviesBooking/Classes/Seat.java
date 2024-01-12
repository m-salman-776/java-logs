package LLD.MoviesBooking.Classes;

import LLD.MoviesBooking.enums.SeatStatus;
import LLD.MoviesBooking.enums.SeatType;
import lombok.Getter;

@Getter
public class Seat {
//    Character row;
    Integer number;
    SeatStatus status;
    SeatType seatType;
    public Seat(Character row,Integer number,SeatType type){
        this.number = number;
//        this.row = row;
        this.seatType = type;
        this.status = SeatStatus.AVAILABLE;
    }

    public void updateStatus(SeatStatus seatStatus){
        this.status = seatStatus;
    }

}
