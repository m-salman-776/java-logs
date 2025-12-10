package Project101.BookingSystem.MoviesBooking.Search;

import Project101.BookingSystem.MoviesBooking.Model.StaticModel.Movie;

import java.util.List;

public interface SearchStrategy {
    List<Movie> search(String searchTerm);
}
