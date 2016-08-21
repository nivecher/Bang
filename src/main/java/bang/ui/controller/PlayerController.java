/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bang.ui.controller;

import bang.game.cards.PlayingCard;

import java.util.List;

/**
 *
 * @author Morgan
 */
public interface PlayerController {

    void takeTurn();

    void setDiscardSelector(ISelector<PlayingCard> selector);

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
}
