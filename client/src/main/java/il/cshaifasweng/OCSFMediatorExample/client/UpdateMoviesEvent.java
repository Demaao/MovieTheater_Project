package il.cshaifasweng.OCSFMediatorExample.client;
import il.cshaifasweng.OCSFMediatorExample.entities.Movie;

import java.io.Serializable;
import java.util.List;

public class UpdateMoviesEvent implements Serializable {
    private  List<Movie> movies;

    public UpdateMoviesEvent(List<Movie> movies) {
        this.movies = movies;
    }

    public List<Movie> getMovies() {
        return movies;
    }
}