/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bang.ui.controller;

import bang.game.cards.PlayingCard;

/**
 *
 * @author Morgan
 */
public interface PlayerController {
    
    PlayingCard selectDiscard();
    
    PlayingCard drawCard();
    
}
