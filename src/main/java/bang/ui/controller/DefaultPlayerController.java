package bang.ui.controller;

import bang.game.Ability;
import bang.game.BangGame;
import bang.game.Player;
import bang.game.cards.PlayingCard;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Morgan on 8/20/2016.
 */
public class DefaultPlayerController implements PlayerController {

    private final Player player;
    private final BangGame game;
    private ISelector<PlayingCard> discardSelector = new ISelector<PlayingCard>() {
        @Override
        public PlayingCard select(List<PlayingCard> list) {
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

    public void play() {
        // TODO add AI
    }

    @Override
    public void takeTurn() {
        player.startTurn();
        // TODO do something here?
    }

    @Override
    public void setDiscardSelector(ISelector<PlayingCard> selector) {
        this.discardSelector = selector;
    }

    @Override
    public PlayingCard discard() {
        PlayingCard card = discardSelector.select(player.getHand());
        player.discardCard(card, game.getDiscardPile());
        return card;
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
