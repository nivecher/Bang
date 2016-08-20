/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bang.game.cards;

import bang.game.IPlayerEffect;
import bang.game.Player;
import bang.ui.controller.ISelector;
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
    private ISelector<PlayingCard> selector;
    
    public void setDiscardPile(List<PlayingCard> discardPile) {
        this.discardPile = discardPile;
    }
    
    public void setCardSelector(ISelector<PlayingCard> selector) {
        this.selector = selector;
    }
    
    @Override
    public boolean apply(Player p) {
        return p.discardCard(selector.select(p.getCards()), discardPile);
    }

}
