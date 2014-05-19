/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bang.game;

import java.util.function.Consumer;

/**
 *
 * @author Morgan
 */
public interface IPlayerEffect /*TODO extends Consumer<Player>*/ {
    
    /**
     * Apply the affect to the specified player
     * @param p 
     * @return true if effect applied successfully, false otherwise
     */
    boolean apply(Player p);
}
