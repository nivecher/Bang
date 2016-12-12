package bang.game;

import bang.PlayerController;
import bang.game.cards.BarrelCard;
import bang.game.cards.JailCard;
import bang.game.cards.PlayingCard;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * Bang player
 *
 * @author Morgan
 */
public class Player implements Consumer<PlayingCard> {

    private final Role role;
    private final List<PlayingCard> hand; // cards hidden from other players
    private final PlayingBoard board; // cards in play (on the table)
    private PlayerController controller;
    private Character character;
    private int numLives; // bullets / life-points
    private int drawsPerTurn = 2; // default
    private int cardsToDraw = getDrawsPerTurn();
    private boolean isTurn = false;
    private boolean isPassing = false;
    private boolean isUnderAttack = false;
    private boolean abiityActivated = false;

    /**
     * Construct a new player with a specific role
     *
     * @param role
     */
    public Player(Role role) {
        this.role = role;
        this.hand = new ArrayList<>();
        this.board = new PlayingBoard();
        // TODO move to set role?
        if (role == Role.Sheriff) this.isTurn = true; // sheriff starts
    }

    /**
     * Resets the player to its initial state (at the beginning of the game)
     */
    public void reset() {
        hand.clear();
        board.reset();
        isTurn = false;
        isPassing = false;
        character = null;
        abiityActivated = false;
        cardsToDraw = getDrawsPerTurn();
    }

    /**
     * Return the max number of lives (bullets) of this player based on his/her
     * role / character
     *
     * @return max lives (bullets)
     */
    public int getMaxLives() {
        int lives = character.getNumBullets();
        if (role == Role.Sheriff) {
            lives++;
        }
        return lives;
    }

    /**
     * Get player's role
     * @return player role
     */
    public Role getRole() {
        return role;
    }

    /**
     * Returns whether or not it is this player's turn
     * @return true if it's the player's turn, false otherwise
     */
    public boolean isTurn() {
        return isTurn;
    }


    /**
     * Return the number of lives the player has left
     *
     * @return number of lives left
     */
    public int getNumLives() {
        return numLives;
    }

    /**
     * Returns the player's character
     * @return
     */
    public Character getCharacter() {
        return character;
    }

    /**
     *
     * @param character
     */
    public void setCharacter(Character character) {
        this.character = character;
        this.abiityActivated = false;
        this.numLives = getMaxLives(); // reset lives
    }

    /**
     * Player is shot (once) and if hit, loses one life
     *
     * @return true if hit, false if missed
     */
    public boolean bang() {
        isUnderAttack = true;
        // Has miss ability (character, cards)?
        if (controller.avoidHit()) {
            return false;
        }
        loseLife();
        return true; // hit
    }

    /**
     * Removes the first card from the list and  adds it to the player's hand
     *
     * @param cards cards from which to draw
     * @return card drawn
     */
    public PlayingCard drawCard(List<PlayingCard> cards) {
        PlayingCard draw = cards.remove(0);
        hand.add(draw); // TODO handle shuffling and rebuilding deck
        cardsToDraw--;
        return draw;
    }

    /**
     * Accept a card into the player's hand
     *
     * @param card new card
     * @return true if added to the player's hand
     * @throws IllegalArgumentException if the card is a jail card and this player is
     * playing the Sheriff role
     */
    @Override
    public void accept(PlayingCard card) {
        if (card instanceof JailCard) { // TODO handle accepting a card vs. being put in jail
            if (role == Role.Sheriff) {
                throw new IllegalArgumentException("The Sheriff cannot be put in Jail!");
            }
            board.addCard(card);
        }
        hand.add(card);
    }

    /**
     * Determine's if a player is in Jail or not
     * @return true if the player has a JailCard on his/her board
     */
    public boolean isInJail() {
        return PlayingCard.findCard(JailCard.class, board.getCards()) != null;
    }

    /**
     * Starts the player's turn and resets any turn specific data
     */
    public void startTurn() {
        cardsToDraw = getDrawsPerTurn();
        isTurn = true;
        // TODO DodgeCity: handle green cards being playable
    }

    /**
     * Return the number of times the player draws for each turn
     * @return
     */
    private int getDrawsPerTurn() {
        return drawsPerTurn; // TODO handle other characters
    }

    public boolean canDraw() {
        return (isTurn && cardsToDraw > 0);
    }

    /**
     * Returns whether the player can play cards
     *
     * @return true if the player has started his/her turn and has drawn
     */
    public boolean canPlay() {
        return (isTurn && cardsToDraw == 0);
    }

    /**
     * Returns whether the player can pass
     *
     * @return true if this player has started his/her turn and is not passing and can or is under attack
     */
    public boolean canPass() {
        return (isTurn && cardsToDraw == 0 && !isPassing || isUnderAttack);
    }

    /**
     * Returns whether the player can discard
     *
     * @return true if this player has started his/her turn, is passing and has cards to discard
     */
    public boolean canDiscard() {
        return (isTurn && isPassing && cardsToDiscard() > 0);
    }

    /**
     * Returns whether the player can end his/her turn
     *
     * @return true if this player has started his/her turn and has no cards to discard
     */
    public boolean canEndTurn() {
        int discards = cardsToDiscard();
        return (isTurn && cardsToDraw == 0 && discards == 0);
    }

