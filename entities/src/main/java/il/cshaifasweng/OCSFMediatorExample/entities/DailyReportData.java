package il.cshaifasweng.OCSFMediatorExample.entities;
import java.io.Serializable;
import java.time.LocalDate;

public class DailyReportData implements Serializable {
    private LocalDate date;
    private int cardsSold = 0;
    private int linksSold = 0;
    private double cardRevenue = 0.0;
    private double linkRevenue = 0.0;

    public DailyReportData(LocalDate date) {
        this.date = date;
    }
    public void addLinkSold() {
        this.linksSold++;
    }

    public void addLinkRevenue(double revenue) {
        this.linkRevenue += revenue;
    }

    public void addCardSold() {
        this.cardsSold++;
    }

    public void addCardRevenue(double revenue) {
        this.cardRevenue += revenue;
    }
    public void addCard(double pricePaid) {
        this.cardsSold++;
        this.cardRevenue += pricePaid;
    }

    public void addLink(double pricePaid) {
        this.linksSold++;
        this.linkRevenue += pricePaid;
    }

    public double getTotalRevenue() {
        return cardRevenue + linkRevenue;
    }

    public LocalDate getDate() {
        return date;
    }

    public int getCardsSold() {
        return cardsSold;
    }

    public int getLinksSold() {
        return linksSold;
    }

    public double getCardRevenue() {
        return cardRevenue;
    }

    public double getLinkRevenue() {
        return linkRevenue;
    }

    @Override
    public String toString() {
        return "Date: " + date +
                ", Cards Sold: " + cardsSold +
                ", Links Sold: " + linksSold +
                ", Card Revenue: " + cardRevenue +
                ", Link Revenue: " + linkRevenue +
                ", Total Revenue: " + getTotalRevenue();
    }
}