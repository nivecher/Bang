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

    private static final Logger logger = Logger.getLogger(DefaultPlayerController.class.getName());

    private final Player player;
    private final BangGame game;
    private ISelector<PlayingCard> discardSelector = new ISelector<PlayingCard>() {
        @Override
        public PlayingCard select(List<PlayingCard> list) {
            return list.get(0); // default implementation
        }
    };

    private ISelector<Player> playerSelector = new ISelector<Player>() {
        @Override
        public Player select(List<Player> list) {
            return list.get(0); // default implementation
        }
    };

    public DefaultPlayerController(Player player, BangGame game) {
        this.player = player;
        this.game = game;
    }

    @Override
    public List<PlayingCard> draw() {
        List<PlayingCard> cards = new ArrayList<>();
        while (player.canDraw()) {
            cards.add(player.drawCard(selectDrawPile()));
        }
        return cards;
    }

    private List<PlayingCard> selectDrawPile() {
        List<PlayingCard> optPile = null;
        if (!player.hasDrawn()) { // first draw
            if (player.getCharacter().getAbility() == Ability.DRAW_FIRST_FROM_DISCARD) {
                optPile = game.getDiscardPile();
            } else if (player.getCharacter().getAbility() == Ability.DRAW_FIRST_FROM_PLAYER) {
                // TODO select player
                optPile = game.getPlayers().get(0).getCards();
            }
        }
        // TODO support discard pile and other's hands
        return game.getDrawPile();
    }

    @Override
    public void takeTurn() {
        player.startTurn();
        // TODO do something here?

        PlayingCard card = player.drawCard(game.getDrawPile());
        logger.info("Drew card: " + card);
    }

    @Override
    public void setDiscardSelector(ISelector<PlayingCard> selector) {
        this.discardSelector = selector;
    }

    @Override
    public void setPlayerSelector(ISelector<Player> selector) {
        this.playerSelector = selector;
    }

    @Override
    public PlayingCard discard() {
        PlayingCard card = discardSelector.select(player.getHand());
        player.discardCard(card, game.getDiscardPile());
        return card;
    }

    @Override
    public boolean avoidHit() {
        if (player.getCharacter().getAbility() == Ability.DRAW_ON_BANG_FOR_HEART_TO_MISS) {
            BarrelCard barrel = new BarrelCard();
            if(barrel.apply(player)) {
                return true; // avoided
            }
        }

        // Try all barrels
        if (player.getBarrels().stream().peek(b -> {
                b.setContext(new PlayingContext(game, player));
            }).anyMatch(b -> b.play())) {
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
}
