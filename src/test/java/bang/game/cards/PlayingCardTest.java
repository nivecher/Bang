package bang.game.cards;

import bang.game.Player;
import bang.game.PlayingContext;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Created by Morgan on 12/4/2016.
 */
public class PlayingCardTest {
    private PlayingCard cut = new PlayingCard("Test 123", Color.Blue, Suit.Spades, Face.Five);
    private PlayingContext mockContext = mock(PlayingContext.class);
    private Player mockPlayer1 = mock(Player.class);
    private Player mockPlayer2 = mock(Player.class);
    private Player mockPlayer3 = mock(Player.class);
    private Player mockPlayer4 = mock(Player.class);
    private List<PlayingCard> mockDiscardPile = mock(List.class);

    @Before
    public void setUp() throws Exception {
        reset(mockPlayer1);
        reset(mockPlayer2);
        reset(mockPlayer3);
        reset(mockPlayer4);
        reset(mockDiscardPile);
        when(mockContext.getPlayer()).thenReturn(mockPlayer1);
        when(mockContext.getDiscardPile()).thenReturn(mockDiscardPile);
        when(mockContext.getActivePlayers()).thenReturn(Arrays.asList(mockPlayer1, mockPlayer2, mockPlayer3));
        when(mockContext.getActiveOpponents()).thenReturn(Arrays.asList(mockPlayer2, mockPlayer3));
        when(mockContext.getTargetPlayer(any())).thenReturn(mockPlayer3);
        cut.setContext(mockContext);
    }

    @Test
    public void getName() throws Exception {
        assertEquals("Test 123", cut.getName());
    }

    @Test
    public void getColor() throws Exception {
        assertEquals(Color.Blue, cut.getColor());
    }

    @Test
    public void getSuit() throws Exception {
        assertEquals(Suit.Spades, cut.getSuit());
    }

    @Test
    public void getFace() throws Exception {
        assertEquals(Face.Five, cut.getFace());
    }

    @Test
    public void findCard() throws Exception {
        PlayingCard bangCard = new BangCard(Suit.Hearts, Face.Four);
        PlayingCard barrelCard = new BarrelCard(Suit.Diamonds, Face.Three);
        Collection<PlayingCard> cards = Arrays.asList(barrelCard, bangCard);
        assertEquals(bangCard, PlayingCard.findCard(BangCard.class, cards));
        assertEquals(barrelCard, PlayingCard.findCard(BarrelCard.class, cards));
        assertNull(PlayingCard.findCard(PanicCard.class, cards));
    }

    @Test
    public void setContext() throws Exception {
        cut.setContext(mock(PlayingContext.class));
    }


    @Test
    public void testPlayerEffect() throws Exception {
        PlayingCard card = new BangCard(Suit.Clubs, Face.Three);
        card.setContext(mockContext);
        when(mockContext.getTargetPlayer(any())).thenReturn(mockPlayer2);
        when(mockPlayer2.bang()).thenReturn(true);
        assertTrue(card.play());
        verify(mockPlayer2).bang();
    }

    @Test
    public void testPlayBrown() throws Exception {
        PlayingCard card = new MissedCard(Suit.Spades, Face.Five);
        card.setContext(mockContext);
        assertFalse(card.play());
        verify(mockPlayer1).discardCard(card, mockDiscardPile);
    }

    @Test
    public void testPlayBlue() throws Exception {
        when(mockPlayer1.playCardOnBoard(cut)).thenReturn(true);
        assertTrue(cut.play());
        verify(mockPlayer1).playCardOnBoard(cut);
    }
}