    /**
     * Player is passing (i.e. ending his/her turn)
     *
     * @return 0 if turn also ended, otherwise the number of cards to discard to end the turn
     */
    public int pass() {
        isPassing = true;
        isUnderAttack = false;
        if (canEndTurn()) {
            endTurn();
            return 0;
        }
        return cardsToDiscard(); // cards to discard
    }

    /**
     * Return the number of cards to discard in order to pass
     *
     * @return
     */
    public int cardsToDiscard() {
        return Math.max(hand.size() - getMaxCards(), 0);
    }

    /**
     * Return the number of cards left to draw for this turn
     * @return int cards left to draw
     */
    public int getCardsToDraw() {
        return cardsToDraw;
    }

    /**
     * Player is ending his/her turn (after passing or forced to end)
     */
    public void endTurn() {
        if (!canEndTurn()) {
            throw new IllegalStateException("Cannot end turn");
        }
        isTurn = false;
        isPassing = false;
        isUnderAttack = false;
        abiityActivated = false;
        cardsToDraw = getDrawsPerTurn();
    }

    /**
     * Place card on player's playing board
     *
     * @param card to put in play
     * @return true if card added to board, false otherwise
     */
    public boolean playCardOnBoard(PlayingCard card) {
        return (hand.remove(card) && board.addCard(card));
    }

    /**
     * Determine the distance the player can target based on his/her
     * current weapon, cards in play on the playing board and character ability
     *
     * @return positive integer representing the distance a player can reach
     */
    public int getTargetDistance() {
        return board.getWeapon().getDistance() + board.getTargetModDelta()
                + (character != null ? character.getDecrease() : 0);
    }

    /**
     * Determine the distance the player is viewable or can be "seen" from
     * relative to other players based on cards in play on the playing board and
     * character ability
     *
     * @return positive integer representing the delta in distance a player can
     * be seen by another player
     */
    public int getViewableDistanceDelta() {
        return board.getViewableModDelta()
                + (character != null ? character.getIncrease() : 0);
    }

    /**
     * Discards a card from hand and adds it to the discard pile
     *
     * @param card        discard
     * @param discardPile pile
     * @return true if successfully removed from hand and added to the discard pile
     */
    public boolean discardCard(PlayingCard card, List<PlayingCard> discardPile) {
        boolean discarded = (hand.remove(card) && discardPile.add(card));
        if (isPassing && canEndTurn()) {
            endTurn(); // TODO automatically end turn if this is the last move?
        }
        return discarded;
    }

    /**
     * Discards a card from the player's board and adds it to the discard pile
     *
     * @param card        discard
     * @param discardPile pile
     * @return true if successfully removed from the player's board and added to the discard pile
     */
    public boolean discardFromBoard(PlayingCard card, List<PlayingCard> discardPile) {
        return board.removeCard(card) && discardPile.add(card);
    }

    /**
     * Returns a copy of the player's hand
     *
     * @return mutable copy of the player's hand
     */
    public List<PlayingCard> getHand() {
        return new ArrayList<>(hand);
    }

    /**
     * Returns a new list of cards in the player's hand and on the player's board
     *
     * @return mutable copy of all player's cards
     */
    public List<PlayingCard> getCards() {
        List<PlayingCard> cards = new ArrayList<>(hand);
        cards.addAll(board.getCards());
        return cards;
    }

    /**
     * Add a life to the player's lives if not already at max lives
     *
     * @return true if life added, false otherwise
     */
    public boolean regainLife() {
        if (numLives == getMaxLives()) {
            return false;
        }
        numLives++;
        return true;
    }

    /**
     * Loses one life point
     *
     * @return true if still alive, false otherwise
     */
    public boolean loseLife() {
        numLives--;
        return numLives > 0;
    }

    /**
     * Returns a new list of all the barrel cards in play on the player's board
     * @return list of barrel cards in effect
     */
    public List<PlayingCard> getBarrelsInPlay() {
        return board.findObjectCardsByType(BarrelCard.class);
    }

    /**
     * Returns whether the player has drawn (at least once) for the current turn
     *
     * @return true if the player has drawn (at least once)
     */
    public boolean hasDrawn() {
        return cardsToDraw < getDrawsPerTurn();
    }

    public PlayerController getController() {
        return controller;
    }

    /**
     * Force the player to discard a card of a certain type / class or lose a life
     *
     * @param clazz       type of card to discard
     * @param discardPile pile onto which card is added
     * @return true if a card is discarded, false if player does not have a card of that type and loses a life
     */
    public boolean forceDiscard(Class<? extends PlayingCard> clazz, List<PlayingCard> discardPile) {

        if (controller.forceDiscard(clazz)) {
            return true;
        }

        loseLife();
        return false; // hit
    }

    /**
     * Selects a card from the list and removes it from the list and adds it to the player's hand
     * (similar to accept but with a choice)
     * @param cards list of cards to take
     * @return true if card added to hand
     */
    public boolean selectCard(List<PlayingCard> cards) {
        PlayingCard card = controller.select(cards);
        cards.remove(card);
        return hand.add(card);
    }

    /**
     * Set the player controller
     *
     * @param controller
     */
    public void setController(PlayerController controller) {
        this.controller = controller;
    }

    /**
     * Sets the player's ability (if any to activated)
     */
    public void activateAbility() {
        abiityActivated = true;
        // TODO improve ability handling
    }

    public int getMaxCards() {
        if (character.getAbility() == Ability.CAN_KEEP_TEN_CARDS) return 10;
        return numLives;
    }

}
