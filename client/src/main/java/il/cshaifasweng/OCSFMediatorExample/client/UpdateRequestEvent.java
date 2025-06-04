package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.ChangePriceRequest;
import il.cshaifasweng.OCSFMediatorExample.entities.Movie;

import java.util.List;

public class UpdateRequestEvent {
    private final List<ChangePriceRequest> requests;

    public UpdateRequestEvent(List<ChangePriceRequest> requests) {
        this.requests = requests;
    }

    public List<ChangePriceRequest> getRequests() {
        return requests;
    }
}
