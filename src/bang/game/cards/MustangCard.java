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
public class MustangCard extends PlayingCard implements ViewableDistanceModifier {

    public MustangCard(Suit suit, Face face) {
        super("Mustang", Color.Blue, suit, face);
    }

    /**
     * Viewable distance increased by 1
     * @return 
     */
    @Override
    public int getIncrease() {
        return 1;
    }
    
}
