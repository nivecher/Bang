package bang.game.cards;

import bang.game.IPlayerEffect;
import bang.game.Player;

/**
 * Created by Morgan on 11/22/2016.
 */
public class DynamiteCard extends PlayingCard implements IPlayerEffect {
    public DynamiteCard(Suit suit, Face face) {
        super("Dynamite", Color.Blue, suit, face);
    }

    @Override
    public boolean apply(Player p) {
        return false; // TODO implement
    }
}
