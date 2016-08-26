/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bang.game.cards;

import bang.game.IPlayerEffect;
import bang.game.Player;

import java.util.List;

/**
 * @author Morgan
 */
public class BarrelCard extends PlayingCard implements IPlayerEffect {

    private List<PlayingCard> drawPile;
    private List<PlayingCard> discardPile;

    public BarrelCard(Suit suit, Face face) {
        super("Barrel", Color.Blue, suit, face);
    }

    public BarrelCard() {
        this(null, null);
    }

    public void setDrawPile(List<PlayingCard> drawPile) {
        this.drawPile = drawPile;
    }

    public void setDiscardPile(List<PlayingCard> discardPile) {
        this.discardPile = discardPile;
    }

    /**
     * Returns true if the player drew a heart
     * @param p player to which affect is applied
     * @return
     */
    @Override
    public boolean apply(Player p) {
        PlayingCard draw = p.drawCard(drawPile);
        p.discardCard(draw, discardPile); // flip = draw + discard
        return (draw.getSuit() == Suit.Hearts);
    }

}