package bang.ui.controller;

import bang.PlayerController;
import bang.game.Player;
import bang.game.cards.PlayingCard;

import java.util.List;

/**
 * Created by Morgan on 11/23/2016.
 */
public class CommandLinePlayerController implements PlayerController {
    @Override
    public void takeTurn() {
        System.out.println("It's your turn.  Please draw a card");
        // TODO
    }

    @Override
    public void setDiscardSelector(ISelector<PlayingCard> selector) {

    }

    @Override
    public void setCardSelector(ISelector<PlayingCard> selector) {

    }

    @Override
    public void setPlayerSelector(ISelector<Player> selector) {

    }

    @Override
    public List<PlayingCard> draw() {
        return null;
    }

    @Override
    public List<PlayingCard> pass() {
        return null;
    }

    @Override
    public PlayingCard discard() {
        return null;
    }

    @Override
    public PlayingCard select(List<PlayingCard> cards) {
        return null;
    }

    @Override
    public boolean forceDiscard(Class<? extends PlayingCard> clazz) {
        return false;
    }

    @Override
    public boolean avoidHit() {
        return false; // TODO implement
    }
}
