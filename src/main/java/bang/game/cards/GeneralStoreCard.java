/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bang.game.cards;

import bang.game.IAllPlayersEffect;
import bang.game.Player;

import java.util.ArrayList;
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

    @Override
    public boolean play() {
        cards = new ArrayList<>();
        // flip a car from
        for (int i = 0; i <  context.getActivePlayers().size(); i++) {
            cards.add(context.getDrawPile().remove(0));
        }
        return super.play();
    }

    @Override
    public boolean apply(Player p) {
        return p.takeCard(cards) != null;
    }

}