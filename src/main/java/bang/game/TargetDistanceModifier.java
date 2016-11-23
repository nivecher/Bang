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
public interface TargetDistanceModifier {
    
    /**
     * Get the decrease in distance to target other players
     * @return positive number representing decrease in distance to target others
     */
    int getDecrease();
    
}
