/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bang.game;

import bang.game.cards.PlayingCard;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * BANG! is a shootout game, in Spaghetti Western style, between a group of
 * Outlaws and the Sheriff, who is their primary target. The Deputies incognitos
 * help the Sheriff, but there is also a Renegade pursuing his own goal! In
 * BANG! each player plays one of these roles, and represents a famous Wild West
 * inspired character.
 *
 * @author Morgan
 */
public class BangGame implements StandardGame {

    /**
     * Minimum number of players in the game
     */
    public static final int MIN_PLAYERS = 4;

    /**
     * Maximum number of players in the game
     */
    public static final int MAX_PLAYERS = 7;

    /**
     * List of players
     */
    private final List<Player> players;

    /**
     * Player card draw pile
     */
    private List<PlayingCard> drawPile;

    /**
     * Player card discard pile
     */
    private final List<PlayingCard> discardPile;

    /**
     * Construct a new game with the specified number of players
     *
     * @param numPlayers number of players
     */
    public BangGame(int numPlayers) {

        if (numPlayers < MIN_PLAYERS || numPlayers > MAX_PLAYERS) {
            throw new IllegalArgumentException("Number of players must be "
                    + "[" + MIN_PLAYERS + " - " + MAX_PLAYERS + "]");
        }

        drawPile = new ArrayList<>();
        discardPile = new ArrayList<>();

        players = new ArrayList<>(numPlayers);
        players.add(new Player(Role.Sheriff));
        players.add(new Player(Role.Renegade));
        players.add(new Player(Role.Outlaw));
        players.add(new Player(Role.Outlaw));
        if (players.size() < numPlayers) { // 5 players
            players.add(new Player(Role.Deputy));
        }
        if (players.size() < numPlayers) { // 6 players
            players.add(new Player(Role.Outlaw));
        }
        if (players.size() < numPlayers) { // 7 players
            players.add(new Player(Role.Deputy));
        }
    }

    /**
     * Reset and setup the game
     */
    public void setup() {
        List<Character> characters = buildCharacterList();
        drawPile = buildPlayingDeck();
        discardPile.clear();

        Collections.shuffle(characters);
        Collections.shuffle(players);
        Collections.shuffle(drawPile);

        players.stream().forEach((p) -> {
            p.setCharacter(characters.remove(0));
            p.drawCard(drawPile);
        });

    }

    /**
     * Players in the game (does not contain eliminated players)
     *
     * @return players left in the game
     */
    public List<Player> getPlayers() {
        return new ArrayList<>(players);
    }

    /**
     * Returns the distance of player 2 from player 1
     *
     * @param p1 player 1
     * @param p2 player 2
     * @return relative distance (by position) of player 2 from player 1
     * representing the shortest distance between the two players (clockwise or
     * counter-clockwise); zero if player 1 is the same as player 2
     *
     * See Figure below for distances from player A (seven players shown) A,A ->
     * 0; A,B -> 1; A,C -> 2; A,D -> 3; A,E -> 3; A,F -> 2; A,G -> 1
     *
     * D_ _E ^ C / \ F ^ | | | | \ B \ _ _ / G / A
     */
    public int getPositionDistance(Player p1, Player p2) {
        int index1 = players.indexOf(p1);
        int index2 = players.indexOf(p2);

        if (index1 == index2) {
            return 0; // same player
        }

        int max = Math.round(players.size() / 2);

        int dist = Math.abs(index2 - index1);
        if (dist > max) {
            dist = players.size() - dist;
        }

        return dist;
    }

    /**
     * Returns the net modified distance of a target player relative to a source
     * player (including character abilities, weapon, and object cards in play)
     *
     * @param source player trying to reach the target player
     * @param target player viewed by the source player
     * @return distance of target player from source player (including mods)
     */
    public int getViewableDistance(Player source, Player target) {
        return getPositionDistance(source, target) + target.getViewableDistanceDelta();
    }

    /**
     * Determines if a source player can reach a target player
     *
     * @param source player trying to reach the target player
     * @param target player viewed by the source player
     * @return true if the target player is within the reachable distance of the
     * target player including modifications
     */
    public boolean canReach(Player source, Player target) {
        return source.getReachableDistance() >= getViewableDistance(source, target);
    }

}
