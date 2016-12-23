package bang.ui.controller;

import bang.PlayerController;
import bang.game.Ability;
import bang.game.BangGame;
import bang.game.Player;
import bang.game.PlayingContext;
import bang.game.cards.BarrelCard;
import bang.game.cards.MissedCard;
import bang.game.cards.PlayingCard;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by Morgan on 8/20/2016.
 */
public class DefaultPlayerController implements PlayerController {

    private static final Logger LOG = Logger.getLogger(DefaultPlayerController.class.getName());

    private Player player;
    private BangGame game;

    // TODO add advanced selection

    private ISelector<PlayingCard> discardSelector = list -> {
        return list.get(0); // default implementation
    };

    private ISelector<PlayingCard> cardSelector = list -> {
        return list.get(0); // default implementation
    };

    private ISelector<Player> playerSelector = list -> {
        return list.get(0); // default implementation
    };

    public DefaultPlayerController() {
    }

    /**
     * TODO delete this
     *
     * @param player
     * @param game
     * @deprecated use default constructor and setters
     */
    @Deprecated
    public DefaultPlayerController(Player player, BangGame game) {
        this.player = player;
        this.game = game;
    }

    @Override
    public List<PlayingCard> draw() {
        List<PlayingCard> cards = new ArrayList<>();
        while (player.canDraw()) {
            PlayingCard card = player.drawCard(selectDrawPile());
            card.setContext(new PlayingContext(game, player));
            cards.add(card);
        }
        return cards;
    }

    private List<PlayingCard> selectDrawPile() {
        List<PlayingCard> optPile = game.getDrawPile();
        if (!player.hasDrawn()) { // first draw
            if (player.getCharacter().getAbility() == Ability.DRAW_FIRST_FROM_DISCARD) {
                LOG.info(player.getName() + " is drawing from the discard pile");
                optPile = game.getDiscardPile();
            } else if (player.getCharacter().getAbility() == Ability.DRAW_FIRST_FROM_PLAYER) {
                Player p = playerSelector.select(game.getActivePlayers()); // TODO change to opponents
                LOG.info(player.getName() + " is drawing from player: " + p);
                optPile = p.getCards();
            }
        }
        // TODO support discard pile and other's hands
        return optPile;
    }

    @Override
    public void takeTurn() {
        player.startTurn();
        // TODO do something here?

        PlayingCard card = player.drawCard(game.getDrawPile());
        LOG.info("Drew card: " + card);
    }

    @Override
    public Player selectTargetPlayer(List<Player> opponents) {
        return playerSelector.select(opponents);
    }

    @Override
    public PlayingCard selectTargetDiscard(List<PlayingCard> cards) {
        return discardSelector.select(cards);
    }

    @Override
    public PlayingCard discard() {
        PlayingCard card = discardSelector.select(player.getHand());
        player.discardCard(card, game.getDiscardPile());
        return card;
    }

    @Override
    public PlayingCard selectCard(List<PlayingCard> cards) {
        PlayingCard card = cardSelector.select(cards);
        player.accept(card);
        cards.remove(card);
        return card;
    }

    @Override
    public boolean forceDiscard(Class<? extends PlayingCard> clazz) {
        PlayingCard card = PlayingCard.findCard(clazz, player.getHand());

        if (card != null) {
            return player.discardCard(card, game.getDiscardPile());
        }

        card = PlayingCard.findCard(clazz, player.getCards());

        // discard if player has a card of that type
        return card != null && player.discardFromBoard(card, game.getDiscardPile());
    }

    /**
     * Try to avoid a hit (AI)
     *
     * @return true if hit avoided, false if hit
     */
    @Override
    public boolean avoidHit() {
        if (player.getCharacter().getAbility() == Ability.DRAW_ON_BANG_FOR_HEART_TO_MISS) {
            BarrelCard barrel = new BarrelCard();
            if (barrel.play()) {
                return true; // avoided
            }
        }

        // Try all barrels
        if (player.getBarrelsInPlay().stream().peek(b -> b.setContext(new PlayingContext(game, player))).anyMatch(PlayingCard::play)) {
            return true; // avoided
        }

        PlayingCard miss = PlayingCard.findCard(MissedCard.class, player.getHand());
        if (miss != null) {
            return miss.play(); // avoided
        }

        // TODO handle other miss card types

        return false; // hit
    }

    @Override
    public List<PlayingCard> pass() {
        List<PlayingCard> discards = new ArrayList<>();
        // Discard excess
        for (int i = 0; i < player.pass(); i++) {
            discards.add(discard());
        }
        return discards;
    }

    @Override
    public Player getPlayer() {
        return player;
    }

    @Override
    public BangGame getGame() {
        return game;
    }

    @Override
    public void setPlayer(Player player) {
        this.player = player;
    }

    @Override
    public void setGame(BangGame game) {
        this.game = game;
    }

    public void setDiscardSelector(ISelector<PlayingCard> selector) {
        this.discardSelector = selector;
    }

    public void setCardSelector(ISelector<PlayingCard> selector) {
        this.cardSelector = selector;
    }

    public void setPlayerSelector(ISelector<Player> selector) {
        this.playerSelector = selector;
    }

}
