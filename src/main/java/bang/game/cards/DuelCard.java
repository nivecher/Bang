/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bang.game.cards;

import bang.game.IPlayerEffect;
import bang.game.Player;

/**
 *
 * @author Morgan
 */
public class DuelCard extends PlayingCard implements IPlayerEffect {

    public DuelCard(Suit suit, Face face) {
        super("Duel", Color.Brown, suit, face);
    }

    @Override
    public boolean apply(Player p) {

        boolean opponentHasBang = false;
        boolean playerHasBang = false;

        do {
            opponentHasBang = p.forceDiscard(BangCard.class, context.getDiscardPile());
            if (opponentHasBang) {
                playerHasBang = context.getPlayer().forceDiscard(BangCard.class, context.getDiscardPile());
            } else {
                return true; // opponent beaten
            }
        } while (opponentHasBang && playerHasBang);

        return false; // player beaten
    }
}
