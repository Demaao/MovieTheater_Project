package il.cshaifasweng.OCSFMediatorExample.entities;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "branches")
public class Branch implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private String location;

    @OneToMany(mappedBy = "branch", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Hall> halls = new ArrayList<>();


    @ManyToMany(mappedBy = "branches", fetch = FetchType.EAGER)
    private List<Movie> movies;

    @ManyToOne
    private HeadManager headManager;

    @OneToOne
    @JoinColumn(name = "branch_manager_id")
    private BranchManager branchManager;


  @OneToMany(mappedBy = "branch", cascade = CascadeType.ALL, fetch = FetchType.LAZY)  //checked
    private List<Screening> screenings;

    public List<Screening> getScreenings() {
        return screenings;
    }

    public void setScreenings(List<Screening> screenings) {
        this.screenings = screenings;
    }

    public Branch() {}

    public Branch(int id, String CinemaName, String location){//, BranchManager branchManager, HeadManager headManager) {
        this.id = id;
        this.name = CinemaName;
        this.location = location;
        this.movies = new ArrayList<>(); /////////////////////////
        // this.branchManager = branchManager; //////////////////////
        // setHeadManager(headManager); ////////////////////////////////
        // this.branchManager = branchManager; //////////////////////
        // setHeadManager(headManager); ////////////////////////////////
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }

    public BranchManager getBranchManager() {
        return branchManager;
    }
    public void setBranchManager(BranchManager branchManager) {
        this.branchManager = branchManager;
    }

    public void setHeadManager(HeadManager headManager) { /////////////////////////////////////
        this.headManager = headManager;
        headManager.getBranches().add(this);
    }

    public void addMovie(Movie... movies1){ ///////////////////////
        for(Movie movie : movies1){
            movies.add(movie);
            movie.getBranches().add(this);
        }
    }

    public void addHall(Hall hall) {
        halls.add(hall);
        hall.setBranch(this);
    }

    public List<Hall> getHalls() {
        return halls;
    }
    public void setHalls(List<Hall> halls) {
        this.halls = halls;
    }
}