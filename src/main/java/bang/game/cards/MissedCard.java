/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bang.game.cards;

/**
 *
 * @author Morgan
 */
public class MissedCard extends PlayingCard {
    
    public MissedCard(Suit suit, Face face) {
        super("Missed!", Color.Brown, suit, face);
    }
}
