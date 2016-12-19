package bang.game;

/**
 * Special ability of a player
 *
 * @author Morgan
 */
public enum Ability {

    DRAW_ON_HIT(AbilityType.Draw, "Each time he is hit, he draws a card."),
    SHOW_2ND_DRAW_EXTRA_ON_HEART_OR_DIAMOND(AbilityType.Draw, "He shows the second card he draws.  "
            + "On Heart or Diamonds, he draws one more card."),
    BANGS_ARE_MISSED(AbilityType.ConvertCards, "She can play BANG! cards as Missed! cards and vice versa."),
    DRAW_FROM_PLAYER_ON_HIT(AbilityType.Draw, "Each time he is hit by a player, "
            + "he draws a card from the hand of that player."),
    DRAW_FIRST_FROM_PLAYER(AbilityType.Draw, "He may draw his first card from the hand of a player."),
    DRAW_ON_BANG_FOR_HEART_TO_MISS(AbilityType.AvoidHit, "Whenever he is the target of a BANG!, "
            + "he may \"draw!\": on a Heart, he is missed."),
    LOOK_AT_THREE_DRAW_TWO(AbilityType.Draw, "He looks at the top three cards of the deck and "
            + "chooses the 2 to draw."),
    FLIP_TWO_DRAW_ONE(AbilityType.Draw, "Each time he \"draws!\", he flips the op two cards and "
            + "chooses one."),
    SEEN_AT_DISTANCE_PLUS_ONE(AbilityType.Distance, "All players ee him at a distance increased by 1."),
    DRAW_FIRST_FROM_DISCARD(AbilityType.Draw, "He may draw his first card from the discard pile."),
    SEES_AT_DISTANCE_MINUS_ONE(AbilityType.Range, "She sees all players at a distance decreased by 1."),
    MAY_DISCARD_TWO_CARDS_FOR_ONE_LIFE(AbilityType.ConvertCards, "He may discard 2 cards to regain one life point."),
    NEED_TWO_MISSED_TO_CANCEL_BANG(AbilityType.BangStregth, "Players need 2 Missed! cards to cancel his BANG! card."),
    DRAWS_WHEN_HAND_IS_EMPTY(AbilityType.ConditionalDraw, "As soon as she has no cards in hand, she draws a card."),
    TAKE_CARDS_ON_ELIMINATION(AbilityType.ConditionalDraw, "Whenever a player is eliminated from play, "
            + "he takes in hand all the cards of that player."),
    CAN_PLAY_ANY_NUMBER_OF_BANG_CARDS(AbilityType.Limits, "He can play any number of BANG! cards."),
    CAN_KEEP_TEN_CARDS(AbilityType.Limits, "He may hold in his hand up to 10 cards");

    private final AbilityType type;
    private final String description;

    Ability(AbilityType type, String description) {
        this.type = type;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public AbilityType getType() {
        return type;
    }

}
