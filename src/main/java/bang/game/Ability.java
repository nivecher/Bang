/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bang.game;

/**
 *
 * @author Morgan
 */
public enum Ability {

    DRAW_ON_HIT("Each time he is hit, he draws a card."),
    SHOW_2ND_DRAW_EXTRA_ON_HEART_OR_DIAMOND("He shows the second card he draws.  "
            + "On Heart or Diamonds, he draws one more card."),
    BANGS_ARE_MISSED("She can play BANG! cards as Missed! cards and vice versa."),
    DRAW_FROM_PLAYER_ON_HIT("Each time he is hit by a player, "
            + "he draws a card from the hand of that player."), 
    DRAW_1ST_FROM_PLAYER("He may draw his first card from the hand of a player."), 
    DRAW_ON_BANG_FOR_HEART_TO_MISS("Whenever he is the target of a BANG!, "
            + "he may \"draw!\": on a Heart, he is missed."), 
    LOOK_AT_THREE_DRAW_TWO("He looks at the top three cards of the deck and "
            + "chooses the 2 to draw."), 
    FLIP_TWO_DRAW_ONE("Each time he \"draws!\", he flips the op two cards and "
            + "chooses one."), 
    SEEN_AT_DISTANCE_PLUS_ONE("All players ee him at a distance increased by 1."),
    DRAW_FIRST_FROM_DISCARD("He may draw his first card from the discard pile."), 
    SEES_AT_DISTANCE_MINUS_ONE("She sees all players at a distance decreased by 1."), 
    MAY_DISCARD_TWO_CARDS_FOR_ONE_LIFE("He may discard 2 cards to regain one life point."), 
    NEED_TWO_MISSED_TO_CANCEL_BANG("Players need 2 Missed! cards to cancel his BANG! card."), 
    DRAWS_WHEN_HAND_IS_EMPTY("As soon as she has no cards in hand, she draws a card."), 
    TAKE_CADS_ON_ELIMINATION("Whenever a player is eliminated from play, "
            + "he takes in hand all the cards of that player."), 
    CAN_PLAY_ANY_NUMBER_OF_BANG_CARDS("He can play any number of BANG! cards.");

    private final String description;

    Ability(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

}
