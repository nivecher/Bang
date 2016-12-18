package bang.game.cards;

import bang.game.Player;
import bang.game.PlayingContext;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

/**
 * Created by Morgan on 11/25/2016.
 */
public class IndiansCardTest {

    private IndiansCard cut = new IndiansCard(Suit.Clubs, Face.Six);
    private PlayingContext mockContext = mock(PlayingContext.class);
    private Player mockPlayer1 = mock(Player.class);
    private Player mockPlayer2 = mock(Player.class);
    private Player mockPlayer3 = mock(Player.class);
    private Player mockPlayer4 = mock(Player.class);

    @Before
    public void setUp() throws Exception {
        reset(mockContext);
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
        when(mockPlayer2.forceDiscard(eq(BangCard.class), any())).thenReturn(true); // has Bang
        assertTrue(cut.play());
        verify(mockPlayer1, times(1)).forceDiscard(eq(BangCard.class), any());
        verify(mockPlayer2, times(1)).forceDiscard(eq(BangCard.class), any());
        verify(mockPlayer3, times(1)).forceDiscard(eq(BangCard.class), any());
        verify(mockPlayer4, never()).forceDiscard(eq(BangCard.class), any());
    }

}