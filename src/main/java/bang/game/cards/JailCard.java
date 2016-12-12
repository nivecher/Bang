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
     * Play the jail card and remain in jail (i.e. skip turn) unless a heart is drawn
     * @return true if player remains in jail
     */
    public boolean play() {
        return (context.flipCard().getSuit() != Suit.Hearts);
    }

    /**
     * Draws a card from the draw pile and
     * @param p player being put in jail
     */
    @Override
    public boolean apply(Player p) { // draw a card
        p.accept(this);
        return true; // player is in jail
    }
    
}
