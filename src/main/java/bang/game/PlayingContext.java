package bang.game;

import bang.game.cards.PlayingCard;

import javax.swing.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Establishes the context for a given play
 * Created by Morgan on 11/23/2016.
 */
public class PlayingContext {

    /**
     * Reference to the game
     */
    private final BangGame game;

    /**
     * Current player
     */
    private final Player player;

    private List<PlayingCard> drawPile;

    private List<PlayingCard> discardPile;

    protected BangGame getGame() {
        return game;
    }

    public Player getPlayer() {
        return player;
    }

    public PlayingContext(BangGame game, Player player) {
        this.game = game;
        this.player = player;
        this.discardPile = game.getDiscardPile();
        this.drawPile = game.getDrawPile();
    }

    /**
     * List the active opponents
     * @return list of active players not including the current player
     */
    public List<Player> getActiveOpponents() {
        return game.getActivePlayers().stream().filter(p -> !p.equals(player)).collect(Collectors.toList());
    }

    /**
     * List of active opponents given a distance restriction
     * @return list of active players within the distance restriction of the current player
     */
    public List<Player> getActiveOpponents(PlayerDistance distance) {
        switch (distance) {
            case ANY_PLAYER:
                return getActiveOpponents();
            case REACHABLE:
                return getReachableOpponents();
            case ONE_AWAY:
                return getNeighboringOpponents();
        }
        throw new IllegalArgumentException("Invalid distance: " + distance);
    }

    /**
     * Returns this list of reachable (active) opponents
     * @return list of reachable opponants or empty list if none
     */
    public List<Player> getReachableOpponents() {
        return getActiveOpponents().stream().filter(p -> game.canReach(player, p)).collect(Collectors.toList());
    }

    /**
     * Returns this list of (active) opponents that are one away from this player
     * @return list of neighboring opponents or empty list if none
     */
    public List<Player> getNeighboringOpponents() {
        return getActiveOpponents().stream().filter(p -> game.getPositionDistance(player, p) == 1).collect(Collectors.toList());
    }

    public List<PlayingCard> getDiscardPile() {
        return  discardPile;
    }

    public void setDiscardPile(List<PlayingCard> discardPile) {
        this.discardPile = discardPile;
    }

    public List<PlayingCard> getDrawPile() {
        return drawPile;
    }

    public void setDrawPile(List<PlayingCard> drawPile) {
        this.drawPile = drawPile;
    }

    public List<Player> getActivePlayers() {
        return game.getActivePlayers();
    }

    /**
     * Select a target player
     * @return
     */
    public Player getTargetPlayer(PlayerDistance targetting) {
        return player.getController().selectTargetPlayer(getActiveOpponents(targetting));
    }

    public PlayingCard getTargetDiscard(List<PlayingCard> cards) {
        return player.getController().selectTargetDiscard(cards);
    }

    /**
     * Draws a and discards a card
     * @return card drawn / discarded
     */
    public PlayingCard flipCard() {
        PlayingCard card = drawPile.remove(0);
        discardPile.add(card);
        return card;
    }

    /**
     * Returns the active player whose turn is next
     * @return next player
     */
    public Player getNextPlayer() {
        List<Player> active = game.getActivePlayers();
        int next = (active.indexOf(player) + 1) % active.size();
        return active.get(next);
    }
}

