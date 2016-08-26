/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bang.game.cards;

import bang.game.IAllPlayersEffect;
import bang.game.Player;
import java.util.List;

/**
 *
 * @author Morgan
 */
public class GatlingCard extends PlayingCard implements IAllPlayersEffect {

    private List<PlayingCard> drawPile;
    private List<PlayingCard> discardPile;

    public GatlingCard(Suit suit, Face face) {
        super("Gatling", Color.Brown, suit, face);
    }

    public void setDrawPile(List<PlayingCard> drawPile) {
        this.drawPile = drawPile;
    }

    public void setDiscardPile(List<PlayingCard> discardPile) {
        this.discardPile = discardPile;
    }

    @Override
    public boolean apply(Player p) {
        if (!p.getBarrels().stream().map(b -> {
            b.setDrawPile(drawPile);
            b.setDrawPile(discardPile);
            return b;
        }).noneMatch(b -> b.apply(p))) {
            return false; // TODO verify this behavior
        }
        // TODO play missed card?
        return p.bang();
    }
}