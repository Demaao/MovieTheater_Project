package il.cshaifasweng.OCSFMediatorExample.client;
import il.cshaifasweng.OCSFMediatorExample.entities.HomeMovie;

import java.io.Serializable;
import java.util.List;

public class UpdateHomeMoviesEvent implements Serializable {
    private  List<HomeMovie> homeMovies;

    public UpdateHomeMoviesEvent(List<HomeMovie> homeMovies) {
        this.homeMovies = homeMovies;
    }

    public List<HomeMovie> getHomeMovies() {
        return homeMovies;
    }
}
