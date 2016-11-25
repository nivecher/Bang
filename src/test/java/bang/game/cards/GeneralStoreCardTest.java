package bang.game.cards;

import bang.game.Player;
import bang.game.PlayingContext;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Created by Morgan on 11/25/2016.
 */
public class GeneralStoreCardTest {
    private GeneralStoreCard cut = new GeneralStoreCard(Suit.Clubs, Face.Seven);
    private PlayingContext mockContext = mock(PlayingContext.class);
    private Player mockPlayer1 = mock(Player.class);
    private Player mockPlayer2 = mock(Player.class);
    private Player mockPlayer3 = mock(Player.class);
    private Player mockPlayer4 = mock(Player.class);
    private List<PlayingCard> mockDrawPile = mock(List.class);

    @Before
    public void setUp() throws Exception {
        reset(mockContext);
        reset(mockPlayer1);
        reset(mockPlayer2);
        reset(mockPlayer3);
        reset(mockPlayer4);
        reset(mockDrawPile);
        when(mockContext.getPlayer()).thenReturn(mockPlayer1);
        when(mockContext.getDrawPile()).thenReturn(mockDrawPile);
        when(mockContext.getActivePlayers()).thenReturn(Arrays.asList(mockPlayer1, mockPlayer2, mockPlayer3));
        when(mockContext.getActiveOpponents()).thenReturn(Arrays.asList(mockPlayer2, mockPlayer3));
        cut.setContext(mockContext);
    }

    @Test
    public void testPlay() throws Exception {
        assertTrue(cut.play());
        verify(mockPlayer1, times(1)).selectCard(any());
        verify(mockPlayer2, times(1)).selectCard(any());
        verify(mockPlayer3, times(1)).selectCard(any());
        verify(mockPlayer4, never()).drawCard(mockDrawPile);
    }
}