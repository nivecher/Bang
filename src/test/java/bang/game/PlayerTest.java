package bang.game;

import bang.game.cards.PlayingCard;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

/**
 * Created by Morgan on 8/20/2016.
 */
public class PlayerTest {

    private Player deputyPlayer = new Player(Role.Deputy);
    private Player outlawPlayer = new Player(Role.Outlaw);
    private Player rengegadePlayer = new Player(Role.Renegade);
    private Player sheriffPlayer = new Player(Role.Sheriff);

    private Character willyTheKid = new Character("Willy The Kid", Ability.CAN_PLAY_ANY_NUMBER_OF_BANG_CARDS, 4);
    private Character paulRegret = new Character("Paul Regret", Ability.SEEN_AT_DISTANCE_PLUS_ONE, 3);
    private Character calamityJanet = new Character("Calamity Janet", Ability.BANGS_ARE_MISSED, 4);
    private Character suzyLafayette = new Character("Suzy Lafayette", Ability.DRAWS_WHEN_HAND_IS_EMPTY, 4);

    List<PlayingCard> mockDrawPile = mock(List.class);
    List<PlayingCard> mockDiscardPile = mock(List.class);

    @Before
    public void setUp() throws Exception {
        deputyPlayer.setCharacter(willyTheKid);
        outlawPlayer.setCharacter(paulRegret);
        rengegadePlayer.setCharacter(calamityJanet);
        sheriffPlayer.setCharacter(suzyLafayette);
    }

    @After
    public void tearDown() throws Exception {
        deputyPlayer.reset();
        outlawPlayer.reset();
        rengegadePlayer.reset();
        sheriffPlayer.reset();
        reset(mockDrawPile);
        reset(mockDiscardPile);
    }

    @Test
    public void testCharacterLives() throws Exception {
        Character mockCharacter = mock(Character.class);
        int numBullets = 4;
        when(mockCharacter.getNumBullets()).thenReturn(numBullets);
        deputyPlayer.setCharacter(mockCharacter);
        assertEquals(numBullets, deputyPlayer.getNumLives()); // num bullets
        assertEquals(numBullets, deputyPlayer.getMaxLives()); // num bullets
        sheriffPlayer.setCharacter(mockCharacter);
        assertEquals(numBullets + 1, sheriffPlayer.getNumLives()); // + 1
        assertEquals(numBullets + 1, sheriffPlayer.getMaxLives()); // + 1
    }

    @Test
    public void testRole() throws Exception {
        assertEquals(Role.Deputy, deputyPlayer.getRole());
        assertEquals(Role.Outlaw, outlawPlayer.getRole());
        assertEquals(Role.Renegade, rengegadePlayer.getRole());
        assertEquals(Role.Sheriff, sheriffPlayer.getRole());
    }

    @Test
    public void testBang() throws Exception {
        // TODO implement
//        assertTrue(outlawPlayer.bang());
    }

    @Test
    public void testDrawAndDiscard() throws Exception {
        PlayingCard card = mock(PlayingCard.class);
        when(mockDrawPile.remove(0)).thenReturn(card);
        when(mockDiscardPile.add(card)).thenReturn(true);
        assertEquals(card, deputyPlayer.drawCard(mockDrawPile));
        assertTrue(deputyPlayer.discardCard(card, mockDiscardPile));
        verify(mockDrawPile).remove(0);
        verify(mockDiscardPile).add(card);
    }

    @Test
    public void testPlayAndDiscardCard() throws Exception {
        // TODO implement
    }

    @Test
    public void getReachableDistance() throws Exception {

    }

    @Test
    public void getViewableDistanceDelta() throws Exception {

    }

    @Test
    public void testPlayAndDiscardFromBoard() throws Exception {
        PlayingCard card = mock(PlayingCard.class);
        when(mockDiscardPile.add(card)).thenReturn(true);

        assertTrue(sheriffPlayer.playCardOnBoard(card));
        assertFalse(sheriffPlayer.getHand().contains(card));
        assertTrue(sheriffPlayer.getCards().contains(card));

        assertTrue(sheriffPlayer.discardFromBoard(card, mockDiscardPile));
        assertFalse(sheriffPlayer.getCards().contains(card));
        assertFalse(sheriffPlayer.getHand().contains(card));
    }

    @Test
    public void testRegainAndLooseLife() throws Exception {
        int origLives = outlawPlayer.getNumLives();
        assertEquals(origLives, outlawPlayer.getMaxLives()); // max
        assertFalse(outlawPlayer.regainLife());
        assertEquals(origLives, outlawPlayer.getNumLives());
        assertTrue(outlawPlayer.loseLife());
        assertTrue(outlawPlayer.regainLife());

        for (int i = outlawPlayer.getNumLives(); i > 0; i--) {
            if (i > 1) {
                assertTrue(outlawPlayer.loseLife());
            } else {
                assertFalse(outlawPlayer.loseLife());
            }
        }
        assertEquals(0, outlawPlayer.getNumLives());
        assertEquals(origLives, outlawPlayer.getMaxLives());
    }

    @Test
    public void testCanDraw() throws Exception {
        sheriffPlayer.startTurn();
        assertTrue(sheriffPlayer.canDraw());
        sheriffPlayer.endTurn();
        assertFalse(sheriffPlayer.canDraw());
    }

    @Test
    public void testCanDiscard() throws Exception {
        assertFalse(rengegadePlayer.canDiscard());
        rengegadePlayer.startTurn();
        assertFalse(rengegadePlayer.canDiscard());
        rengegadePlayer.pass();
        for (int i = 0; i < rengegadePlayer.getNumLives(); i++) {
//            rengegadePlayer.drawCard()
        }
        assertTrue(rengegadePlayer.canDiscard());
        assertTrue(rengegadePlayer.canDiscard());
    }

    @Test
    public void getBarrels() throws Exception {
        outlawPlayer.getBarrels(); // TODO
    }

}