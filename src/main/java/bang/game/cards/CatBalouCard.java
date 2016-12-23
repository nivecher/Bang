/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bang.game.cards;

import bang.game.IPlayerEffect;
import bang.game.Player;
import bang.game.PlayerDistance;

/**
 * 
 * @author Morgan-
 */
public class CatBalouCard extends PlayingCard implements IPlayerEffect {
    
    public CatBalouCard(Suit suit, Face face) {
        super("Cat Balou", Color.Brown, suit, face);
    }

    @Override
    public PlayerDistance distance() {
        return PlayerDistance.ANY_PLAYER;
    }

    @Override
    public boolean apply(Player p) {
        PlayingCard card = context.getTargetDiscard(p.getCards());
        return p.discardCard(card, context.getDiscardPile());
    }

}
