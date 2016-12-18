package bang.game.cards;

import bang.game.Player;
import bang.game.PlayingContext;
import org.junit.Before;
import org.junit.Test;

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

    @Before
    public void setUp() throws Exception {
        reset(mockContext);
        reset(mockPlayer);
        cut.setContext(mockContext);
    }

    @Test
    public void testDynamiteExplodes() throws Exception {
        PlayingCard lowSpade = new PlayingCard("2 of spades", Color.Brown, Suit.Spades, Face.Two);
        when(mockContext.flipCard()).thenReturn(lowSpade);
        assertTrue(cut.apply(mockPlayer));

        verify(mockPlayer, times(3)).loseLife();
    }

    @Test
    public void testDynamiteDoesntExplode() throws Exception {
        PlayingCard lowSpade = new PlayingCard("10 of spades", Color.Brown, Suit.Spades, Face.Ten);
        when(mockContext.flipCard()).thenReturn(lowSpade);

        assertFalse(cut.apply(mockPlayer));

        verify(mockPlayer, never()).loseLife();
    }

}