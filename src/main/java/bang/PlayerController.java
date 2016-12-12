/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bang;

import bang.game.Player;
import bang.game.cards.PlayingCard;
import bang.ui.controller.ISelector;

import java.util.List;

/**
 *
 * @author Morgan
 */
public interface PlayerController {

    void takeTurn();

    // TODO remove these selectors

    void setDiscardSelector(ISelector<PlayingCard> selector);

    void setCardSelector(ISelector<PlayingCard> selector);

    void setPlayerSelector(ISelector<Player> selector);

    /**
     * Draw the appropriate number of cards to start phase 1 of turn
     * @return list of cards drawn
     */
    List<PlayingCard> draw();

    /**
     * Pass play and discard excess cards
     * @return list of discards or empty list if none
     */
    List<PlayingCard> pass();

    /**
     * Select and discard a card
     * @return card discarded
     */
    PlayingCard discard();

    /**
     * Select and keep a card from the list of cards
     * @param cards list of cards from which card is selected
     * @return selected card
     */
    PlayingCard select(List<PlayingCard> cards);

    /**
     * Select a card to discard of a certain type
     * @param clazz type of card to discard
     * @return true if card discarded, false otherwise (i.e. pass)
     */
    boolean forceDiscard(Class<? extends PlayingCard> clazz);

    /**
     * Attempt to avoid a hit (i.e. bang) from an opponent
     * @return true if avoided
     */
    boolean avoidHit();
}
