package bang.game.cards;

import bang.game.Player;
import bang.game.PlayingContext;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

/**
 * Created by Morgan on 11/25/2016.
 */
public class CatBalouCardTest {

    private CatBalouCard cut = new CatBalouCard(Suit.Diamonds, Face.Queen);
    private PlayingContext mockContext = mock(PlayingContext.class);
    private Player mockPlayer = mock(Player.class);
    private List<PlayingCard> mockDiscardPile = mock(List.class);

    @Before
    public void setUp() throws Exception {

        reset(mockContext);
        reset(mockPlayer);
        reset(mockDiscardPile);
        when(mockContext.getDiscardPile()).thenReturn(mockDiscardPile);
        cut.setContext(mockContext);
    }

    @Test
    public void testApply() throws Exception {

        final PlayingCard card = new PlayingCard("Fake card", Color.Blue, Suit.Hearts, Face.Four);

        when(mockContext.getTargetDiscard(mockPlayer.getCards())).thenReturn(card);
        when(mockPlayer.discardCard(card, mockDiscardPile)).thenReturn(true);
        assertTrue(cut.apply(mockPlayer));

        verify(mockPlayer, times(1)).discardCard(card, mockDiscardPile);
    }

}