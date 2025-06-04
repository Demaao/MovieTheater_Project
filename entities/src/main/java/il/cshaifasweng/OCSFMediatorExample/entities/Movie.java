package il.cshaifasweng.OCSFMediatorExample.entities;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "DTYPE")
@Table(name = "movies")
public class Movie implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String Engtitle;
    private String Hebtitle;
    private String director;
    private int RlsYear;
    private String genre;
    private String description;
    private String mainActors;
    private String length;

    @Lob
    @Column(columnDefinition="BLOB")  // הגדרה של עמודת BLOB במסד הנתונים
    private byte[] imageData;

    @ManyToMany(fetch = FetchType.EAGER)
    @Fetch(FetchMode.JOIN)
    @JoinTable(
            name = "movie_branch",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "branch_id")
    )
    private List<Branch> branches = new ArrayList<>();


    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    private List<Screening> screenings = new ArrayList<>();

    public Movie() {}

    public Movie(int id, String Engtitle, String Hebtitle, String director, int RlsYear, byte[] imageData, String genre, String description, String mainActors, String length) {
        this.id = id;
        this.Engtitle = Engtitle;
        this.Hebtitle = Hebtitle;
        this.director = director;
        this.RlsYear = RlsYear;
        this.imageData = imageData;
        this.genre = genre;
        this.description = description;
        this.mainActors = mainActors;
        this.length = length;
    }

    // Getters and setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEngtitle() {
        return Engtitle;
    }

    public void setEngtitle(String Engtitle) {
        this.Engtitle = Engtitle;
    }

    public String getHebtitle() {
        return Hebtitle;
    }

    public void setHebtitle(String Hebtitle) {
        this.Hebtitle = Hebtitle;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public int getYear() {
        return RlsYear;
    }

    public void setYear(int RlsYear) {
        this.RlsYear = RlsYear;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMainActors() {
        return mainActors;
    }

    public void setMainActors(String mainActors) {
        this.mainActors = mainActors;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public byte[] getImageData() {
        return imageData;
    }

    public void setImageData(byte[] imageData) {
        this.imageData = imageData;
    }

    public List<Branch> getBranches() {
        return branches;
    }

    public void setBranches(List<Branch> branches) {
        this.branches = branches;
    }

    public List<Screening> getScreenings() {
        return screenings;
    }

    public void setScreenings(List<Screening> screenings) {
        this.screenings = screenings;
    }

    public void addScreening(LocalDateTime screeningTime, Branch branch, Hall hall) {
        Screening screening = new Screening(screeningTime, this, branch, hall);
        this.screenings.add(screening);
    }


    public void removeScreening(Screening screening) {
        screenings.remove(screening);
    }


}