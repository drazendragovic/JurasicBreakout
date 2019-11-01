/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package NetworkGame.Chat;

import java.io.Closeable;
import java.rmi.RemoteException;

/**
 *
 * @author Drazen Dragovic
 */
public abstract class Chat implements Closeable {
    public abstract void sendMessageToRemote(String playerName, String message) throws RemoteException;
    public abstract void notifyListeners(String playerName, String message);
    public abstract void addChatListener(ChatListener listener);
}
