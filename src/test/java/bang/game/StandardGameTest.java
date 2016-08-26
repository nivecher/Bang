package bang.game;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by Morgan on 8/21/2016.
 */
public class StandardGameTest {
    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testGame() throws Exception {
        BangGame game = new BangGame(4);
        game.buildCharacterList();
        game.buildPlayingDeck();
    }
}