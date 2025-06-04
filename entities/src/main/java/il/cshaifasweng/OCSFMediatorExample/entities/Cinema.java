package il.cshaifasweng.OCSFMediatorExample.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table (name = "cinema")
public class Cinema implements Serializable {
    @Id
    private int id = 1;

    public static Cinema cinema = null;

    public static Cinema getCinema() {
        if (cinema == null) {
            cinema = new Cinema(100, 90, 1000);
        }
        return cinema;
    }

    public static void setCinema(Cinema cinema) {
        Cinema.cinema = cinema;
    }

    public double ticketPrice;
    public double linkTicketPrice;
    public double cardPrice;

    public Cinema() {}

    public Cinema(double ticketPrice, double linkTicketPrice, double cardPrice) {
      this.ticketPrice = ticketPrice;
      this.linkTicketPrice = linkTicketPrice;
      this.cardPrice = cardPrice;
    }

    public double getTicketPrice() {
        return ticketPrice;
    }

    public double getLinkTicketPrice() {
        return linkTicketPrice;
    }

    public double getCardPrice() {
        return cardPrice;
    }

    public void setTicketPrice(double ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public void setLinkTicketPrice(double linkTicketPrice) {
        this.linkTicketPrice = linkTicketPrice;
    }

    public void setCardPrice(double cardPrice) {
        this.cardPrice = cardPrice;
    }
}
