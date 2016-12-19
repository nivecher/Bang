package bang.game.dodgecity;

import bang.game.Character;
import bang.game.cards.PlayingCard;
import bang.game.cards.Suit;

/**
 * Created by Morgan on 12/18/2016.
 */
public class DodgeCityCharacter extends Character {

    private final DodgeCityAbility ability;

    public DodgeCityCharacter(String name, DodgeCityAbility ability, int numBullets) {
        super(name, null, numBullets); // TODO fix this
        this.ability = ability;
    }

    /**
     * TODO handle this
     *
     * @param card
     * @return
     */
    @Override
    public boolean cardHasEffect(PlayingCard card) {

        if (ability == DodgeCityAbility.UNAFFECTED_BY_DIAMONDS && card.getSuit() == Suit.Diamonds) {
            return false;
        }

        return super.cardHasEffect(card);
    }
}
