package bang.game.cards;

import bang.game.BangGame;
import bang.game.Player;
import bang.game.PlayingContext;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

/**
 * Created by Morgan on 11/24/2016.
 */
public class StagecoachCardTest {

    private StagecoachCard cut = new StagecoachCard(Suit.Hearts, Face.Jack);
    private BangGame mockGame = mock(BangGame.class);
    private Player mockPlayer = mock(Player.class);
    private List<PlayingCard> mockDrawPile = mock(List.class);
    private PlayingContext mockContext = mock(PlayingContext.class);

    @Before
    public void setUp() throws Exception {
        reset(mockContext);
        reset(mockGame);
        reset(mockPlayer);
        reset(mockDrawPile);
        cut.setContext(mockContext);
        when(mockContext.getPlayer()).thenReturn(mockPlayer);
        when(mockContext.getDrawPile()).thenReturn(mockDrawPile);
    }

    @Test
    public void testPlay() throws Exception {
        assertTrue(cut.play());
        verify(mockPlayer, times(2)).drawCard(mockDrawPile);
    }

}