package bang.game.cards;

import bang.game.Player;
import bang.game.PlayingContext;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

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
    private List<PlayingCard> mockDiscardPile = mock(List.class);
    private List<PlayingCard> mockDrawPile = mock(List.class);
    private PlayingCard diamondCard = new PlayingCard("diamond", Color.Brown, Suit.Diamonds, Face.Queen);
    private PlayingCard heartCard = new PlayingCard("diamond", Color.Brown, Suit.Hearts, Face.Two);

    @Before
    public void setUp() throws Exception {
        reset(mockContext);
        reset(mockPlayer);
        reset(mockDiscardPile);
        reset(mockDrawPile);
        when(mockContext.getDiscardPile()).thenReturn(mockDiscardPile);
        when(mockContext.getDrawPile()).thenReturn(mockDrawPile);
        barrelCard.setContext(mockContext);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testDefaultBarrel() throws Exception {
        barrelCard = new BarrelCard();
        setUp();
        when(mockPlayer.drawCard(mockDrawPile)).thenReturn(diamondCard);
        assertFalse(barrelCard.apply(mockPlayer));
    }

    @Test
    public void testApplyHeart() throws Exception {
        when(mockPlayer.drawCard(mockDrawPile)).thenReturn(heartCard);
        assertTrue(barrelCard.apply(mockPlayer));
        verify(mockPlayer).drawCard(mockDrawPile);
        verify(mockPlayer).discardCard(heartCard, mockDiscardPile);
    }

    @Test
    public void testApplyDiamond() throws Exception {
        when(mockPlayer.drawCard(mockDrawPile)).thenReturn(diamondCard);
        assertFalse(barrelCard.apply(mockPlayer));
        verify(mockPlayer).drawCard(mockDrawPile);
        verify(mockPlayer).discardCard(diamondCard, mockDiscardPile);
    }

}