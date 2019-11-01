/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package NetworkGame.Client;

import java.util.List;
import screens.GameScreenController;

/**
 *
 * @author Drazen Dragovic
 */
public class EventListener {

    private GameScreenController gsc;
    private List<Object> gameState;

    public void received(Object p) {
        gameState = (List<Object>) p;
        gsc = GameScreenController.getGsc();
        gsc.showGameState(gameState);
    }

}
