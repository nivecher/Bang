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
 * Jail card
 * @author Morgan
 */
public class JailCard extends PlayingCard implements IPlayerEffect {
    
    public JailCard(Suit suit, Face face) {
        super("Jail", Color.Blue, suit, face);
    }

    /**
     *
     * @return true if player remains in jail
     */
    public boolean play() {
        boolean heart = (context.flipCard().getSuit() == Suit.Hearts);
        // TODO handle ending turn?
        return !heart; // remain in jail (skip turn) unless a heart is drawn
    }

    /**
     * Draws a card from the draw pile and
     * @param p player being put in jail
     * @return true if the player remains in jail
     */
    @Override
    public boolean apply(Player p) { // draw a card
        return p.acceptCard(this);
    }
    
}
