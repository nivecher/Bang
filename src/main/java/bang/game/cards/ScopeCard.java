/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bang.game.cards;

import bang.game.TargetDistanceModifier;

/**
 *
 * @author Morgan
 */
public class ScopeCard extends PlayingCard implements TargetDistanceModifier {

    public ScopeCard(Suit suit, Face face) {
        super("Scope", Color.Blue, suit, face);
    }

    /**
     * Reachable distance decreased by 1
     * @return 
     */
    @Override
    public int getDecrease() {
        return 1;
    }
    
}
