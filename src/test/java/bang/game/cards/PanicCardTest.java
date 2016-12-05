package bang.game.cards;

import bang.game.Player;
import bang.game.PlayingContext;
import bang.ui.controller.ISelector;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Created by Morgan on 11/25/2016.
 */
public class PanicCardTest {

    private PanicCard cut = new PanicCard(Suit.Hearts, Face.Queen);
    private PlayingContext mockContext = mock(PlayingContext.class);
    private Player mockPlayer = mock(Player.class);
    private Player mockOpponent = mock(Player.class);
    private ISelector<PlayingCard> mockCardSelector = mock(ISelector.class);
    private List<PlayingCard> mockOpponentCards = mock(List.class);
    private List<PlayingCard> mockPlayerHand = mock(List.class);

    @Before
    public void setUp() throws Exception {
        reset(mockContext);
        reset(mockPlayer);
        reset(mockOpponent);
        reset(mockOpponentCards);
        reset(mockCardSelector);
        reset(mockPlayerHand);
        when(mockContext.getCardSelector()).thenReturn(mockCardSelector);
        when(mockOpponent.getCards()).thenReturn(mockOpponentCards);
        when(mockContext.getPlayer()).thenReturn(mockPlayer);
        when(mockPlayer.getHand()).thenReturn(mockPlayerHand);
        cut.setContext(mockContext);
    }

    @Test
    public void testApply() throws Exception {
        final PlayingCard card = new PlayingCard("Test", Color.Blue, Suit.Diamonds, Face.Two);
        when(mockCardSelector.select(mockOpponentCards)).thenReturn(card);
        when(mockOpponent.discardCard(card, mockPlayerHand)).thenReturn(true);
        assertTrue(cut.apply(mockOpponent));
        verify(mockOpponent).discardCard(card, mockPlayerHand); // TODO change from discard to take?
    }

}