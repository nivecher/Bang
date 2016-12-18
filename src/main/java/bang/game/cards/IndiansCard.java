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
public class IndiansCard extends PlayingCard implements IAllPlayersEffect {
    public IndiansCard(Suit suit, Face face) {
        super("Indians!", Color.Brown, suit, face);
    }

    @Override
    public boolean apply(Player p) {
        if (!p.forceDiscard(BangCard.class, context.getDiscardPile())) {
            p.loseLife();
            return true; // hit
        }
        return false; // miss
    }

}
