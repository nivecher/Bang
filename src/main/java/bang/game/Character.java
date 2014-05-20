/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bang.game;

/**
 * Western character with a special ability
 *
 * @author Morgan
 */
public class Character implements ViewableDistanceModifier, ReachableDistanceModifier {

    private final String name;
    private final Ability ability;
    private final int numBullets;

    public Character(String name, Ability ability, int numBullets) {
        this.name = name;
        this.ability = ability;
        this.numBullets = numBullets;
    }

    public String getName() {
        return name;
    }

    public Ability getAbility() {
        return ability;
    }

    public int getNumBullets() {
        return numBullets;
    }

    @Override
    public int getIncrease() {
        if (ability == Ability.SEEN_AT_DISTANCE_PLUS_ONE) {
            return 1;
        }
        return 0;
    }

    @Override
    public int getDecrease() {
        if (ability == Ability.SEES_AT_DISTANCE_MINUS_ONE) {
            return 1;
        }
        return 0;
    }

}
