package il.cshaifasweng.OCSFMediatorExample.client;

public class UpdateTicketsPurchasesEvent {
    private String message;

    public UpdateTicketsPurchasesEvent(String message) {
        this.message = message;
    }

    public UpdateTicketsPurchasesEvent() {

    }

    public String getMessage() {
        return message;
    }
}

