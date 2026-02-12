package Project101.LoyaltyProgram;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class BookingStats {
    Map<BookingType,Integer> bookingCount;
    double amount;
    public BookingStats(){
        amount = 0L;
        bookingCount = new ConcurrentHashMap<>();
        for (BookingType tier : BookingType.values()){
            bookingCount.putIfAbsent(tier,0);
        }
    }
    public void updateBookingCount(BookingEvent event){
//        this.bookingCount.put(event.type,bookingCount.getOrDefault(event.type,0)+1);
        this.bookingCount.merge(event.type,1,Integer::sum);
        synchronized (this){
            this.amount += event.amount;
        }
    }

    public double getAmount() {
        return amount;
    }

    public int getConfig(BookingType type){
        return this.bookingCount.get(type);
    }
}
