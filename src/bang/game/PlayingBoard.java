/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bang.game;

import bang.game.cards.ViewableDistanceModifier;
import bang.game.cards.PlayingCard;
import bang.game.cards.ReachableDistanceModifier;
import bang.game.cards.WeaponCard;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

/**
 * Player's board where cards are played. Each player has a Colt .45 weapon by
 * default. A WeaponCard can be added to the board to replace the weapon card.
 *
 * @author Morgan
 */
public class PlayingBoard implements Consumer<PlayingCard> {

    /**
     * Cards played on the board. Only one of each type can be played.
     */
    private final Set<PlayingCard> objectCards = new HashSet<>();
    
    /**
     * Cards placed on the board that have an effect on the player
     * (perhaps on the next turn).
     */
    private final Set<PlayingCard> effectCards = new HashSet<>();

    /**
     * Optional weapon added to replace the Colt .45
     */
    private WeaponCard weapon;

    /**
     * Add the playing card to the playing board
     *
     * @param card PlayingCard to Add
     * @return true if board changed
     * @throws IllegalArgumentException if card is an effect card
     */
    public boolean addCard(PlayingCard card) {
        if (card instanceof WeaponCard) {
            setWeapon((WeaponCard) card);
        } else if (card instanceof IPlayerEffect) {
            return effectCards.add(card);
        }
        return objectCards.add(card); // object card
    }

    /**
     * Return the change in reach based on cards on the playing board
     * @return positive number representing the decrease in reachable distance
     */
    public int reachableDistDelta() {
        return objectCards.stream().filter(c -> c instanceof ReachableDistanceModifier).
                mapToInt(c -> {
                    return ((ReachableDistanceModifier) c).getDecrease();
                }).sum();
    }

    /**
     * Return the change in viewable distance based on cards on the playing board
     * @return positive number representing the increase in viewable distance
     */
    public int viewableDistanceDelta() {
        return objectCards.stream().filter(c -> c instanceof ViewableDistanceModifier).
                mapToInt(c -> {
                    return ((ViewableDistanceModifier) c).getIncrease();
                }).sum();
    }

    /**
     * Remove the playing card from the playing board. If the card is the
     * weapon, the weapon is reset
     *
     * @param card PlayingCard to remove
     * @return true if board contained the card
     */
    public boolean removeCard(PlayingCard card) {
        if (card.equals(this.weapon)) {
            this.weapon = null;
        }
        return objectCards.remove(card);
    }

    /**
     * Returns the player's weapon.
     *
     * @return WeaponCard added or the default weapon (a Colt .45)
     */
    public WeaponCard getWeapon() {
        if (weapon == null) {
            return new WeaponCard("Colt .45", 1);
        }

        return weapon;
    }

    /**
     * Sets the weapon to the specified weapon card and discards the previous
     * weapon card (if any)
     *
     * @param weapon new weapon
     */
    protected void setWeapon(WeaponCard weapon) {
        if (this.weapon != null) {
            objectCards.remove(this.weapon); // discard previous weapon
        }

        this.weapon = weapon;
    }

    /**
     * Reset the playing board back to its original state.
     */
    public void reset() {
        this.weapon = null;
        this.objectCards.clear();
        this.effectCards.clear();
    }

    @Override
    public void accept(PlayingCard c) {
        addCard(c);
    }
}
