package bang.game;

import bang.game.cards.*;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Morgan on 12/18/2016.
 */
public class PlayingBoardTest {

    private PlayingBoard cut = new PlayingBoard();

    @Before
    public void setUp() throws Exception {
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
    public void testGetEffectCards() throws Exception {
        JailCard jailCard = new JailCard(Suit.Diamonds, Face.Five);
        WeaponCard weaponCard1 = new WeaponCard("gun", 4);
        WeaponCard weaponCard2 = new WeaponCard("knife", 1);
        DynamiteCard dynamiteCard = new DynamiteCard(Suit.Clubs, Face.Nine);
        BarrelCard barrelCard = new BarrelCard();
        cut.addCard(jailCard);
        cut.addCard(weaponCard1);
        cut.addCard(weaponCard2);
        cut.addCard(dynamiteCard);
        cut.addCard(barrelCard);
        List<PlayingCard> actual = cut.getEffectCards();
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