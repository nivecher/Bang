/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bang.game;

import bang.game.cards.PlayingCard;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * Bang player
 * @author Morgan
 */
public class Player {
    private final Role role;
    private final List<PlayingCard> hand;
    private final PlayingBoard board;
    private Character character;
    private int numLives; // bullets
    
    public Player(Role role) {
        this.role = role;
        this.hand = new ArrayList<>();
        this.board = new PlayingBoard();
    }
    
    public void reset() {
        hand.clear();
        board.reset();
        character = null;
    }
    
    /**
     * Return the max number of lives (bullets) of this player based on his/her
     * role / character
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
        this.numLives = getMaxLives(); // reset lives
    }

    /**
     * 
     * @return true if hit, false if missed
     */
    public boolean hitOrMiss() {
    
        // Has miss ability (character, cards)?
        
        
        
        return false; // TODO implement
    }

    public PlayingCard drawCard(List<PlayingCard> cards) {
        PlayingCard draw = cards.remove(0);
        hand.add(draw); // TODO handle shuffling and rebuilding deck
        return draw;
    }
    
    /**
     * 
     * @param card
     * @param player 
     */
    public void playAndDiscardCard(PlayingCard card, Consumer<PlayingCard> player) {
        player.accept(card);
        hand.remove(card);
        // TODO finish this
    }
    
    /**
     * Place card on player's playing board
     * @param card to put in play
     * @return true if card added to board, false otherwise
     */
    public boolean playCardOnBoard(PlayingCard card) {
        hand.remove(card);
        return board.addCard(card);
    }

    public boolean discardCard(PlayingCard card, List<PlayingCard> discardPile) {
        return hand.remove(card) && discardPile.add(card);
    }

    public List<PlayingCard> getHand() {
        return new ArrayList<>(hand);
    }
    
    /**
     * Add a life to the player's lives if not already at max lives
     * @return true if life added, false otherwise
     */
    public boolean regainLife() {
        if (numLives == getMaxLives()) {
            return false;
        }
        numLives++;
        return true;
    }

    public PlayingCard selectDiscard() {
        // TODO delegate to player controller (discard selector?)
        return hand.get(0);
    }
    
}
