/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bang.game;

/**
 *
 * @author Morgan
 */
public interface IPlayerEffect {

    /**
     * Returns any distance restrictions on the affect
     */
    PlayerDistance distance();

    /**
     * Apply the affect to the specified player
     * @param p player to which affect is applied
     * @return true if effect applied successfully, false otherwise
     */
    boolean apply(Player p);
}
