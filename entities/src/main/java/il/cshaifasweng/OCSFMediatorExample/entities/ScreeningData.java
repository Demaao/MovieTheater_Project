package il.cshaifasweng.OCSFMediatorExample.entities;

import java.io.Serializable;
import java.time.LocalDateTime;

public class ScreeningData implements Serializable {
    private int movieId;  // מזהה של הסרט
    private Integer branchId; // מזהה של בית הקולנוע, עשוי להיות null אם זו הקרנת בית
    private LocalDateTime screeningTime;  // הזמן של ההקרנה
    private Integer hallId;


    // יוצר חדש ללא מזהה ההקרנה, כיוון שהוא אינו נדרש למחיקה
    public ScreeningData(int movieId, Integer branchId, LocalDateTime screeningTime) {
        this.movieId = movieId;
        this.branchId = branchId;
        this.screeningTime = screeningTime;
    }


    public ScreeningData(int movieId, Integer branchId, Integer hallId, LocalDateTime screeningTime) {
        this.movieId = movieId;
        this.branchId = branchId;
        this.hallId = hallId;
        this.screeningTime = screeningTime;
    }

    // Getters
    public int getMovieId() {
        return movieId;
    }

    public Integer getBranchId() {
        return branchId;
    }

    public LocalDateTime getScreeningTime() {
        return screeningTime;
    }

    // Setters
    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public void setBranchId(Integer branchId) {
        this.branchId = branchId;
    }

    public void setScreeningTime(LocalDateTime screeningTime) {
        this.screeningTime = screeningTime;
    }

    public void setHallId(Integer hallId) {
        this.hallId = hallId;
    }
    public Integer getHallId() {
        return hallId;
    }
}
