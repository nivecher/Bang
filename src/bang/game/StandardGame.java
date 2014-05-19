/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bang.game;

import bang.game.cards.PlayingCard;
import bang.game.cards.Suit;
import bang.game.cards.BangCard;
import bang.game.cards.BeerCard;
import bang.game.cards.CatBalouCard;
import bang.game.cards.DuelCard;
import bang.game.cards.Face;
import bang.game.cards.GatlingCard;
import bang.game.cards.GeneralStoreCard;
import bang.game.cards.IndiansCard;
import bang.game.cards.WeaponCard;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

/**
 *
 * @author Morgan
 */
public interface StandardGame {

    /**
     * Builds the standard list of characters
     *
     * @return list of characters
     */
    default List<Character> buildCharacterList() {
        List<Character> charList = new ArrayList<>(16);

        charList.add(new Character("Bart Cassidy",
                Ability.DRAW_ON_HIT, 4));
        charList.add(new Character("Black Jack",
                Ability.SHOW_2ND_DRAW_EXTRA_ON_HEART_OR_DIAMOND, 4));
        charList.add(new Character("Calamity Janet",
                Ability.BANGS_ARE_MISSED, 4));
        charList.add(new Character("El Gringo",
                Ability.DRAW_FROM_PLAYER_ON_HIT, 3));
        charList.add(new Character("Jesse Jones",
                Ability.DRAW_1ST_FROM_PLAYER, 4));
        charList.add(new Character("Jourdonnais",
                Ability.DRAW_ON_BANG_FOR_HEART_TO_MISS, 4));
        charList.add(new Character("Kit Carlson",
                Ability.LOOK_AT_THREE_DRAW_TWO, 4));
        charList.add(new Character("Lucky Duke",
                Ability.FLIP_TWO_DRAW_ONE, 4));
        charList.add(new Character("Paul Regret",
                Ability.SEEN_AT_DISTANCE_PLUS_ONE, 3));
        charList.add(new Character("Pedro Ramirez",
                Ability.DRAW_FIRST_FROM_DISCARD, 4));
        charList.add(new Character("Rose Doolan",
                Ability.SEES_AT_DISTANCE_MINUS_ONE, 4));
        charList.add(new Character("Sid Ketchum",
                Ability.MAY_DISCARD_TWO_CARDS_FOR_ONE_LIFE, 4));
        charList.add(new Character("Slab the Killer",
                Ability.NEED_TWO_MISSED_TO_CANCEL_BANG, 4));
        charList.add(new Character("Suzy Lafayette",
                Ability.DRAWS_WHEN_HAND_IS_EMPTY, 4));
        charList.add(new Character("Vulture Sam",
                Ability.TAKE_CADS_ON_ELIMINATION, 4));
        charList.add(new Character("Willy the Kid",
                Ability.CAN_PLAY_ANY_NUMBER_OF_BANG_CARDS, 4));

        return charList;
    }

    /**
     * Builds a standard deck of playing cards
     * @return list of cards
     */
    default List<PlayingCard> buildPlayingDeck() {
        List<PlayingCard> deck = new ArrayList<>();
        EnumSet.range(Face.Two, Face.Nine).stream().forEach((f) -> {
            deck.add(new BangCard(Suit.Clubs, f));
        });
        EnumSet.range(Face.Ace, Face.King).stream().forEach((f) -> {
            deck.add(new BangCard(Suit.Diamonds, f));
        });
        EnumSet.of(Face.Queen, Face.King, Face.Ace).stream().forEach((f) -> {
            deck.add(new BangCard(Suit.Hearts, f));
        });
        deck.add(new BangCard(Suit.Spades, Face.Ace));
        
        EnumSet.range(Face.Six, Face.Jack).stream().forEach((f) -> {
            deck.add(new BeerCard(Suit.Hearts, f));
        });
        
        deck.add(new CatBalouCard(Suit.Hearts, Face.King));
        
        EnumSet.range(Face.Nine, Face.Jack).stream().forEach((f) -> {
            deck.add(new CatBalouCard(Suit.Diamonds, f));
        });
        
        deck.add(new DuelCard(Suit.Clubs, Face.Eight));
        deck.add(new DuelCard(Suit.Spades, Face.Jack));
        deck.add(new DuelCard(Suit.Diamonds, Face.Queen));
        
        deck.add(new GatlingCard(Suit.Hearts, Face.Ten));
        
        deck.add(new GeneralStoreCard(Suit.Clubs, Face.Nine));
        deck.add(new GeneralStoreCard(Suit.Spades, Face.Queen));
        
        deck.add(new IndiansCard(Suit.Diamonds, Face.King));
        deck.add(new IndiansCard(Suit.Diamonds, Face.Ace));
        
        // TODO add rest of deck
        
        deck.add(new WeaponCard("Volcanic", 1, Suit.Clubs, Face.Five));
        return deck;
    }
    
}
