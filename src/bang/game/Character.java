/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bang.game;

/**
 * Western character with a special ability
 * @author Morgan
 */
public class Character {
    private final String name;
    private final Ability abiliity;
    private final int numBullets;

    public Character (String name, Ability ability, int numBullets) {
        this.name = name;
        this.abiliity = ability;
        this.numBullets = numBullets;
    }
    
    public String getName() {
        return name;
    }

    public Ability getAbiliity() {
        return abiliity;
    }

    public int getNumBullets() {
        return numBullets;
    }
    

    
}
