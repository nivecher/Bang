/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bang;

import bang.game.BangGame;
import bang.ui.controller.DefaultPlayerController;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Morgan
 */
public class Bang {

    private final List<PlayerController> controllers = new ArrayList<>();

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new Bang().init();
    }

    public void init() {
        controllers.clear();
        BangGame game = new BangGame(4);
        game.setup();

        game.getPlayers().forEach(p -> controllers.add(new DefaultPlayerController(p, game)));

        // TODO code application logic here
    }

    public void play() {
        // TODO implement play
    }

}
