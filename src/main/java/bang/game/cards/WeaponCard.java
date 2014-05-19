/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bang.game.cards;

/**
 * Weapon playing card
 * @author Morgan
 */
public class WeaponCard extends PlayingCard {

    /**
     * Weapon's reachable distance
     */
    private final int distance;
    
    /**
     * Construct a new weapon card weapon card with a suit and face
     * @param name
     * @param distance
     * @param suit
     * @param face 
     */
    public WeaponCard(String name, int distance, Suit suit, Face face) {
        super(name, Color.Blue, suit, face);
        this.distance = distance;
    }
    
    /**
     * Construct a new weapon card without a suit or face
     * @param name
     * @param distance 
     */
    public WeaponCard(String name, int distance) {
        this(name, distance, null, null);
    }

    /**
     * Return weapon's maximum reachable distance
     * @return positive int representing the weapon's reach
     */
    public int getDistance() {
        return distance;
    }
    
}
