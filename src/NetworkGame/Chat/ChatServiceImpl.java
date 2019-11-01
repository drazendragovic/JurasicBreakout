/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package NetworkGame.Chat;

import java.rmi.RemoteException;
import javafx.scene.input.KeyEvent;

/**
 *
 * @author Drazen Dragovic
 */
public class ChatServiceImpl implements ChatService {
    
    private Chat chat;
    
    public ChatServiceImpl(Chat chat) {
        this.chat = chat;
    }

    @Override
    public void sendMessage(String playerName, String message) throws RemoteException {
        chat.notifyListeners(playerName, message);
    }
    
}
