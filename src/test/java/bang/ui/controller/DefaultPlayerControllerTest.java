package bang.ui.controller;

import bang.PlayerController;
import bang.game.Ability;
import bang.game.BangGame;
import bang.game.Character;
import bang.game.Player;
import bang.game.cards.Color;
import bang.game.cards.Face;
import bang.game.cards.PlayingCard;
import bang.game.cards.Suit;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Created by Morgan on 8/21/2016.
 */
public class DefaultPlayerControllerTest {

    private Player mockPlayer = mock(Player.class);
    private BangGame mockGame = mock(BangGame.class);
    private PlayerController cut = new DefaultPlayerController(mockPlayer, mockGame);

    @Before
    public void setUp() throws Exception {
        cut = new DefaultPlayerController(mockPlayer, mockGame);
    }

    @After
    public void tearDown() throws Exception {
        reset(mockPlayer);
        reset(mockGame);
    }

    @Test
    public void testDraw() throws Exception {
        PlayingCard c1 = new PlayingCard("Card 1", Color.Brown, Suit.Clubs, Face.Nine);
        PlayingCard c2 = new PlayingCard("Card 2", Color.Blue, Suit.Hearts, Face.Jack);
        List<PlayingCard> drawList = Arrays.asList(c1, c2);
        when(mockGame.getDrawPile()).thenReturn(drawList);
        when(mockPlayer.getCharacter()).thenReturn(new Character("Test", Ability.CAN_PLAY_ANY_NUMBER_OF_BANG_CARDS, 3));
        when(mockPlayer.canDraw()).thenReturn(true).thenReturn(true).thenReturn(false);
        when(mockPlayer.drawCard(drawList)).thenReturn(c1).thenReturn(c2);
        assertEquals(drawList, cut.draw());
        verify(mockPlayer, times(2)).drawCard(drawList);
    }

    @Test
    public void play() throws Exception {

    }

    @Test
    public void takeTurn() throws Exception {
        cut.takeTurn();
        verify(mockPlayer, times(1)).startTurn();
    }

    @Test
    public void testDiscard() throws Exception {
        PlayingCard c1 = new PlayingCard("Card 1", Color.Blue, Suit.Hearts, Face.Ace);
        PlayingCard c2 = new PlayingCard("Card 1", Color.Blue, Suit.Diamonds, Face.Four);
        List<PlayingCard> hand = Arrays.asList(c1, c2);
        // TODO assert selection
        List<PlayingCard> discardPile = mock(List.class);
        when(mockGame.getDiscardPile()).thenReturn(discardPile);
        when(mockPlayer.getHand()).thenReturn(hand);
        when(mockPlayer.discardCard(c1, discardPile)).thenReturn(true);
        assertEquals(c1, cut.discard());
        verify(mockPlayer).discardCard(c1, discardPile);
    }

    @Test
    public void testPass() throws Exception {
        PlayingCard c1 = new PlayingCard("Card 1", Color.Blue, Suit.Hearts, Face.Ace);
        PlayingCard c2 = new PlayingCard("Card 2", Color.Brown, Suit.Spades, Face.Three);
        PlayingCard c3 = new PlayingCard("Card 3", Color.Blue, Suit.Diamonds, Face.Queen);
        ISelector<PlayingCard> mockSelector = mock(ISelector.class);
        List<PlayingCard> hand = Arrays.asList(c1, c2, c3);
        List<PlayingCard> discardPile = mock(List.class);
        when(mockSelector.select(hand)).thenReturn(c2).thenReturn(c3); // AI to selectCard c2 and c3
        when(mockGame.getDiscardPile()).thenReturn(discardPile);
        when(mockPlayer.getHand()).thenReturn(hand);
        when(mockPlayer.discardCard(c2, discardPile)).thenReturn(true);
        when(mockPlayer.discardCard(c3, discardPile)).thenReturn(true);
        when(mockPlayer.pass()).thenReturn(2);
        assertEquals(Arrays.asList(c2, c3), cut.pass()); // verify automatic discard
        verify(mockPlayer).discardCard(c2, discardPile);
        verify(mockPlayer).discardCard(c3, discardPile);
    }

}