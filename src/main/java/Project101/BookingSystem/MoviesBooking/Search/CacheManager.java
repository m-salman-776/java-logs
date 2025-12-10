package Project101.BookingSystem.MoviesBooking.Search;

import Project101.BookingSystem.MoviesBooking.Model.StaticModel.Movie;

import java.util.*;

public class CacheManager {
    Map<String, List<Movie>> searchCache;
    public CacheManager(){
        searchCache = new HashMap<>();
    }
    public List<Movie> get(String searchTerm){
        return searchCache.get(searchTerm);
    }
    public void put(String searchTerm, List<Movie> movies){
        searchCache.put(searchTerm,movies);
    }
}
