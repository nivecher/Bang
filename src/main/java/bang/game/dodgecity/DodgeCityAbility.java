package bang.game.dodgecity;

/**
 * Created by Morgan on 12/18/2016.
 */
public enum DodgeCityAbility {

    UNAFFECTED_BY_DIAMONDS("He is unaffected by chards from the suit of Diamons played by other players"),
    // exception - Duel
    UNAFFECTED_BY_CARDS_IN_FRONT("During her turn, no card in front of any other player has any affect");
    // including blue and green cards

    private final String description;

    DodgeCityAbility(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
