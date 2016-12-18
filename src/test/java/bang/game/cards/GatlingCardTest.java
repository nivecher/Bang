package bang.game.cards;

import bang.game.Player;
import bang.game.PlayingContext;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

/**
 * Created by Morgan on 11/23/2016.
 */
public class GatlingCardTest {

    private GatlingCard cut = new GatlingCard(Suit.Hearts, Face.Seven);
    private PlayingContext mockContext = mock(PlayingContext.class);
    private Player mockPlayer1 = mock(Player.class);
    private Player mockPlayer2 = mock(Player.class);
    private Player mockPlayer3 = mock(Player.class);
    private Player mockPlayer4 = mock(Player.class);

    @Before
    public void setUp() throws Exception {
        reset(mockPlayer1);
        reset(mockPlayer2);
        reset(mockPlayer3);
        reset(mockPlayer4);
        when(mockContext.getPlayer()).thenReturn(mockPlayer1);
        when(mockContext.getActivePlayers()).thenReturn(Arrays.asList(mockPlayer1, mockPlayer2, mockPlayer3));
        when(mockContext.getActiveOpponents()).thenReturn(Arrays.asList(mockPlayer2, mockPlayer3));
        cut.setContext(mockContext);
    }

    @Test
    public void testPlay() throws Exception {
        assertTrue(cut.play());
        verify(mockPlayer1, never()).bang();
        verify(mockPlayer2, times(1)).bang();
        verify(mockPlayer3, times(1)).bang();
        verify(mockPlayer4, never()).bang();
    }

}