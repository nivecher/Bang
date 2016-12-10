package bang.game.cards;

import bang.game.Player;
import bang.game.PlayingContext;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

/**
 * Created by Morgan on 11/23/2016.
 */
public class BeerCardTest {

    private BeerCard cut = new BeerCard(Suit.Hearts, Face.Three);
    private PlayingContext mockContext = mock(PlayingContext.class);
    private Player mockPlayer = mock(Player.class);

    @Before
    public void setUp() throws Exception {
        reset(mockContext);
        reset(mockPlayer);
        cut.setContext(mockContext);
    }

    @Test
    public void testBeer() throws Exception {
        when(mockContext.getActivePlayers()).thenReturn(Arrays.asList(mockPlayer, mock(Player.class), mock(Player.class)));
        when(mockPlayer.regainLife()).thenReturn(true);
        assertTrue(cut.apply(mockPlayer));
        verify(mockPlayer, times(1)).regainLife();
    }

    @Test
    public void testTwoPlayersLeft() throws Exception {
        when(mockContext.getActivePlayers()).thenReturn(Arrays.asList(mockPlayer, mock(Player.class)));
        when(mockPlayer.regainLife()).thenReturn(true);
        assertFalse(cut.apply(mockPlayer));
        verify(mockPlayer, never()).regainLife();
    }

}