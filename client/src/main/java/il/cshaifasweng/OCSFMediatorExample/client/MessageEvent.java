package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Movie;
import java.util.List;
import il.cshaifasweng.OCSFMediatorExample.entities.*;

public class MessageEvent {
    private String message;
    private List<Movie> movieList;
    private NewMessage msg;

    public MessageEvent(String message, List<Movie> movieList) {
        this.message = message;
        this.movieList = movieList;
    }

    public MessageEvent(NewMessage msg) {
        this.msg = msg;
    }


    public MessageEvent(String message) { /////////////////////////////////////
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Movie> getMovieList() {
        return movieList;
    }

    public void setMovieList(List<Movie> movieList) {
        this.movieList = movieList;
    }

}