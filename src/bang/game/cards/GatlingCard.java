/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bang.game.cards;

import bang.game.IAllPlayersEffect;
import bang.game.Player;

/**
 *
 * @author Morgan
 */
public class GatlingCard extends PlayingCard implements IAllPlayersEffect {
    public GatlingCard(Suit suit, Face face) {
        super("Gatling", Color.Brown, suit, face);
    }
    
    public boolean apply(Player p) {
        boolean hit = true; // TODO apply strength to gatling card
        // Based on the strength of the bang effect, player needs so many misses
        // otherwise the player is hit
//        for (int i = 0; i < this.strength; i++) {
            if (p.hitOrMiss()) {
                hit = false;
            } else {
                hit = true;
//                break; // do not try again if hit
            }
//        }
        return hit;
    }
}
