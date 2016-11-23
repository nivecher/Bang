/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bang.game.cards;

import bang.game.IAllPlayersEffect;
import bang.game.Player;

import java.util.List;

/**
 * General store
 * @author Morgan
 */
public class GeneralStoreCard extends PlayingCard implements IAllPlayersEffect {

    private List<PlayingCard> cards;

    public GeneralStoreCard(Suit suit, Face face) {
        super("General Store", Color.Brown, suit, face);
    }

    public void setCards(List<PlayingCard> cards) {
        this.cards = cards;
    }

    @Override
    public boolean apply(Player p) {
        p.drawCard(cards);
        return true;
    }

}