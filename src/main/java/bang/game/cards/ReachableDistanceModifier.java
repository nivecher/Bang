/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bang.game.cards;

/**
 *
 * @author Morgan
 */
public interface ReachableDistanceModifier {
    
    /**
     * Get the decrease in distance to reach other players
     * @return positive number representing decrease in distance to reach others
     */
    int getDecrease();
    
}
