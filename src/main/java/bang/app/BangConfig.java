package bang.app;

import bang.app.dodgecity.DodgeCityConfig;
import com.sun.istack.internal.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by Morgan on 12/22/2016.
 */
@Component
@ConfigurationProperties(prefix="bang")
public class BangConfig {

    @NotNull
    private int players;

    private DodgeCityConfig dodgeCity;

    public int getPlayers() {
        return players;
    }

    public void setPlayers(int players) {
        this.players = players;
    }

    public DodgeCityConfig getDodgeCity() {
        return dodgeCity;
    }

    public void setDodgeCity(DodgeCityConfig dodgeCity) {
        this.dodgeCity = dodgeCity;
    }
}


