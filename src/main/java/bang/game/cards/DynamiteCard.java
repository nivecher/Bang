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

    private List<PlayingCard> drawPile;
    private List<PlayingCard> discardPile;

    public void setDrawPile(List<PlayingCard> drawPile) {
        this.drawPile = drawPile;
    }

    public void setDiscardPile(List<PlayingCard> discardPile) {
        this.discardPile = discardPile;
    }

    @Override
    public boolean apply(Player p) {
        PlayingCard draw = p.drawCard(drawPile);
        p.discardCard(draw, discardPile); // flip = draw + discard

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
