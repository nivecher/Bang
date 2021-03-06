package bang.game.cards;

import bang.game.Player;
import bang.game.PlayingContext;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

/**
 * Created by Morgan on 8/20/2016.
 */
public class BarrelCardTest {

    private BarrelCard cut = new BarrelCard(Suit.Diamonds, Face.Five);
    private PlayingContext mockContext = mock(PlayingContext.class);
    private Player mockPlayer = mock(Player.class);
    private PlayingCard diamondCard = new PlayingCard("diamond", Color.Brown, Suit.Diamonds, Face.Queen);
    private PlayingCard heartCard = new PlayingCard("diamond", Color.Brown, Suit.Hearts, Face.Two);

    @Before
    public void setUp() throws Exception {
        reset(mockContext);
        reset(mockPlayer);
        cut.setContext(mockContext);
    }

    @Test
    public void testDefaultBarrel() throws Exception {
        cut = new BarrelCard();
        setUp();
        when(mockContext.flipCard()).thenReturn(diamondCard);
        assertFalse(cut.play());
    }

    @Test
    public void testApplyHeart() throws Exception {
        when(mockContext.flipCard()).thenReturn(heartCard);
        assertTrue(cut.play());
        verify(mockContext).flipCard();
    }

    @Test
    public void testApplyDiamond() throws Exception {
        when(mockContext.flipCard()).thenReturn(diamondCard);
        assertFalse(cut.play());
        verify(mockContext).flipCard();
    }

}