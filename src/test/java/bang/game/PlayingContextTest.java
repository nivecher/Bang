package bang.game;

import bang.game.cards.Color;
import bang.game.cards.Face;
import bang.game.cards.PlayingCard;
import bang.game.cards.Suit;
import bang.ui.controller.ISelector;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Created by Morgan on 12/6/2016.
 */
public class PlayingContextTest {

    private BangGame mockGame = mock(BangGame.class);
    private Player mockPlayer1 = mock(Player.class);
    private Player mockPlayer2 = mock(Player.class);
    private Player mockPlayer3 = mock(Player.class);
    private Player mockPlayer4 = mock(Player.class);
    private PlayingContext cut;
    private List<PlayingCard> mockDiscardPile = mock(List.class);
    private List<PlayingCard> mockDrawPile = mock(List.class);

    @Before
    public void setUp() throws Exception {
        reset(mockGame);
        reset(mockPlayer1);
        reset(mockPlayer2);
        reset(mockPlayer3);
        reset(mockPlayer4);
        when(mockGame.getDiscardPile()).thenReturn(mockDiscardPile);
        when(mockGame.getDrawPile()).thenReturn(mockDrawPile);
        cut = new PlayingContext(mockGame, mockPlayer1);
    }

    @Test
    public void testGetters() throws Exception {
        List<Player> activePlayers = Arrays.asList(mockPlayer1, mockPlayer2, mockPlayer3, mockPlayer4);
        List<Player> activeOpponents = Arrays.asList(mockPlayer2, mockPlayer3, mockPlayer4);
        when(mockGame.getActivePlayers()).thenReturn(activePlayers);
        assertEquals(mockGame, cut.getGame());
        assertEquals(mockPlayer1, cut.getPlayer());
        assertEquals(mockDiscardPile, cut.getDiscardPile());
        assertEquals(mockDrawPile, cut.getDrawPile());
        assertEquals(activePlayers, cut.getActivePlayers());
        assertEquals(activeOpponents, cut.getActiveOpponents());
    }

    @Test
    public void testSetters() throws Exception {
        List<PlayingCard> discardPile = mock(List.class);
        cut.setDiscardPile(discardPile);
        assertEquals(discardPile, cut.getDiscardPile());
        List<PlayingCard> drawPile = mock(List.class);
        cut.setDrawPile(drawPile);
        assertEquals(drawPile, cut.getDrawPile());
        ISelector<PlayingCard> cardSelector = mock(ISelector.class);
        cut.setCardSelector(cardSelector);
        assertEquals(cardSelector, cut.getCardSelector());
        ISelector<Player> playerSelector = mock(ISelector.class);
        cut.setPlayerSelector(playerSelector);
        assertEquals(playerSelector, cut.getPlayerSelector());
    }

    @Test
    public void flipCard() throws Exception {
        PlayingCard card = new PlayingCard("Test card", Color.Brown, Suit.Diamonds, Face.Jack);
        when(mockDrawPile.remove(0)).thenReturn(card);
        assertEquals(card, cut.flipCard());
        verify(mockDiscardPile).add(card);
    }

}