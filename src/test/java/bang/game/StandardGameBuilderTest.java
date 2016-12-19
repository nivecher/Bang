package bang.game;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Morgan on 8/21/2016.
 */
public class StandardGameBuilderTest {

    private StandardGameBuilder cut = new StandardGameBuilder(4);

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testGenerateRoles() throws Exception {
        // TODO test roles
    }

    @Test
    public void testGenerateCharacters() throws Exception {
        // TODO test characters
    }

    @Test
    public void testGeneratePlayingCards() throws Exception {
        assertEquals(80, cut.generatePlayingCards().size());
    }
}