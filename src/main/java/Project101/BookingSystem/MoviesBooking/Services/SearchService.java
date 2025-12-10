package Project101.BookingSystem.MoviesBooking.Services;

import Project101.BookingSystem.MoviesBooking.Model.StaticModel.Movie;
import Project101.BookingSystem.MoviesBooking.Search.CacheManager;
import Project101.BookingSystem.MoviesBooking.Search.SearchStrategy;
import java.util.List;

public class SearchService {
    SearchStrategy searchStrategy;
    CacheManager cacheManager;
    public void setSearchStrategy(SearchStrategy searchStrategy,CacheManager cacheManager){
        this.cacheManager = cacheManager;
        this.searchStrategy = searchStrategy;
    }
    public List<Movie> search(String searchTerm){
        if (cacheManager.get(searchTerm) != null) return this.cacheManager.get(searchTerm);
        List<Movie> searchResult =  this.searchStrategy.search(searchTerm);
        cacheManager.put(searchTerm,searchResult);
        return searchResult;
    }
}
