package il.cshaifasweng.OCSFMediatorExample.client;
import il.cshaifasweng.OCSFMediatorExample.entities.SoonMovie;

import java.io.Serializable;
import java.util.List;

public class UpdateSoonMoviesEvent implements Serializable {
    private final List<SoonMovie> soonMovies;

    public UpdateSoonMoviesEvent(List<SoonMovie> soonMovies) {
        this.soonMovies = soonMovies;
    }

    public List<SoonMovie> getSoonMovies() {
        return soonMovies;
    }
}
