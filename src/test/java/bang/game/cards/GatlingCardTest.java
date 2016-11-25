package bang.game.cards;

import bang.game.Player;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Created by Morgan on 11/23/2016.
 */
public class GatlingCardTest {

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testApply() throws Exception {
        GatlingCard cut = new GatlingCard(Suit.Hearts, Face.Seven);
        Player mockPlayer = mock(Player.class);

        when(mockPlayer.bang()).thenReturn(true);
        assertTrue(cut.apply(mockPlayer));

        reset(mockPlayer);

        when(mockPlayer.bang()).thenReturn(false);
        assertFalse(cut.apply(mockPlayer));

        // TODO test that all players get shot
    }

}