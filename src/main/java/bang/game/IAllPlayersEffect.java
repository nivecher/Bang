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
     * Applys to all players at any distance
     * @return
     */
    @Override
    default PlayerDistance distance() {
        return PlayerDistance.ANY_PLAYER;
    }

    /**
     * Apply effect to all players
     * @param players left in the game
     */
    default void apply(List<Player> players) {
        players.forEach(this::apply);
    }
}
