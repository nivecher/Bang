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
public interface ViewableDistanceModifier {
    
    /**
     * Get the increase in distance seen by other players
     * @return positive number representing increased viewable distance
     */
    int getIncrease();
}
