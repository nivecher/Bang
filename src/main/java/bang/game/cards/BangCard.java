/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bang.game.cards;

import bang.game.Player;
import bang.game.IPlayerEffect;

/**
 * BANG! card is played to reduce other players' life points
 * @author Morgan
 */
public class BangCard extends PlayingCard implements IPlayerEffect {
    
    private int strength = 1; // num misses required to counteract

    public BangCard(Suit suit, Face face) {
        super("BANG!", Color.Brown, suit, face);
    }

    /**
     * Returns the strength of the effect
     * @return number of misses required to counteract the effect
     */
    public int getStrength() {
        return strength;
    }

    /**
     * Change the strength of the effect
     * @param strength number of misses required to counteract the effect
     */
    public void setStrength(int strength) {
        this.strength = strength;
    }
    
    /**
     * Remove one life point from the specified player
     * @param p player affected
     * @return true if player hit, false if player missed
     */
    @Override
    public boolean apply(Player p) {
        boolean hit = true;
        // Based on the strength of the bang effect, player needs so many misses
        // otherwise the player is hit
        for (int i = 0; i < this.strength; i++) {
            if (p.hitOrMiss()) {
                hit = false;
            } else {
                hit = true;
                break; // do not try again if hit
            }
        }
        return hit;
    }
}
