package il.cshaifasweng.OCSFMediatorExample.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Halls")
public class Hall implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int rowsNum;
    private int colsNum;
    private int seatsNum;

    private String hallName;

    @ManyToOne(fetch = FetchType.LAZY)
    private Branch branch;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "hall")
    private List<Screening> screenings = new ArrayList<Screening>();

    public Hall() {};

    public Hall(int rows, int cols, int seatsNum, String hallName,Branch branch){
        this.rowsNum = rows;
        this.colsNum = cols;
        this.seatsNum = seatsNum;
        this.branch = branch;
        this.screenings = new ArrayList<Screening>();
        this.hallName = hallName;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRows() {
        return rowsNum;
    }

    public void setRows(int rows) {
        this.rowsNum = rows;
    }

    public int getCols() {
        return colsNum;
    }

    public void setCols(int cols) {
        this.colsNum = cols;
    }

    public int getSeatsNum() {
        return seatsNum;
    }

    public void setSeatsNum(int seatsNum) {
        this.seatsNum = seatsNum;
    }

    public Branch getBranch() {
        return branch;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }

    public int getRowsNum() {
        return rowsNum;
    }

    public void setRowsNum(int rowsNum) {
        this.rowsNum = rowsNum;
    }

    public int getColsNum() {
        return colsNum;
    }

    public void setColsNum(int colsNum) {
        this.colsNum = colsNum;
    }

    public String getHallName() {
        return hallName;
    }

    public void setHallName(String hallName) {
        this.hallName = hallName;
    }

    public void setScreening(List<Screening> screening) {
        this.screenings = screening;
    }

    public void addScreening(Screening screening) {
        this.screenings.add(screening);
    }

    public List<Screening> getScreenings() {
        return screenings;
    }

    public void setScreenings(List<Screening> screenings) {
        this.screenings = screenings;
    }

}