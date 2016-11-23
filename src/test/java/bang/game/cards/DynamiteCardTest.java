package bang.game.cards;

import bang.game.Player;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Created by Morgan on 11/23/2016.
 */
public class DynamiteCardTest {

    private DynamiteCard cut = new DynamiteCard(Suit.Diamonds, Face.Nine);
    private Player mockPlayer = mock(Player.class);
    private List<PlayingCard> drawPile = mock(List.class);
    private List<PlayingCard> discardPile = mock(List.class);

    @Before
    public void setUp() throws Exception {
        reset(drawPile);
        reset(mockPlayer);
        cut.setDrawPile(drawPile);
        cut.setDiscardPile(discardPile);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testDynamiteExplodes() throws Exception {
        PlayingCard lowSpade = new PlayingCard("2 of spades", Color.Brown, Suit.Spades, Face.Two);
        when(mockPlayer.drawCard(drawPile)).thenReturn(lowSpade);
        assertTrue(cut.apply(mockPlayer));

        verify(mockPlayer, times(3)).loseLife();
        verify(mockPlayer).discardCard(lowSpade, discardPile);
    }

    @Test
    public void testDynamiteDoesntExplode() throws Exception {
        PlayingCard lowSpade = new PlayingCard("10 of spades", Color.Brown, Suit.Spades, Face.Ten);
        when(mockPlayer.drawCard(drawPile)).thenReturn(lowSpade);

        assertFalse(cut.apply(mockPlayer));

        verify(mockPlayer, never()).loseLife();
        verify(mockPlayer).discardCard(lowSpade, discardPile);
    }

}