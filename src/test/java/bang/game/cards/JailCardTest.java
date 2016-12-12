package bang.game.cards;

import bang.game.Player;
import bang.game.PlayingContext;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

/**
 * Created by Morgan on 11/25/2016.
 */
public class JailCardTest {

    private JailCard cut = new JailCard(Suit.Diamonds, Face.Four);
    private PlayingContext mockContext = mock(PlayingContext.class);
    private Player mockPlayer = mock(Player.class);

    @Before
    public void setUp() throws Exception {
        reset(mockContext);
        reset(mockPlayer);
        when(mockContext.getPlayer()).thenReturn(mockPlayer);
        cut.setContext(mockContext);
    }

    @Test
    public void testApply() throws Exception {
        assertTrue(cut.apply(mockPlayer));
        verify(mockPlayer, times(1)).accept(cut);
    }

    @Test
    public void testHeart() throws Exception {
        PlayingCard card = new PlayingCard("Heart", Color.Blue, Suit.Hearts, Face.Ace);
        when(mockContext.flipCard()).thenReturn(card);
        assertFalse(cut.play());
    }

    @Test
    public void testNotHeart() throws Exception {
        PlayingCard card = new PlayingCard("Not Heart", Color.Blue, Suit.Spades, Face.Ace);
        when(mockContext.flipCard()).thenReturn(card);
        assertTrue(cut.play());
    }
}