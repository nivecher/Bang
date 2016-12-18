package bang.app;

import bang.game.BangGame;
import bang.game.Player;
import bang.game.Role;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.logging.Logger;

/**
 * Created by Morgan on 12/11/2016.
 */
@SpringBootApplication
public class BangApplication implements CommandLineRunner {

    public BangApplication() {
    }

    protected final Logger LOG = Logger.getLogger(getClass().getName());

    public static void main(String[] args) {
        SpringApplication.run(BangApplication.class, args);
    }

    @Override
    public void run(String... strings) throws Exception {
        BangGame game = new BangGame(4); // TODO make option

        game.setup();

        Player sheriff = null;

        LOG.info("Starting 4 player game");
        for (Player p : game.getPlayers()) {
            if (p.getRole() == Role.Sheriff) {
                sheriff = p;
            }
        }

        LOG.info("Sheriff is starting");
        assert sheriff != null;
        sheriff.startTurn();

        // TODO implement mock game

        sheriff.endTurn();
    }
}