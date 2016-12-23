package bang.game.cards;

import bang.game.IPlayerEffect;
import bang.game.Player;
import bang.game.PlayerDistance;

/**
 * Created by Morgan on 11/22/2016.
 */
public class PanicCard extends PlayingCard implements IPlayerEffect {
    public PanicCard(Suit suit, Face face) {
        super("Panic", Color.Brown, suit, face);
    }

    @Override
    public PlayerDistance distance() {
        return PlayerDistance.ONE_AWAY;
    }

    @Override
    public boolean apply(Player p) {
        return p.loseCard(context.getPlayer().takeCard(p.getCards()));
    }
}
