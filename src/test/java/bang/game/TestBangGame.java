/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bang.game;

import static bang.game.BangGame.MAX_PLAYERS;
import static bang.game.BangGame.MIN_PLAYERS;
import java.util.List;
import java.util.stream.IntStream;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Morgan
 */
public class TestBangGame {

    public TestBangGame() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testDistance() {
        IntStream.rangeClosed(MIN_PLAYERS, MAX_PLAYERS).forEach(i -> {
            BangGame game = new BangGame(i);
            System.out.println("Num Players: " + i);
            List<Player> player1List = game.getPlayers();
            List<Player> player2List = game.getPlayers();
            player1List.stream().forEach((p1) -> {
                player2List.stream().forEach((p2) -> {
                    System.out.println("Player x: " + player1List.indexOf(p1));
                    System.out.println("Player y: " + player1List.indexOf(p2));
                    System.out.println("Distance: " + game.getDistance(p1, p2));
                });
            });
        });
    }
}
