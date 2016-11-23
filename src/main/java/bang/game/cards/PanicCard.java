package bang.game.cards;

import bang.game.IPlayerEffect;
import bang.game.Player;

/**
 * Created by Morgan on 11/22/2016.
 */
public class PanicCard extends PlayingCard implements IPlayerEffect {
    public PanicCard(Suit suit, Face face) {
        super("Panic", Color.Brown, suit, face);
    }

    @Override
    public boolean apply(Player p) {
        return false; // TODO implement
    }
}
