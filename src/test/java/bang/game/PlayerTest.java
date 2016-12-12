package bang.game;

import bang.PlayerController;
import bang.game.cards.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Created by Morgan on 8/20/2016.
 */
public class PlayerTest {

    private Player deputyPlayer = new Player(Role.Deputy);
    private Player outlawPlayer = new Player(Role.Outlaw);
    private Player renegadePlayer = new Player(Role.Renegade);
    private Player sheriffPlayer = new Player(Role.Sheriff);

    private Character willyTheKid = new Character("Willy The Kid", Ability.CAN_PLAY_ANY_NUMBER_OF_BANG_CARDS, 4);
    private Character paulRegret = new Character("Paul Regret", Ability.SEEN_AT_DISTANCE_PLUS_ONE, 3);
    private Character calamityJanet = new Character("Calamity Janet", Ability.BANGS_ARE_MISSED, 4);
    private Character suzyLafayette = new Character("Suzy Lafayette", Ability.DRAWS_WHEN_HAND_IS_EMPTY, 4);

    private List<PlayingCard> mockDrawPile = mock(List.class);
    private List<PlayingCard> mockDiscardPile = mock(List.class);

    /**
     * Fake card that acts as a scope and mustang
     */
    private class DistModCard extends PlayingCard implements TargetDistanceModifier, ViewableDistanceModifier {

        public DistModCard() {
            super("Distance modifier Card", Color.Blue, Suit.Diamonds, Face.Seven);
        }

        @Override
        public int getDecrease() {
            return 1;
        }

        @Override
        public int getIncrease() {
            return 1;
        }
    }

    @Before
    public void setUp() throws Exception {
        when(mockDrawPile.remove(0)).thenReturn(createCard());
        when(mockDiscardPile.add(any())).thenReturn(true);
        deputyPlayer.setController(mock(PlayerController.class));
        deputyPlayer.setCharacter(willyTheKid);
        outlawPlayer.setCharacter(paulRegret);
        outlawPlayer.setController(mock(PlayerController.class));
        renegadePlayer.setCharacter(calamityJanet);
        renegadePlayer.setController(mock(PlayerController.class));
        sheriffPlayer.setCharacter(suzyLafayette);
        sheriffPlayer.setController(mock(PlayerController.class));
    }

    @After
    public void tearDown() throws Exception {
        deputyPlayer.reset();
        outlawPlayer.reset();
        renegadePlayer.reset();
        sheriffPlayer.reset();
        reset(mockDrawPile);
        reset(mockDiscardPile);
    }

    @Test
    public void testAcceptCard() throws Exception {
        PlayingCard card = new PlayingCard("Test Card", Color.Brown, Suit.Clubs, Face.Eight);
        renegadePlayer.accept(card);
        assertTrue(renegadePlayer.getCards().contains(card));
    }

    @Test
    public void testInJail() throws Exception {
        assertFalse(outlawPlayer.isInJail());
        outlawPlayer.accept(new JailCard(Suit.Diamonds, Face.King));
        assertTrue(outlawPlayer.isInJail());
        try {
            sheriffPlayer.accept(new JailCard(Suit.Diamonds, Face.King));
            fail("Allowed sheriff to be put in jail!");
        } catch(IllegalArgumentException ex) {
            assertNotNull(ex.getMessage());
        }
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
        assertEquals(Role.Renegade, renegadePlayer.getRole());
        assertEquals(Role.Sheriff, sheriffPlayer.getRole());
    }

    @Test
    public void testBangHit() throws Exception {
        PlayerController controller = mock(PlayerController.class);
        outlawPlayer.setController(controller);
        when(controller.avoidHit()).thenReturn(false);
        assertTrue(outlawPlayer.bang());
    }

