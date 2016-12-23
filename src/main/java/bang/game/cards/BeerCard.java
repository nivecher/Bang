/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bang.game.cards;

/**
 * Beer card that has the effect of regaining a life point
 * @author Morgan
 */
public class BeerCard extends PlayingCard {

    public BeerCard(Suit suit, Face face) {
        super("Beer", Color.Brown, suit, face);
    }

    /**
     * Player regains one life point
     * @param p player to which affect is applied
     * @return true if player regained a life point
     */
    @Override
    public boolean play() {
        int activePlayers = context.getActivePlayers().size();
        // beer has no effect when 2 players are left
        return activePlayers > 2 && context.getPlayer().regainLife();
    }
    
}
