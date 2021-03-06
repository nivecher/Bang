/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bang.game;

import bang.PlayerController;
import bang.game.cards.PlayingCard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * BANG! is a shootout game, in Spaghetti Western style, between a group of
 * Outlaws and the Sheriff, who is their primary target. The Deputies incognitos
 * help the Sheriff, but there is also a Renegade pursuing his own goal! In
 * BANG! each player plays one of these roles, and represents a famous Wild West
 * inspired character.
 *
 * @author Morgan
 */
public class BangGame {

    /**
     * List of players
     */
    private final List<Player> players;

    /**
     * Player card draw pile
     */
    private List<PlayingCard> drawPile = new ArrayList<>();

    public List<PlayingCard> getDrawPile() {
        return drawPile;
    }

    public List<PlayingCard> getDiscardPile() {
        return discardPile;
    }

    /**
     * Player card discard pile
     */
    private final List<PlayingCard> discardPile = new ArrayList<>();

    /**
     * Construct a new game with the specified number of players
     *
     * @param numPlayers number of players
     * @throws IllegalArgumentException if numPlayers is out of range
     */
    /*package*/ BangGame(int numPlayers) {
        players = new ArrayList<>(numPlayers);
        for (int i =  0; i < numPlayers; i++) {
            players.add(new Player());
        }
        // TODO remove
//        players.add(new Player(Role.Sheriff));
//        players.add(new Player(Role.Renegade));
//        players.add(new Player(Role.Outlaw));
//        players.add(new Player(Role.Outlaw));
//        if (players.size() < numPlayers) { // 5 players
//            players.add(new Player(Role.Deputy));
//        }
//        if (players.size() < numPlayers) { // 6 players
//            players.add(new Player(Role.Outlaw));
//        }
//        if (players.size() < numPlayers) { // 7 players
//            players.add(new Player(Role.Deputy));
//        }
    }

    /**
     * Reset and setup the game
     */
    public void setup(GameBuilder builder) {
        List<Role> roles = builder.generateRoles();
        List<Character> characters = builder.generateCharacters();
        List<PlayerController> controllers = builder.getControllers();
        drawPile = builder.generatePlayingCards();
        discardPile.clear();

        Collections.shuffle(roles);
        Collections.shuffle(characters);
        Collections.shuffle(drawPile);

        assert characters.size() >= players.size();

        players.forEach((p) -> {
            p.setRole(roles.remove(0));
            p.setCharacter(characters.remove(0));
            p.setController(controllers.remove(0));
            // register player and game with controlller
            p.getController().setPlayer(p);
            p.getController().setGame(this);
            for (int i = 0; i < p.getMaxLives(); i++) {
                p.drawCard(drawPile);
            }
        });

        // verify all elements are passed out
        assert roles.isEmpty();
        assert controllers.isEmpty();
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
     * <p>
     * See Figure below for distances from player A (seven players shown) A,A ->
     * 0; A,B -> 1; A,C -> 2; A,D -> 3; A,E -> 3; A,F -> 2; A,G -> 1
     * <p>
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
        return source.getTargetDistance() >= getViewableDistance(source, target);
    }

    /**
     * Returns the list of active (i.e. not dead) players
     *
     * @return list of players left in the game
     */
    public List<Player> getActivePlayers() {
        return players.stream().filter(p -> p.getNumLives() > 0).collect(Collectors.toList());
    }

    /**
     * Returns reference the sheriff
     * @return Sheriff player
     */
    public Player getSheriff() {
        return players.stream().filter(p -> p.getRole() == Role.Sheriff).findAny().orElseThrow(
                () -> new IllegalStateException("No Sheriff defined"));
    }
}
