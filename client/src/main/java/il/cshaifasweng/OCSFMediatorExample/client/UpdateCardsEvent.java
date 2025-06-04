package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Card;
import il.cshaifasweng.OCSFMediatorExample.entities.Complaint;

import java.io.Serializable;
import java.util.List;

public class UpdateCardsEvent implements Serializable {
    private final List<Card> cards;
    public UpdateCardsEvent(List<Card> cards) { this.cards = cards;
    }
    public List<Card> getCards() { return cards; }
}