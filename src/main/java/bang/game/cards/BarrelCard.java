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
public class BarrelCard extends PlayingCard {

    public BarrelCard(Suit suit, Face face) {
        super("Barrel", Color.Blue, suit, face);
    }

    public BarrelCard() {
        this(null, null);
    }

    /**
     * Play the barrel card by flipping a card to see if it is a heart
     * @return true if a heart is flipped
     */
    public boolean play() {
        return (context.flipCard().getSuit() == Suit.Hearts);
    }

}
