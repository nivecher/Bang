/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bang.game.cards;

import bang.game.IAllPlayersEffect;
import bang.game.IPlayerEffect;
import bang.game.Player;
import bang.game.PlayingContext;
import bang.ui.controller.ISelector;

import java.util.Collection;

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
     * Context when played
     */
    protected PlayingContext context;

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
     * Finds a card of a certain class
     * @param clazz type of card to find
     * @param cards list of cards to search
     * @param <C> any PlayingCard
     * @return first card found with class clazz or null if not found
     */
    public static <C extends PlayingCard> C findCard(Class<C> clazz, Collection<PlayingCard> cards) {
        return (C) cards.stream().filter(clazz::isInstance).findAny().orElse(null);
    }

    /**
     * Sets the playing context
     *
     * @param context
     */
    public void setContext(PlayingContext context) {
        this.context = context;
    }

    /**
     * Plays the card within the established context
     *
     * @return true if the play accomplished its intended goal, false otherwise (i.e. discarded)
     * @throws NullPointerException if context is null
     */
    public boolean play() {
        if (this instanceof IAllPlayersEffect) {
            ((IAllPlayersEffect) this).apply(context.getActivePlayers());
            return true;
        } else if (this instanceof IPlayerEffect) {
            ISelector<Player> playerSelector = context.getPlayerSelector();
            return ((IPlayerEffect) this).apply(playerSelector.select(context.getActivePlayers()));
        } else if (color == Color.Blue) {
            // TODO DodgeCity: support green cards
            Player player = context.getPlayer();
            return player.playCardOnBoard(this);
        } else {
            context.getPlayer().discardCard(this, context.getDiscardPile());
            return false; // not played, discarded
        }
    }
}
