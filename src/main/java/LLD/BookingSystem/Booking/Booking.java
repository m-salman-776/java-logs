package LLD.BookingSystem.Booking;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.joda.time.DateTime;

import java.sql.Time;
@Getter
@Setter
@NoArgsConstructor
public class Booking {
    int id;
    int userId;
    String status;
    DateTime createdTime;
    DateTime updatedTime;
    public Booking(int user_id,DateTime createdTime, DateTime updatedTime){
        this.userId = user_id;
        this.createdTime = createdTime;
        this.updatedTime = updatedTime;
    }
}
enum Status {
    CREATED,
    CANCELLED,
    COMPLETED
}
