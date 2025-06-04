package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Cinema;

public class UpdateCinemaEvent {
    private final Cinema cinema;
    public UpdateCinemaEvent(Cinema cinema) { this.cinema = cinema; }
    public Cinema getCinema() { return cinema; }
}
