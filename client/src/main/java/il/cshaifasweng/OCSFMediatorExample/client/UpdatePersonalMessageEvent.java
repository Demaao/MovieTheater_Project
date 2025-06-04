package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.ChangePriceRequest;
import il.cshaifasweng.OCSFMediatorExample.entities.Notification;

import java.util.List;

public class UpdatePersonalMessageEvent {
    private final List<Notification> notifications;

    public UpdatePersonalMessageEvent(List<Notification> notifications) {
        this.notifications = notifications;
    }

    public List<Notification> getNotifications() {
        return notifications;
    }
}
