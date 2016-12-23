package bang.game;

import bang.game.cards.*;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;

/**
 * Created by Morgan on 12/18/2016.
 */
public class PlayingBoardTest {

    private PlayingBoard cut = new PlayingBoard();
    private PlayingContext mockContext = mock(PlayingContext.class);

    @Before
    public void setUp() throws Exception {
        reset(mockContext);
        cut.reset();
    }

    @Test
    public void testSetWeapon() throws Exception {
        WeaponCard weapon = new WeaponCard("test weapon", 2);
        assertNull(cut.setWeapon(weapon));
        assertEquals(weapon, cut.setWeapon(new WeaponCard("another weapon", 3)));
    }

    @Test
    public void testFindObjectCardsByType() throws Exception {

    }

    @Test
    public void testGetTurnCards() throws Exception {
        JailCard jailCard = new JailCard(Suit.Diamonds, Face.Five);
        WeaponCard weaponCard1 = new WeaponCard("gun", 4);
        WeaponCard weaponCard2 = new WeaponCard("knife", 1);
        DynamiteCard dynamiteCard = new DynamiteCard(Suit.Clubs, Face.Nine);
        BarrelCard barrelCard = new BarrelCard();
        jailCard.setContext(mockContext);
        cut.addCard(jailCard);
        weaponCard1.setContext(mockContext);
        cut.addCard(weaponCard1);
        weaponCard2.setContext(mockContext);
        cut.addCard(weaponCard2);
        dynamiteCard.setContext(mockContext);
        cut.addCard(dynamiteCard);
        barrelCard.setContext(mockContext);
        cut.addCard(barrelCard);
        List<PlayingCard> actual = cut.getTurnCards();
        assertTrue(actual.containsAll(Arrays.asList(dynamiteCard, jailCard)));
        assertFalse(actual.contains(weaponCard1));
        assertFalse(actual.contains(weaponCard2));
        assertFalse(actual.contains(barrelCard));
        assertEquals(2, actual.size());
        assertEquals(dynamiteCard, actual.get(0)); // play dynamite first
        assertEquals(jailCard, actual.get(1));
    }

    // TODO DodgeCity: handle green cards

}