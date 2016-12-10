package bang.game.cards;

import java.util.List;

/**
 * Created by Morgan on 11/22/2016.
 */
public class StagecoachCard extends PlayingCard {
    public StagecoachCard(Suit suit, Face face) {
        super("Stagecoach", Color.Brown, suit, face);
    }

    public boolean play() {
        List<PlayingCard> drawPile = context.getDrawPile();
        context.getPlayer().drawCard(drawPile);
        context.getPlayer().drawCard(drawPile);
        return true;
    }
}
