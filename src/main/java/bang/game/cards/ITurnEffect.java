package bang.game.cards;

/**
 * Created by Morgan on 12/23/2016.
 */
public interface ITurnEffect extends Comparable<ITurnEffect> {
    boolean onTurn();
}
