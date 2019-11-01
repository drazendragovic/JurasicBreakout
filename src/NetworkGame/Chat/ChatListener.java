/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package NetworkGame.Chat;

/**
 *
 * @author Drazen Dragovic
 */
public interface ChatListener {
    void MessageReceived(String playerName, String message);
}
