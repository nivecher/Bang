/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bang;

import bang.game.BangGame;
import bang.game.Player;
import bang.game.cards.PlayingCard;

import java.util.List;

/**
 * Controller's player's decisions
 * @author Morgan
 */
public interface PlayerController {

    void takeTurn();

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
    PlayingCard selectCard(List<PlayingCard> cards);

    // TODO selectCard from player vs. list of cards?

    /**
     * Select the opponent's discard
     * @param cards opponents cards
     * @return selected discard from opponents hand or board
     */
    PlayingCard selectTargetDiscard(List<PlayingCard> cards);

    /**
     * Select player to target for the current play
     * @param opponents list of active opponents
     * @return targetted player or null if cancelled
     */
    Player selectTargetPlayer(List<Player> opponents);

    /**
     * Select a card to discard of a certain type from this player's hand
     * (player's choice due to special abilities or choice to not give up a card and take a hit instead)
     * @param clazz type of card to discard
     * @return true if card discarded, false otherwise (i.e. pass)
     */
    boolean forceDiscard(Class<? extends PlayingCard> clazz);

    /**
     * Attempt to avoid a hit (i.e. bang) from an opponent
     * @return true if avoided
     */
    boolean avoidHit();

    Player getPlayer();

    BangGame getGame();

    void setPlayer(Player player);

    void setGame(BangGame game);
}
