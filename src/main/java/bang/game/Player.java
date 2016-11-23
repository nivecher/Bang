package bang.game;

import bang.PlayerController;
import bang.game.cards.BangCard;
import bang.game.cards.BarrelCard;
import bang.game.cards.MissedCard;
import bang.game.cards.PlayingCard;

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

    public Player(Role role) {
        this.role = role;
        this.hand = new ArrayList<>();
        this.board = new PlayingBoard();
    }

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
        if (character == null) {
            throw new IllegalStateException("Character not set");
        }
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
        if (role == Role.Sheriff) this.isTurn = true; // sheriff starts
        this.numLives = getMaxLives(); // reset lives
    }

    /**
     * Player is shot (once)
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

    public PlayingCard drawCard(List<PlayingCard> cards) {
        PlayingCard draw = cards.remove(0);
        hand.add(draw); // TODO handle shuffling and rebuilding deck
        cardsToDraw--;
        return draw;
    }

    public void startTurn() {
        cardsToDraw = getDrawsPerTurn();
        isTurn = true;
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

    public boolean discardCard(PlayingCard card, List<PlayingCard> discardPile) {
        return hand.remove(card) && discardPile.add(card);
    }

    public boolean discardFromBoard(PlayingCard card, List<PlayingCard> discardPile) {
        return board.removeCard(card) && discardPile.add(card);
    }

    public List<PlayingCard> getHand() {
        return new ArrayList<>(hand);
    }

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
        // TODO handle case when beer has no effect (2 players left)
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

    public boolean forceDiscard(Class<? extends PlayingCard> clazz, List<PlayingCard> discardPile) {
        PlayingCard card = PlayingCard.findCard(clazz, hand);

        if (card != null) {
            discardCard(card, discardPile);
        }
        return false;
    }
}
