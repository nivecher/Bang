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
 * 
 * @author Morgan
 */
public class CatBalouCard extends PlayingCard implements IPlayerEffect {
    
    public CatBalouCard(Suit suit, Face face) {
        super("Cat Balou", Color.Brown, suit, face);
    }

    private List<PlayingCard> discardPile;
    
    public void setDiscardPile(List<PlayingCard> discardPile) {
        this.discardPile = discardPile;
    }
    
    @Override
    public boolean apply(Player p) {
        return p.discardCard(p.selectDiscard(), discardPile);
    }

}
