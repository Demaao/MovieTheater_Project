package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Purchase;
import java.util.List;

public class UpdatePurchasesEvent {
    private List<Purchase> purchases;

    public UpdatePurchasesEvent(List<Purchase> purchases) {
        this.purchases = purchases;
    }

    public List<Purchase> getPurchases() {
        return purchases;
    }

}

