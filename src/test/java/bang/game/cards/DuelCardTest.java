package bang.game.cards;

import bang.game.Player;
import bang.game.PlayingContext;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Created by Morgan on 11/25/2016.
 */
public class DuelCardTest {

    private DuelCard cut = new DuelCard(Suit.Spades, Face.Two);
    private PlayingContext mockContext = mock(PlayingContext.class);
    private Player mockPlayer = mock(Player.class);
    private Player mockOpponent = mock(Player.class);


    @Before
    public void setUp() throws Exception {
        reset(mockContext);
        reset(mockPlayer);
        reset(mockOpponent);
        when(mockContext.getPlayer()).thenReturn(mockPlayer);
        cut.setContext(mockContext);

    }

    @Test
    public void testNoBangsDuel() throws Exception {
        // given opponent and player don't have bangs, opponent wins
        when(mockOpponent.forceDiscard(eq(BangCard.class),anyListOf(PlayingCard.class))).thenReturn(false);
        when(mockPlayer.forceDiscard(eq(BangCard.class),anyListOf(PlayingCard.class))).thenReturn(false);
        assertTrue(cut.apply(mockOpponent)); // win
        verify(mockPlayer, never()).loseLife();
        verify(mockOpponent, times(1)).loseLife();
    }

    @Test
    public void testSameBangsDuel() throws Exception {
        // given opponent has two bangs and player has two
        when(mockOpponent.forceDiscard(eq(BangCard.class),anyListOf(PlayingCard.class))).thenReturn(true).thenReturn(true).thenReturn(false);
        when(mockPlayer.forceDiscard(eq(BangCard.class),anyListOf(PlayingCard.class))).thenReturn(true).thenReturn(true);
        assertTrue(cut.apply(mockOpponent)); // win
        verify(mockPlayer, never()).loseLife();
        verify(mockOpponent, times(1)).loseLife();
    }

    @Test
    public void testFewerBangsDuel() throws Exception {
        // given opponent has two bangs and player has one
        when(mockOpponent.forceDiscard(eq(BangCard.class),anyListOf(PlayingCard.class))).thenReturn(true).thenReturn(true);
        when(mockPlayer.forceDiscard(eq(BangCard.class),anyListOf(PlayingCard.class))).thenReturn(true).thenReturn(false);
        assertFalse(cut.apply(mockOpponent)); // lose
        verify(mockPlayer, times(1)).loseLife();
        verify(mockOpponent, never()).loseLife();
    }

}