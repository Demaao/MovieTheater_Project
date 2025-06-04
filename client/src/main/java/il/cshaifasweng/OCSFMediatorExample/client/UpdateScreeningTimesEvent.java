package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Screening;

import java.util.List;

public class UpdateScreeningTimesEvent {
    private List<Screening> screenings;

    public UpdateScreeningTimesEvent(List<Screening> screenings) {
        this.screenings = screenings;
    }

    public List<Screening> getScreenings() {
        return screenings;
    }

    public void setScreenings(List<Screening> screenings) {
        this.screenings = screenings;
    }

}