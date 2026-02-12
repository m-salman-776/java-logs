package Project101.LoyaltyProgram;

public class BookingEvent {
    int userId;
    BookingType type;
    double amount;
    public BookingEvent(int userId,BookingType bookingType,double amount){
        this.userId = userId;
        this.type = bookingType;
        this.amount = amount;
    }
}
