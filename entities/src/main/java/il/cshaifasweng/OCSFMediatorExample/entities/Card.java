package il.cshaifasweng.OCSFMediatorExample.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
public class Card extends Purchase implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int tickets;

    private String cardType;

    public Card() {}

    public Card(String productType, LocalDateTime purchaseDate, String paymentMethod, double pricePaid, Customer customer,
                String branch, int quantity, String purchaseDescription, int tickets, String cardType) {
        super(productType, purchaseDate, paymentMethod, pricePaid, customer, branch, quantity, purchaseDescription);
        this.tickets = tickets;
        this.cardType = cardType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTickets() {
        return tickets;
    }

    public void setTickets(int tickets) {
        this.tickets = tickets;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

}
