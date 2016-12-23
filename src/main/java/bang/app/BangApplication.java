package bang.app;

import bang.game.BangGame;
import bang.game.Player;
import bang.game.PlayingContext;
import bang.game.StandardGameBuilder;
import bang.game.cards.PlayingCard;
import bang.ui.controller.DefaultPlayerController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.logging.Logger;

/**
 * Created by Morgan on 12/11/2016.
 */
@SpringBootApplication
public class BangApplication implements CommandLineRunner {

    protected final Logger LOG = Logger.getLogger(getClass().getName());

    @Autowired
    private final BangConfig bangConfig;

    public BangApplication(BangConfig bangConfig) {
        this.bangConfig = bangConfig;
    }

    public static void main(String[] args) {
        SpringApplication.run(BangApplication.class, args);
    }

    @Override
    public void run(String... strings) throws Exception {
        BangGame game = new StandardGameBuilder(bangConfig.getPlayers()).
                addController(new DefaultPlayerController()).
                addController(new DefaultPlayerController()).
                addController(new DefaultPlayerController()).
                addController(new DefaultPlayerController()).
                create();

        LOG.info("Starting " + game.getPlayers().size() + " player game");
        Player sheriff = game.getSheriff();

        LOG.info("Sheriff is starting");
        assert sheriff != null;
        sheriff.startTurn();

        List<PlayingCard> cards = sheriff.getController().draw();
        LOG.info("Sherrif is drew: " + cards);
        cards.forEach(c -> {
            c.play();
            LOG.info("Sherrif played: " + c);
        });

        // TODO implement mock game
        LOG.info("Sherrif's turn is over");
        sheriff.endTurn();
    }
}