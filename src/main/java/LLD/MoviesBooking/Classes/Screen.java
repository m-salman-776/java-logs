package LLD.MoviesBooking.Classes;

import LLD.MoviesBooking.enums.SeatType;
import LLD.MoviesBooking.enums.ShowSlot;

import java.util.*;
import java.util.stream.Collectors;

public class Screen {
    String screenId;
    Map<ShowSlot,List<Seat>> showSeat; // showTime2Seat
    Map<SeatType,Integer>capacity;
    public Screen(Integer regular,Integer premium,Integer royal){
        this.capacity = new HashMap<>();
        capacity.put(SeatType.REGULAR,regular);
        capacity.put(SeatType.ROYAL,royal);
        capacity.put(SeatType.PREMIUM,premium);
    }

    public List<Seat> getSeat(ShowSlot showSlot, List<Integer> seatNumber){
        HashSet<Integer> uniqueSeat = new HashSet<>(seatNumber);
        List<Seat> seats = showSeat.get(showSlot).stream().filter(seat -> uniqueSeat.contains(seat.getNumber())).collect(Collectors.toList());
        return seats;
    }

    public void addSeat(String showId){
        List<Seat> seats = new ArrayList<>();
        Integer seatNumber = 1;
        for (seatNumber = 1 ; seatNumber < capacity.get(SeatType.REGULAR);seatNumber++){
            seats.add(new Seat('x',seatNumber,SeatType.REGULAR));
        }
//        for (seatNumber = 1 ; seatNumber < capacity.get(SeatType.REGULAR);seatNumber++){
//            seats.add(new Seat('x',seatNumber,SeatType.REGULAR));
//        }
//        for (seatNumber = 1 ; seatNumber < capacity.get(SeatType.REGULAR);seatNumber++){
//            seats.add(new Seat('x',seatNumber,SeatType.REGULAR));
//        }
    }
}
