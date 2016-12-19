package bang.game.dodgecity.cards;

import bang.game.cards.Color;
import bang.game.cards.Face;
import bang.game.cards.PlayingCard;
import bang.game.cards.Suit;

/**
 * Created by Morgan on 12/18/2016.
 */
public class DodgeCityCard extends PlayingCard {
    /**
     * Construct a new playing card
     *
     * @param name  card's name
     * @param color card's color
     * @param suit  card's suit
     * @param face  card's face
     */
    public DodgeCityCard(String name, Color color, Suit suit, Face face) {
        super(name, color, suit, face);
    }
}
