package il.cshaifasweng.OCSFMediatorExample.entities;

import java.io.Serializable;

public class BookingSeatsReq implements Serializable{

    private int[] seats;
    private String[] seatIds;
    private Screening screening;
    private int arrSize;

    public BookingSeatsReq(Screening screening,  int size,int[] seats, String[] seatIds) {
        this.setScreening(screening);
        this.setArrSize(size);
        this.setSeats(seats);
        this.setSeatIds(seatIds);
    }
    public Screening getScreening() {
        return screening;
    }


    public void setScreening(Screening screening) {
        this.screening = screening;
    }


    public int getArrSize() {  return arrSize; }


    public void setArrSize(int arrSize) {
        this.arrSize = arrSize;
    }


    public int[] getSeats() {
        return seats;
    }


    public void setSeats(int[] seats) {
        this.seats = seats;
    }


    public String[] getSeatIds() {
        return seatIds;
    }


    public void setSeatIds(String[] seatIds) {
        this.seatIds = seatIds;
    }

}
