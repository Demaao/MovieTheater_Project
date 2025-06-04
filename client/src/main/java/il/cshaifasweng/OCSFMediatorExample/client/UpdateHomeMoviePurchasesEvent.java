package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.HomeMoviePurchase;
import java.util.List;

public class UpdateHomeMoviePurchasesEvent {
    private List<HomeMoviePurchase> homeMoviePurchases;

    public UpdateHomeMoviePurchasesEvent(List<HomeMoviePurchase> homeMoviePurchases) {
        this.homeMoviePurchases = homeMoviePurchases;
    }

    public List<HomeMoviePurchase> getHomeMoviePurchases() {
        return homeMoviePurchases;
    }
}
