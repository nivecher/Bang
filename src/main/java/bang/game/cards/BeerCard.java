/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bang.game.cards;

import bang.game.IPlayerEffect;
import bang.game.Player;

/**
 * Beer card that has the effect of regaining a life point
 * @author Morgan
 */
public class BeerCard extends PlayingCard implements IPlayerEffect {

    public BeerCard(Suit suit, Face face) {
        super("Beer", Color.Brown, suit, face);
    }

    @Override
    public boolean apply(Player p) {
        return p.regainLife();
    }
    
}
