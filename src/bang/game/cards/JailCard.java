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
 *
 * @author Morgan
 */
public class JailCard extends PlayingCard implements IPlayerEffect {
    
    private List<PlayingCard> drawPile;
    private List<PlayingCard> discardPile;
    
    public JailCard(Suit suit, Face face) {
        super("Jail", Color.Blue, suit, face);
    }

    public void setDrawPile(List<PlayingCard> drawPile) {
        this.drawPile = drawPile;
    }

    public void setDiscardPile(List<PlayingCard> discardPile) {
        this.discardPile = discardPile;
    }
    
    /**
     * Draws a card from the draw pile and
     * @param p player being put in jail
     * @return true if the player remains in jail
     */
    @Override
    public boolean apply(Player p) { // draw a card
        boolean heart = (p.drawCard(drawPile).getSuit() == Suit.Hearts);
        p.discardCard(this, discardPile);
        return !heart; // remain in jail (skip turn) unless a heart is drawn
    }
    
}
