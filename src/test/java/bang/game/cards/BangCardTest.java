package bang.game.cards;

import bang.game.Player;
import org.junit.Test;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

/**
 * Created by Morgan on 8/20/2016.
 */
public class BangCardTest {
    @Test
    public void testStrength() throws Exception {
        BangCard card = new BangCard(Suit.Clubs, Face.Eight);
        assertEquals(1, card.getStrength());

        card.setStrength(2);
        assertEquals(2, card.getStrength());
    }

    @Test
    public void testApply() throws Exception {
        BangCard card = new BangCard(Suit.Spades, Face.Four);
        Player mockPlayer = mock(Player.class);

        when(mockPlayer.bang()).thenReturn(true);
        assertTrue(card.apply(mockPlayer));
        verify(mockPlayer).bang();

        reset(mockPlayer);

        when(mockPlayer.bang()).thenReturn(false);
        assertFalse(card.apply(mockPlayer));
        verify(mockPlayer).bang();

        reset(mockPlayer);

        // Given a strength of 2, first miss, second, hit
        card.setStrength(2);
        when(mockPlayer.bang()).thenReturn(false).thenReturn(true);
        assertTrue(card.apply(mockPlayer));
        verify(mockPlayer, times(2)).bang();

    }

}