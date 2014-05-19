/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bang.game;

import java.util.List;

/**
 * Applies player effect to all players
 * @author Morgan
 */
public interface IAllPlayersEffect extends IPlayerEffect {

    /**
     * Apply effect to all players
     * @param players left in the game
     */
    default void apply(List<Player> players) {
        players.stream().forEach((p) -> {
            this.apply(p);
        });
    }
}
