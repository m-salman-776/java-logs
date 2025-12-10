package Project101.BookingSystem.MoviesBooking.Interface;

import Project101.BookingSystem.MoviesBooking.Model.StaticModel.Movie;

import java.util.List;

public interface MovieFilter {
    List<Movie> filter(List<Movie> movies);
}
