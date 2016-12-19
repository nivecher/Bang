package bang.app;

import bang.game.*;
import bang.ui.controller.DefaultPlayerController;
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
        BangGame game = new StandardGameBuilder(4).
                addController(new DefaultPlayerController()).
                addController(new DefaultPlayerController()).
                addController(new DefaultPlayerController()).
                addController(new DefaultPlayerController()).
                create();

        LOG.info("Starting 4 player game");
        Player sheriff = game.getSheriff();

        LOG.info("Sheriff is starting");
        assert sheriff != null;
        sheriff.startTurn();

        sheriff.getController().draw();

        // TODO implement mock game

        sheriff.endTurn();
    }
}