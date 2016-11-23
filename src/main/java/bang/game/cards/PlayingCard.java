/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bang.game.cards;

import bang.game.IPlayerEffect;

import java.util.*;

/**
 * Playing card that can be drawn and played by a player and has some effect(s)
 *
 * @author Morgan
 */
public class PlayingCard {

    /**
     * Card;s name
     */
    private final String name;

    /**
     * Card's color
     */
    private final Color color;

    /**
     * Card's suit
     */
    private final Suit suit;

    /**
     * Card's face (rank or number)
     */
    private final Face face;

    /**
     * Set of effects that this card has in the game
     *
     * @deprecated effects added to cards instead
     */
    @Deprecated
    private final Set<IPlayerEffect> effects = new HashSet<>();

    /**
     * Construct a new playing card
     *
     * @param name  card's name
     * @param color card's color
     * @param suit  card's suit
     * @param face  card's face
     */
    public PlayingCard(String name, Color color, Suit suit, Face face) {
        this.name = name;
        this.color = color;
        this.suit = suit;
        this.face = face;
    }

    /**
     * Return the card's name
     *
     * @return name of the card
     */
    public String getName() {
        return name;
    }

    /**
     * Return the card's color
     *
     * @return color of the Card
     */
    public Color getColor() {
        return color;
    }

    /**
     * Return the card's suit
     *
     * @return suit of the card
     */
    public Suit getSuit() {
        return suit;
    }

    /**
     * Return the card's face
     *
     * @return face (i.e. rank/number) of the card
     */
    public Face getFace() {
        return face;
    }

    /**
     * Return the list of effects this card has
     *
     * @return new list of effects
     * @deprecated effects added to cards instead
     */
    @Deprecated
    public List<IPlayerEffect> getEffects() {
        return new ArrayList<>(effects);
    }

    /**
     * Add an effect to this card's effects
     *
     * @param effect added
     * @return true if effects changed
     * @deprecated effects added to cards instead
     */
    @Deprecated
    protected boolean addEffect(IPlayerEffect effect) {
        return this.effects.add(effect);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 13 * hash + Objects.hashCode(this.name);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final PlayingCard other = (PlayingCard) obj;
        return this == other;
// TODO correct?       return Objects.equals(this.name, other.name) && Objects.equals(this.suit, other.suit) && Objects.equals(this.face, other.face);
    }


}
