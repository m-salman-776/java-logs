package Project101.BookingSystem.MoviesBooking.Repository;

import Project101.BookingSystem.MoviesBooking.Model.Show;

import java.util.HashMap;
import java.util.Map;

public class ShowRepository {
    Map<String, Show> showMap;
    public ShowRepository(){
        this.showMap = new HashMap<>();
    }
    public void addShow(String showId, Show show){
        this.showMap.put(showId,show);
    }
    public Show getShow(String showId){
        return this.showMap.get(showId);
    }
}
