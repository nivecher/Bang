package bang.game.cards;

import bang.game.Player;
import bang.game.PlayingContext;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

/**
 * Created by Morgan on 11/23/2016.
 */
public class DynamiteCardTest {

    private DynamiteCard cut = new DynamiteCard(Suit.Diamonds, Face.Nine);
    private PlayingContext mockContext = mock(PlayingContext.class);
    private Player mockPlayer = mock(Player.class);
    private List<PlayingCard> mockDiscardPile = mock(List.class);

    @Before
    public void setUp() throws Exception {
        reset(mockContext);
        reset(mockPlayer);
        reset(mockDiscardPile);
        when(mockContext.getPlayer()).thenReturn(mockPlayer);
        when(mockContext.getDiscardPile()).thenReturn(mockDiscardPile);
        cut.setContext(mockContext);
    }

    @Test
    public void testInitialPlay() throws Exception {
        assertTrue(cut.play());
        verify(mockPlayer, times(1)).accept(cut);
    }

    @Test
    public void testDynamiteExplodes() throws Exception {
        PlayingCard lowSpade = new PlayingCard("2 of spades", Color.Brown, Suit.Spades, Face.Two);
        when(mockContext.flipCard()).thenReturn(lowSpade);
        assertTrue(cut.onTurn());

        verify(mockPlayer, times(3)).loseLife();
        verify(mockPlayer,times(1)).discardCard(cut, mockDiscardPile);
    }

    @Test
    public void testDynamiteDoesNotExplode() throws Exception {
        PlayingCard lowSpade = new PlayingCard("10 of spades", Color.Brown, Suit.Spades, Face.Ten);
        Player mockOpponent = mock(Player.class);
        when(mockContext.flipCard()).thenReturn(lowSpade);
        when(mockContext.getNextPlayer()).thenReturn(mockOpponent);

        assertFalse(cut.onTurn());
        verify(mockPlayer, never()).loseLife();
        verify(mockPlayer, times(1)).loseCard(cut);
        verify(mockOpponent, times(1)).accept(cut);
    }

}