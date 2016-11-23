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
public class BeerCardTest {

    private BeerCard cut = new BeerCard(Suit.Hearts, Face.Three);
    private Player mockPlayer = mock(Player.class);

    @Before
    public void setUp() throws Exception {
        reset(mockPlayer);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void apply() throws Exception {
//        when(mockPlayer.drawCard()).then(); // TODO start here
        cut.apply(mockPlayer);
    }

}