    @Test
    public void testBangMissed() throws Exception {
        PlayerController controller = mock(PlayerController.class);
        renegadePlayer.setController(controller);
        when(controller.avoidHit()).thenReturn(true);
        assertFalse(renegadePlayer.bang());
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
    public void testTargetDistance() throws Exception {
        assertEquals(1, sheriffPlayer.getTargetDistance());
        Character mockCharacter = mock(Character.class);
        when(mockCharacter.getDecrease()).thenReturn(1);
        sheriffPlayer.setCharacter(mockCharacter);
        assertEquals(2, sheriffPlayer.getTargetDistance());

        when(mockDrawPile.remove(0)).thenReturn(new DistModCard());
        sheriffPlayer.playCardOnBoard(sheriffPlayer.drawCard(mockDrawPile));
        assertEquals(3, sheriffPlayer.getTargetDistance());
    }

    @Test
    public void testViewableDistanceDelta() throws Exception {
        assertEquals(0, sheriffPlayer.getViewableDistanceDelta());
        Character mockCharacter = mock(Character.class);
        when(mockCharacter.getIncrease()).thenReturn(1);
        sheriffPlayer.setCharacter(mockCharacter);
        assertEquals(1, sheriffPlayer.getViewableDistanceDelta());

        when(mockDrawPile.remove(0)).thenReturn(new DistModCard());
        sheriffPlayer.playCardOnBoard(sheriffPlayer.drawCard(mockDrawPile));
        assertEquals(2, sheriffPlayer.getViewableDistanceDelta());
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
        assertEquals(2, renegadePlayer.getCardsToDraw());
        assertFalse(renegadePlayer.isTurn());
        assertFalse(renegadePlayer.canDraw()); // not turn
        renegadePlayer.startTurn();
        assertTrue(renegadePlayer.isTurn());
        assertTrue(renegadePlayer.canDraw()); // is turn, has cards to draw
        renegadePlayer.drawCard(mockDrawPile);
        assertEquals(1, renegadePlayer.getCardsToDraw());
        assertTrue(renegadePlayer.canDraw()); // still turn, has more cards to draw
        renegadePlayer.drawCard(mockDrawPile);
        assertEquals(0, renegadePlayer.getCardsToDraw());
        assertFalse(renegadePlayer.canDraw()); // still turn, has no cards to draw
        renegadePlayer.endTurn();
        assertFalse(renegadePlayer.isTurn());
        assertFalse(renegadePlayer.canDraw()); // not turn
    }

    @Test
    public void testCanPlay() throws Exception {
        assertFalse(renegadePlayer.isTurn());
        assertFalse(renegadePlayer.canPlay()); // not turn
        renegadePlayer.startTurn();
        assertTrue(renegadePlayer.isTurn());
        assertEquals(2, renegadePlayer.getCardsToDraw());
        assertFalse(renegadePlayer.canPlay()); // not drawn
        renegadePlayer.drawCard(mockDrawPile);
        assertEquals(1, renegadePlayer.getCardsToDraw());
        assertFalse(renegadePlayer.canPlay()); // not finished drawing
        renegadePlayer.drawCard(mockDrawPile);
        assertEquals(0, renegadePlayer.getCardsToDraw());
        assertTrue(renegadePlayer.canPlay()); // can player
        renegadePlayer.endTurn();
        assertFalse(renegadePlayer.isTurn());
        assertFalse(renegadePlayer.canPlay()); // turn over
    }

    @Test
    public void testCanDiscard() throws Exception {
        assertFalse(renegadePlayer.isTurn());
        assertFalse(renegadePlayer.canDiscard()); // not turn
        renegadePlayer.startTurn();
        assertTrue(renegadePlayer.isTurn());
        assertFalse(renegadePlayer.canDiscard()); // not drawn

        renegadePlayer.drawCard(mockDrawPile); // draw
        assertFalse(renegadePlayer.canDiscard()); // only drawn once

        renegadePlayer.drawCard(mockDrawPile); // draw
        assertFalse(renegadePlayer.canDiscard()); // drawn, ready to play

        assertEquals(0, renegadePlayer.pass()); // automatically ends turn
        assertFalse(renegadePlayer.isTurn());
        givenPlayerHasMaxCards(renegadePlayer); // no extra cards
        assertEquals(0, renegadePlayer.cardsToDiscard());
        assertFalse(renegadePlayer.canDiscard()); // turn ended

        renegadePlayer.startTurn();

        // given has extra cards
        renegadePlayer.drawCard(mockDrawPile); // draw once
        PlayingCard card = renegadePlayer.drawCard(mockDrawPile); // draw again

        assertEquals(2, renegadePlayer.cardsToDiscard());
        assertFalse(renegadePlayer.canDiscard()); // not passing, but has cards to discard

        renegadePlayer.pass();
        assertTrue(renegadePlayer.canDiscard()); // passing

        assertTrue(renegadePlayer.discardCard(card, mockDiscardPile)); // discard
        assertEquals(1, renegadePlayer.cardsToDiscard());
        assertTrue(renegadePlayer.canDiscard()); // no more discards
        assertTrue(renegadePlayer.discardCard(card, mockDiscardPile)); // discard
        assertEquals(0, renegadePlayer.cardsToDiscard());
        assertFalse(renegadePlayer.canDiscard()); // no more discards
        assertFalse(renegadePlayer.isTurn()); // turn automatically ended
    }

    @Test
    public void testCanPass() throws Exception {

        // given player has not started turn -> cannot pass
        assertFalse(deputyPlayer.isTurn());
        assertFalse(deputyPlayer.canPass()); // not turn

        deputyPlayer.startTurn();
        assertTrue(deputyPlayer.isTurn());
        assertFalse(deputyPlayer.canPass()); // not drawn

        deputyPlayer.drawCard(mockDrawPile);
        assertFalse(deputyPlayer.canPass()); // not finished drawning

        deputyPlayer.drawCard(mockDrawPile);
        assertTrue(deputyPlayer.canPass()); // ready to play or pass

        deputyPlayer.pass();
        assertFalse(deputyPlayer.canPass()); // already passing

        // given player is attacked and not started a turn
        deputyPlayer.bang();
        assertTrue(deputyPlayer.canPass());
    }

    @Test
    public void testCanEndTurn() throws Exception {

        assertFalse(outlawPlayer.isTurn());
        assertFalse(outlawPlayer.canEndTurn()); // not turn

        outlawPlayer.startTurn(); // turn started
        assertFalse(outlawPlayer.canEndTurn()); // not drawn

        outlawPlayer.drawCard(mockDrawPile);
        assertFalse(outlawPlayer.canEndTurn()); // not finished drawing

        outlawPlayer.drawCard(mockDrawPile);
        assertTrue(outlawPlayer.canEndTurn()); // finished drawing, not cards to discard

        givenPlayerHasMaxCards(outlawPlayer);
        assertTrue(outlawPlayer.canEndTurn()); // finished drawing, not cards to discard

        outlawPlayer.drawCard(mockDrawPile); // too many cards
        assertFalse(outlawPlayer.canEndTurn()); // need to discard

        // TODO start here

        // given player has max cards
//        assertEquals(outlawPlayer.getMaxCards(), outlawPlayer.getHand().size());

    }

    @Test
    public void testMaxLives() throws Exception {
        assertEquals(renegadePlayer.getCharacter().getNumBullets(), renegadePlayer.getMaxLives());
        assertEquals(sheriffPlayer.getCharacter().getNumBullets() + 1, sheriffPlayer.getMaxLives());
    }

    @Test
    public void getBarrels() throws Exception {
        outlawPlayer.getBarrels(); // TODO
    }

    private void givenPlayerHasMaxCards(Player p) {
        while (p.getCards().size() < p.getMaxCards()) {
            p.accept(createCard());
        }
    }

    private static PlayingCard createCard() {
        return new PlayingCard("Test card", Color.Brown, Suit.Hearts, Face.Jack);
    }

}
