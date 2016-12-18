/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bang.game;

import bang.game.cards.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import static bang.game.BangGame.MAX_PLAYERS;
import static bang.game.BangGame.MIN_PLAYERS;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

/**
 *
 * @author Morgan
 */
public class TestBangGame {

    @Test
    public void testConstructors() throws Exception {
        try {
            new BangGame(-1);
            fail("Did not throw exception for -1 players");
        } catch (IllegalArgumentException ex) {
            assertNotNull(ex.getMessage());
        }
        try {
            new BangGame(9);
            fail("Did not throw exception for 9 players");
        } catch (IllegalArgumentException ex) {
            assertNotNull(ex.getMessage());
        }
    }

    @Test
    public void testGameSetup() throws Exception {
        BangGame cut = new BangGame(4);
        cut.setup();
        assertEquals(4, cut.getActivePlayers().size());
        assertTrue(cut.getDiscardPile().isEmpty());
        assertFalse(cut.getDrawPile().isEmpty());
        assertEquals(4, cut.getPlayers().size());
        for (Player p : cut.getPlayers()) {
            assertNotNull(p.getCharacter());
            assertNotNull(p.getCharacter().getName());
            assertNotNull(p.getRole());
            assertEquals(p.getMaxLives(), p.getNumLives());
            assertEquals(p.getMaxLives(), p.getHand().size());
        }
    }

    @Test
    public void testDistance() {
        IntStream.rangeClosed(MIN_PLAYERS, MAX_PLAYERS).forEach(i -> {
            BangGame game = new BangGame(i);
            System.out.println("Num Players: " + i);
            List<Player> player1List = game.getPlayers();
            List<Player> player2List = game.getPlayers();
            player1List.forEach((p1) -> player2List.forEach((p2) -> {
                System.out.println("Player x: " + player1List.indexOf(p1));
                System.out.println("Player y: " + player1List.indexOf(p2));
                System.out.println("Distance: " + game.getPositionDistance(p1, p2));
                assertEquals(game.getPositionDistance(p1, p2),
                             game.getViewableDistance(p1, p2));
            }));
        });

    }

    @Test
    public void testCanReach() {
        BangGame game = new BangGame(MIN_PLAYERS);
        List<Player> players = game.getPlayers();
                
        Player p1 = players.get(0);
        p1.setCharacter(new Character("p1-character", Ability.SEEN_AT_DISTANCE_PLUS_ONE, 3));
        Player p2 = players.get(1);
        p2.setCharacter(new Character("p2-character", Ability.DRAW_FIRST_FROM_PLAYER, 4));
        Player p3 = players.get(2);
        p3.setCharacter(new Character("p3-character", Ability.BANGS_ARE_MISSED, 4));
        Player p4 = players.get(3);
        p4.setCharacter(new Character("p4-character", Ability.SEES_AT_DISTANCE_MINUS_ONE, 5));

        WeaponCard gun2 = new WeaponCard("gun2", 2);
        WeaponCard gun3 = new WeaponCard("gun3", 3);
        WeaponCard gun4 = new WeaponCard("gun4", 4);
        ScopeCard scope1 = new ScopeCard(Suit.Clubs, Face.Ace);
        ScopeCard scope2 = new ScopeCard(Suit.Hearts, Face.Three);
        MustangCard mustang = new MustangCard(Suit.Spades, Face.Jack);
        
        List<PlayingCard> discardPile = new ArrayList<>();
        
        // verify distances without mods
        assertEquals(1, game.getPositionDistance(p1, p2));
        assertEquals(1, game.getViewableDistance(p1, p2));
        assertTrue(game.canReach(p1, p2));
        assertEquals(2, game.getPositionDistance(p1, p3));
        assertEquals(2, game.getViewableDistance(p1, p3));
        assertFalse(game.canReach(p1, p3));
        assertEquals(1, game.getPositionDistance(p1, p4));
        assertEquals(1, game.getViewableDistance(p1, p4));
        assertTrue(game.canReach(p1, p4));
        
        // verify reach does not imply viewable distance
        assertEquals(1, game.getPositionDistance(p2, p1));
        assertEquals(2, game.getViewableDistance(p2, p1));
        assertFalse(game.canReach(p2, p1)); // based on char mod to target

        drawAndPlay(p2, gun2);
        assertEquals(2, p2.getTargetDistance());
        assertEquals(2, game.getViewableDistance(p2, p1));
        assertTrue(game.canReach(p2, p1)); // based on weapon mod

        // verify no change based on object mod to increase target reach
        assertEquals(1, p1.getTargetDistance());
        drawAndPlay(p1, scope1);
        assertEquals(2, p1.getTargetDistance());
        // scope applied, no change to viewable distance
        assertEquals(1, game.getViewableDistance(p1, p2));
        assertEquals(2, game.getViewableDistance(p2, p1)); // no effect
        assertTrue(game.canReach(p2, p1));

        assertEquals(2, game.getViewableDistance(p1, p3)); // can now reach
        assertTrue(game.canReach(p1, p3));

        drawAndPlay(p1, mustang);
        assertEquals(3, game.getViewableDistance(p2, p1)); // scope + char
        assertFalse(game.canReach(p2, p1)); // based on char mod to target

        drawAndPlay(p2, gun4); // can more than reach
        assertEquals(3, game.getViewableDistance(p2, p1)); // scope + char
        assertTrue(game.canReach(p2, p1)); // based on weapon mod

        assertEquals(2, p4.getTargetDistance()); // based on char
        drawAndPlay(p4, scope2);
        assertEquals(3, p4.getTargetDistance()); // plus scope
        drawAndPlay(p4, gun3); // dist 3 weapon
        assertEquals(5, p4.getTargetDistance()); // new weapon
        assertTrue(p4.discardFromBoard(gun3, discardPile)); // default weapon
        assertEquals(3, p4.getTargetDistance()); // default weapon
        assertTrue(p4.discardFromBoard(scope2, discardPile)); // discard scope
        assertEquals(2, p4.getTargetDistance()); // minus scope
        
    }

    private void drawAndPlay(Player p, PlayingCard card) {
        card.setContext(new PlayingContext(mock(BangGame.class), p));
        assertEquals(card, p.drawCard(new ArrayList(Arrays.asList(card))));
        assertTrue(card.play());
    }
}
