package il.cshaifasweng.OCSFMediatorExample.entities;
import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "screenings")
public class Screening implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private LocalDateTime screeningTime;
    private String screeningBranch;
    private String screeningHall;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "movie_id")
    private Movie movie;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "branch_id", nullable = true) // branch יכול להיות null במקרה של סרטי בית
    private Branch branch;

    //@ManyToOne(fetch = FetchType.LAZY)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "hall_id", nullable = true)
    private Hall hall;

    @OneToMany(mappedBy = "screening", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<HomeMoviePurchase> homeMoviePurchases = new ArrayList<>();

    private boolean[] seatsArray;
    private int availableSeats;
    private int takenSeats;
    public int getTakenSeats() {
        return takenSeats;
    }

    public void setTakenSeats(int takenSeats) {
        this.takenSeats = takenSeats;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }

    public Screening() {}

    public Screening(LocalDateTime screeningTime, Movie movie, Branch branch) {
        this.screeningTime = screeningTime;
        this.movie = movie;
       this.branch = branch;
    }

    public Screening(LocalDateTime screeningTime, Movie movie, Branch branch, Hall hall) {
        this.screeningTime = screeningTime;
        this.movie = movie;
        this.branch = branch;
        this.setHall(hall);
        if (hall != null) {
            hall.addScreening(this);
        }

    }

    public void setHall(Hall hall) {
        this.hall = hall;
        if (hall != null) {
            this.setScreeningHall(hall.getHallName());
            availableSeats = hall.getSeatsNum();
            this.seatsArray = new boolean[availableSeats];
            for (int i = 0; i < availableSeats; i++) {
                seatsArray[i] = false;
            }
        }
    }

    public String getScreeningHourAndMinute() {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
            return screeningTime.format(formatter);
        }

        // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTakenSeatAt(int i) {
        if (i<hall.getSeatsNum()) {
            seatsArray[i]=true;
        }
    }
    public boolean getSeatStatus(int i) {
        return seatsArray[i];
    }

    public void setAvailableSeatAt(int i) {
        if (i<hall.getSeatsNum()) {
            seatsArray[i]=false;
        }
    }

    public void setScreeningHall(String screeningHall) {
        this.screeningHall = screeningHall;
    }

    public Hall getHall() {
        return this.hall;
    }

    public LocalDateTime getScreeningTime() {
        return screeningTime;
    }

    public void setScreeningTime(LocalDateTime screeningTime) {
        this.screeningTime = screeningTime;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public Branch getBranch() {
        return this.branch;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }

    public String myToString() {
        if(this.movie instanceof HomeMovie)
            return this.screeningTime.toLocalDate().toString() + "    " + this.screeningTime.toLocalTime();
        else
            return this.screeningTime.toLocalDate().toString() + "    " + this.screeningTime.toLocalTime() + "        " + this.branch.getName();
    }

    public void setHomeMoviePurchase(List<HomeMoviePurchase> homeMoviePurchase) {
        this.homeMoviePurchases = homeMoviePurchase;
    }

    public List<HomeMoviePurchase> getHomeMoviePurchases() {
        return homeMoviePurchases;
    }
}