package bang.game.cards;

import bang.game.Player;
import bang.game.PlayingContext;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

/**
 * Created by Morgan on 8/20/2016.
 */
public class BarrelCardTest {

    private BarrelCard barrelCard = new BarrelCard(Suit.Diamonds, Face.Five);
    private PlayingContext mockContext = mock(PlayingContext.class);
    private Player mockPlayer = mock(Player.class);
    private PlayingCard diamondCard = new PlayingCard("diamond", Color.Brown, Suit.Diamonds, Face.Queen);
    private PlayingCard heartCard = new PlayingCard("diamond", Color.Brown, Suit.Hearts, Face.Two);

    @Before
    public void setUp() throws Exception {
        reset(mockContext);
        reset(mockPlayer);
        barrelCard.setContext(mockContext);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testDefaultBarrel() throws Exception {
        barrelCard = new BarrelCard();
        setUp();
        when(mockContext.flipCard()).thenReturn(diamondCard);
        assertFalse(barrelCard.apply(mockPlayer));
    }

    @Test
    public void testApplyHeart() throws Exception {
        when(mockContext.flipCard()).thenReturn(heartCard);
        assertTrue(barrelCard.apply(mockPlayer));
        verify(mockContext).flipCard();
    }

    @Test
    public void testApplyDiamond() throws Exception {
        when(mockContext.flipCard()).thenReturn(diamondCard);
        assertFalse(barrelCard.apply(mockPlayer));
        verify(mockContext).flipCard();
    }

}