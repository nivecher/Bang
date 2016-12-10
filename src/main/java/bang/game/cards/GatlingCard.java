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
public class GatlingCard extends PlayingCard implements IAllPlayersEffect {

    public GatlingCard(Suit suit, Face face) {
        super("Gatling", Color.Brown, suit, face);
    }

    @Override
    public boolean play() {
        for (Player player : context.getActiveOpponents()) {
            apply(player);
        }
        return true;
    }

    @Override
    public boolean apply(Player p) {
        return p.bang();
    }
}
