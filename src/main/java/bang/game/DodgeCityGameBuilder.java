package bang.game;

import bang.game.cards.PlayingCard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Dodge City BANG! expansion
 * Includes new characters, green playing cards, 3 player game and 8 player game
 *
 * Created by Morgan on 12/18/2016.
 */
public class DodgeCityGameBuilder extends StandardGameBuilder {

    /**
     * Minimum number of controllers in the game
     */
    public static final int MIN_PLAYERS = 3;

    /**
     * Maximum number of controllers in the game
     */
    public static final int MAX_PLAYERS = 8;

    /**
     * Flag to include standard game characters / cards
     */
    private final boolean includeStandardCharacters;

    /**
     * Create a game builder
     *
     * @param numPlayers number of players / controllers
     */
    public DodgeCityGameBuilder(int numPlayers, boolean includeStandardCharacters) {
        super(numPlayers);
        this.includeStandardCharacters = includeStandardCharacters;
    }

    /**
     * Validates the number of players
     */
    protected void validatePlayers() {
        if (numPlayers < MIN_PLAYERS || numPlayers > MAX_PLAYERS) {
            throw new IllegalArgumentException("Number of controllers must be "
                    + "[" + MIN_PLAYERS + " - " + MAX_PLAYERS + "]");
        }
    }

    @Override
    public List<Role> generateRoles() {
        if (numPlayers == MIN_PLAYERS) {
            return new ArrayList<>(Arrays.asList(Role.Sheriff, Role.Renegade, Role.Outlaw));
        }
        List<Role> roles = super.generateRoles();
        if (numPlayers == MAX_PLAYERS) {
            roles.add(Role.Deputy);
        }
        return roles;
    }

    @Override
    public List<Character> generateCharacters() {
        List<Character> characters = new ArrayList<>();
        if (includeStandardCharacters) {
            characters.addAll(super.generateCharacters());
        }

        // TODO DodgeCity: add characters

        return characters;
    }

    @Override
    public List<PlayingCard> generatePlayingCards() {

        List<PlayingCard> cards = new ArrayList<>();
        cards.addAll(super.generatePlayingCards());

        // TODO DodgeCity: add cards

        return cards;
    }
}
