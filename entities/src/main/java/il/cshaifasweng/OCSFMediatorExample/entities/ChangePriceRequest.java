package il.cshaifasweng.OCSFMediatorExample.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "requests")
public class ChangePriceRequest implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private double currentPrice;
    private double newPrice;
    private String status;   ///////////// Received, Confirmed, Denied
    private String product;      ///////////////////////////////////////
  //  private Product product;  /////////////////////////////////////////
    private LocalDateTime date;

    public ChangePriceRequest() {}

    public ChangePriceRequest(double currentPrice, double newPrice, String product) {
        this.newPrice = newPrice;
    /*    switch (product){
            case "Ticket":
                this.currentPrice = Cinema.getCinema().getTicketPrice();
                break;
            case "Link Ticket":
                this.currentPrice = Cinema.getCinema().getLinkTicketPrice();
                break;
            case "Card":
                this.currentPrice = Cinema.getCinema().getCardPrice();
                break;
        }*/
        this.currentPrice = currentPrice;
        this.status = "Received";
        this.product = product;
        this.date = LocalDateTime.now();
    }

    public ChangePriceRequest(double currentPrice, double newPrice, String product, String status, LocalDateTime date) {
        this.currentPrice = currentPrice;
        this.newPrice = newPrice;
        this.status = status;
        this.product = product;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public double getCurrentPrice() {
        return currentPrice;
    }

    public double getNewPrice() {
        return newPrice;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public String getStatus() {
        return status;
    }

    public String getProduct() {  ////////////////////////// Product instead of String
        return product;
    }

    public void setCurrentPrice(double currentPrice) {
        this.currentPrice = currentPrice;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
