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
 * @author Morgan
 */
public class DuelCard extends PlayingCard implements IPlayerEffect {

    public DuelCard(Suit suit, Face face) {
        super("Duel", Color.Brown, suit, face);
    }

    @Override
    public PlayerDistance distance() {
        return PlayerDistance.ANY_PLAYER;
    }

    @Override
    public boolean apply(Player p) {

        boolean opponentHasBang;
        boolean playerHasBang;

        do {
            opponentHasBang = p.forceDiscard(BangCard.class, context.getDiscardPile());
            if (opponentHasBang) {
                playerHasBang = context.getPlayer().forceDiscard(BangCard.class, context.getDiscardPile());
            } else {
                p.loseLife();
                return true; // opponent hit
            }
        } while (opponentHasBang && playerHasBang);

        context.getPlayer().loseLife();
        return false; // player hit
    }
}
