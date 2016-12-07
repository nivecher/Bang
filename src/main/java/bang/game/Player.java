package bang.game;

import bang.PlayerController;
import bang.game.cards.*;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * Bang player
 *
 * @author Morgan
 */
public class Player {

    private final Role role;
    private final List<PlayingCard> hand;
    private final PlayingBoard board;
    private PlayerController controller;
    private Character character;
    private int numLives; // bullets
    private int drawsPerTurn = 2; // default
    private int cardsToDraw = getDrawsPerTurn();
    private boolean isTurn = false;
    private boolean isPassing = false;
    private boolean isUnderAttack = false;

    /**
     * Construct a new player with a specific role
     * @param role
     */
    public Player(Role role) {
        this.role = role;
        this.hand = new ArrayList<>();
        this.board = new PlayingBoard();
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

    public Role getRole() {
        return role;
    }

    /**
     * Return the number of lives the player has left
     *
     * @return number of lives left
     */
    public int getNumLives() {
        return numLives;
    }

    public Character getCharacter() {
        return character;
    }

    public void setCharacter(Character character) {
        this.character = character;
        // TODO move to set role?
        if (role == Role.Sheriff) this.isTurn = true; // sheriff starts
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
     * @param card new card
     * @return true if added to the player's hand
     */
    public boolean acceptCard(PlayingCard card) {
        if (card instanceof JailCard) { // TODO better way?
            return board.addCard(card);
        }
        return hand.add(card);
    }

    /**
     * Starts the player's turn and resets any turn specific data
     */
    public void startTurn() {
        cardsToDraw = getDrawsPerTurn();
        isTurn = true;
        // TODO handle green cards
    }

    private int getDrawsPerTurn() {
        return drawsPerTurn; // TODO handle other characters
    }

    public boolean canDraw() {
        return isTurn && cardsToDraw > 0;
    }

    /**
     * Returns whether the player can play cards
     *
     * @return true if the player has started his/her turn and has drawn
     */
    public boolean canPlay() {
        return isTurn && cardsToDraw == 0;
    }

    public int getMaxCards() {
        if (character.getAbility() == Ability.CAN_KEEP_TEN_CARDS) return 10;
        return numLives;
    }

    /**
     * Returns whether the player can pass
     *
     * @return true if this player has started his/her turn and is not passing and can or is under attack
     */
    public boolean canPass() {
        return isTurn && !isPassing && hand.size() <= getMaxCards() || isUnderAttack;
    }

    /**
     * Returns whether the player can discard
     *
     * @return true if this player has started his/her turn and has cards to discard
     */
    public boolean canDiscard() {
        return isTurn && isPassing && cardsToDiscard() > 0;
    }

    /**
     * Player is passing (i.e. ending his/her turn)
     * @return number of cards to discard to end the turn
     */
    public int pass() {
        isPassing = true;
        isUnderAttack = false;
        return cardsToDiscard();
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
     * Player is ending his/her turn (after passing)
     */
    public void endTurn() {
        if (!canPass()) {
            throw new IllegalStateException("Cannot pass yet ending turn");
        }
        isTurn = false;
        isPassing = false;
    }

    /**
     * Place card on player's playing board
     *
     * @param card to put in play
     * @return true if card added to board, false otherwise
     */
    public boolean playCardOnBoard(PlayingCard card) {
        hand.remove(card);
        return board.addCard(card);
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
     * @param card discard
     * @param discardPile pile
     * @return true if successfully removed from hand and added to the discard pile
     */
    public boolean discardCard(PlayingCard card, List<PlayingCard> discardPile) {
        return hand.remove(card) && discardPile.add(card);
    }

    /**
     * Discards a card from the player's board and adds it to the discard pile
     * @param card discard
     * @param discardPile pile
     * @return true if successfully removed from the player's board and added to the discard pile
     */
    public boolean discardFromBoard(PlayingCard card, List<PlayingCard> discardPile) {
        return board.removeCard(card) && discardPile.add(card);
    }

    /**
     * Returns a copy of the player's hand
     * @return mutable copy of the player's hand
     */
    public List<PlayingCard> getHand() {
        return new ArrayList<>(hand);
    }

    /**
     * Returns a new list of cards in the player's hand and on the player's board
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

    public List<BarrelCard> getBarrels() {
        List<BarrelCard> barrels = new ArrayList<>();
        if (character.getAbility() == Ability.DRAW_ON_BANG_FOR_HEART_TO_MISS) {
            barrels.add(new BarrelCard());
        }
        BarrelCard bInPlay = board.findObjectCard(BarrelCard.class);
        if (bInPlay != null) {
            barrels.add(bInPlay);
        }
        return barrels;
    }

    public boolean hasDrawn() {
        return cardsToDraw < getDrawsPerTurn();
    }

    public PlayerController getController() {
        return controller;
    }

    /**
     * Force the player to discard a card of a certain type / class
     * @param clazz type of card to discard
     * @param discardPile pile onto which card is added
     * @return true if a card is discarded, false if player does not have a card of that type
     */
    public boolean forceDiscard(Class<? extends PlayingCard> clazz, List<PlayingCard> discardPile) {
        PlayingCard card = PlayingCard.findCard(clazz, hand);

        if (card != null) {
            return discardCard(card, discardPile);
        }
        return false;
    }

    /**
     * Selects a card from the list and removes it from the list and adds it to the player's hand
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
     * @param controller
     */
    public void setController(PlayerController controller) {
        this.controller = controller;
    }
}
