package bang.game;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Morgan on 12/6/2016.
 */
public class AbilityTest {
    @Test
    public void testDescription() throws Exception {
        assertEquals("Each time he is hit, he draws a card.", Ability.DRAW_ON_HIT.getDescription());
    }

}