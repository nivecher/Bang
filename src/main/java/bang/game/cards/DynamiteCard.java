package bang.game.cards;

import java.util.EnumSet;
import java.util.stream.IntStream;

/**
 * Created by Morgan on 11/22/2016.
 */
public class DynamiteCard extends PlayingCard implements ITurnEffect {

    public DynamiteCard(Suit suit, Face face) {
        super("Dynamite", Color.Blue, suit, face);
    }

    @Override
    public int compareTo(ITurnEffect o) {
        return -1; // Dynamite has the highest precedence (lowest number)
    }

    @Override
    public boolean play() {
        context.getPlayer().accept(this);
        return true;
    }

    /**
     * @return
     */
    @Override
    public boolean onTurn() {

        PlayingCard draw = context.flipCard();

        boolean hit = (draw.getSuit() == Suit.Spades &&
                EnumSet.range(Face.Two, Face.Nine).contains(draw.getFace()));

        if (hit) {
            IntStream.rangeClosed(1,3).forEach(i -> context.getPlayer().loseLife());
            context.getPlayer().discardCard(this, context.getDiscardPile());
        } else {
            context.getNextPlayer().accept(this); // pass to next player
            context.getPlayer().loseCard(this);
        }

        return hit;
    }

}
