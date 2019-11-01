/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package NetworkGame.Chat;

import java.rmi.Remote;
import java.rmi.RemoteException;
import javafx.scene.input.KeyEvent;

/**
 *
 * @author Drazen Dragovic
 */
public interface ChatService extends Remote {
    public void sendMessage (String player, String message) throws RemoteException;
}
