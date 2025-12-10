package Project101.BookingSystem.MoviesBooking.Search;

import Project101.BookingSystem.MoviesBooking.Model.StaticModel.Movie;
import Project101.BookingSystem.MoviesBooking.Repository.MovieRepository;

import java.util.List;

public class SearchByName implements SearchStrategy{
    MovieRepository repository;
    public  SearchByName(MovieRepository repository){
        this.repository = repository;
    }
    @Override
    public List<Movie> search(String searchTerm) {
        return this.repository.fetchMovies(searchTerm);
    }
}
