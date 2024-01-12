package LLD.MoviesBooking.Interface;

import LLD.MoviesBooking.Classes.Movie;

import java.util.List;

public interface MovieFilter {
    List<Movie> filter(List<Movie> movies);
}
