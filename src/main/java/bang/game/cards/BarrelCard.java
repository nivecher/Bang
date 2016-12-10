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

    public BarrelCard(Suit suit, Face face) {
        super("Barrel", Color.Blue, suit, face);
    }

    public BarrelCard() {
        this(null, null);
    }

    /**
     * Returns true if the player drew a heart
     * @param p player to which affect is applied
     * @return
     */
    @Override
    public boolean apply(Player p) {
        return (context.flipCard().getSuit() == Suit.Hearts);
    }

}
