package bang.game.cards;

import bang.game.IPlayerEffect;
import bang.game.Player;

import java.util.EnumSet;
import java.util.List;

/**
 * Created by Morgan on 11/22/2016.
 */
public class DynamiteCard extends PlayingCard implements IPlayerEffect {
    public DynamiteCard(Suit suit, Face face) {
        super("Dynamite", Color.Blue, suit, face);
    }

    @Override
    public boolean apply(Player p) {
        PlayingCard draw = context.flipCard();

        boolean hit = (draw.getSuit() == Suit.Spades &&
                EnumSet.range(Face.Two, Face.Nine).contains(draw.getFace()));

        if (hit) {
            p.loseLife();
            p.loseLife();
            p.loseLife();
        }

        return hit;
    }
}
