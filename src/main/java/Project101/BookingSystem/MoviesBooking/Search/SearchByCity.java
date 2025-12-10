package Project101.BookingSystem.MoviesBooking.Search;

import Project101.BookingSystem.MoviesBooking.Model.StaticModel.Movie;
import Project101.BookingSystem.MoviesBooking.Repository.MovieRepository;
import lombok.Setter;

import java.util.List;

@Setter
public class SearchByCity implements SearchStrategy{
    MovieRepository repository;
    public SearchByCity(MovieRepository movieRepository){
        this.repository = movieRepository;
    }

    @Override
    public List<Movie> search(String searchTerm) {
        return this.repository.fetchMovies(searchTerm);
    }
}
