//package Project101.BookingSystem.MoviesBooking.Repository;
//
//import Project101.BookingSystem.MoviesBooking.Model.StaticModel.Seat;
//import Project101.BookingSystem.MoviesBooking.enums.ShowFormat;
//import Project101.BookingSystem.MoviesBooking.enums.ShowSlot;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//public class cinemaInventory {
//    Map<String,Screen>showSeats;
//    public cinemaInventory(){
//        showSeats = new HashMap<>();
//    }
//
//    public boolean addShow(String showId, Integer capacity, ShowFormat showFormat){
//        Screen screen = new Screen(50,50,50);
//        screen.addSeat(showId);
//        showSeats.put(showId,screen);
//        return true;
//    }
//
//    public List<Seat> getSeat(String theaterId , ShowSlot showSlot, List<Integer> seatList){
//        String showId = theaterId + showSlot.getDeclaringClass().getName();
//        List<Seat> seats = showSeats.get(showId).getSeat(showSlot,seatList);
//        return seats;
//    }
//
//
//
//}
//
////class Inventory{
////    String movieName;
////    String cinemaId;
////    String showId;
////    Integer totalTicket;
////    Integer reveredTicket;
////}
