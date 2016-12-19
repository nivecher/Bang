package bang.game;

import bang.PlayerController;
import bang.game.cards.PlayingCard;

import java.util.List;

/**
 * Created by Morgan on 12/18/2016.
 */
public interface GameBuilder {

    /**
     * Add a player controller and keep building
     * @param p
     * @return
     */
    GameBuilder addController(PlayerController p);

    /**
     * Create the game with the added controllers
     * @return new BangGame
     */
    BangGame create();

    /**
     * Generates the list of roles for the game
     * @return new list or roles
     */
    List<Role> generateRoles();

    /**
     * Generates the character list for the game
     * @return new list of characters
     */
    List<Character> generateCharacters();

    /**
     * Generates the deck of playing cards for the game
     * @return new list of playing cards
     */
    List<PlayingCard> generatePlayingCards();

    /**
     * Gets the list of controllers
     * @return
     */
    List<PlayerController> getControllers();
}
