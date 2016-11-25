package bang.game;

import bang.game.cards.PlayingCard;
import bang.ui.controller.ISelector;

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

    private ISelector<PlayingCard> cardSelector;

    private ISelector<Player> playerSelector;

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

    public ISelector<PlayingCard> getCardSelector() {
        return cardSelector;
    }

    public ISelector<Player> getPlayerSelector() {
        return playerSelector;
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
     * Draws a and discards a card
     * @return card drawn / discarded
     */
    public PlayingCard flipCard() {
        PlayingCard card = drawPile.remove(0);
        discardPile.add(card);
        return card;
    }
}

