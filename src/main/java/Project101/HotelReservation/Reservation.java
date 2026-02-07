package Project101.HotelReservation;

import java.time.LocalDate;

public class Reservation {
    int id;
    LocalDate start_date;
    LocalDate end_date;
    int roomType;
    int quantity;
    ReservationStatus status;
    public Reservation(int id,LocalDate start_date,LocalDate end_date,int roomType,int quantity){
        this.id = id;
        this.start_date = start_date;
        this.end_date = end_date;
        this.roomType = roomType;
        this.quantity = quantity;
        this.status = ReservationStatus.CREATED;
    }
    public ReservationStatus getReservationStatus(){
        return this.status;
    }
    public void setReservationStatus(ReservationStatus status){
        this.status =status;
    }
    public LocalDate getStartDate(){
        return this.start_date;
    }
    public LocalDate getEndDate(){
        return this.end_date;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getRoomType() {
        return roomType;
    }
}
