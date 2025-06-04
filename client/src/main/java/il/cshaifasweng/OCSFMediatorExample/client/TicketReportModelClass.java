package il.cshaifasweng.OCSFMediatorExample.client;
import javafx.beans.property.*;
import java.time.LocalDate;

public class TicketReportModelClass {

    private final ObjectProperty<LocalDate> date;
    private final StringProperty cinema;
    private final IntegerProperty ticketsSold;
    private final DoubleProperty totalSales;


    public TicketReportModelClass(LocalDate date, String cinema, int ticketsSold, double totalSales) {
        this.date = new SimpleObjectProperty<>(date);
        this.cinema = new SimpleStringProperty(cinema);
        this.ticketsSold = new SimpleIntegerProperty(ticketsSold);
        this.totalSales = new SimpleDoubleProperty(totalSales);
    }


    // Getters and Property methods
    public LocalDate getDate() {
        return date.get();
    }

    public ObjectProperty<LocalDate> dateProperty() {
        return date;
    }

    public String getCinema() {
        return cinema.get();
    }

    public StringProperty cinemaProperty() {
        return cinema;
    }

    public int getTicketsSold() {
        return ticketsSold.get();
    }

    public IntegerProperty ticketsSoldProperty() {
        return ticketsSold;
    }

    public double getTotalSales() {
        return totalSales.get();
    }

    public DoubleProperty totalSalesProperty() {
        return totalSales;
    }
}
