/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package NetworkGame.Chat;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Drazen Dragovic
 */
public class ChatServer extends Chat {

    private Registry registry;
    private String remoteName;
    private List<ChatListener> chatListeners = new ArrayList<>();
    ChatServiceImpl chatService;
    ChatService stub;

    public void StartServer() {
        try {
            chatService = new ChatServiceImpl(this);
            registry = LocateRegistry.createRegistry(22223);

            stub = (ChatService) UnicastRemoteObject.exportObject(chatService, 0);
            registry.rebind("ChatServer", stub);
        } catch (Exception e) {

        }
    }

    @Override
    public void sendMessageToRemote(String playerName, String message) throws RemoteException {
        try {
            ChatService client = (ChatService) registry.lookup("ChatClient");
            if (client != null) {
                client.sendMessage(playerName, message);
            }
        } catch (Exception ex) {
            notifyListeners("", ex.getMessage());
        }
    }

    @Override
    public void notifyListeners(String playerName, String message) {
        for (ChatListener listener : chatListeners) {
            listener.MessageReceived(playerName, message);
        }
    }

    @Override
    public void addChatListener(ChatListener listener) {
        chatListeners.add(listener);
    }

    private void removeChatListeners() {
        chatListeners.clear();
    }

    @Override
    public void close() throws IOException {
        try {
            UnicastRemoteObject.unexportObject(chatService, true);
            registry.unbind("ChatServer");
        } catch (NotBoundException ex) {

        }
        removeChatListeners();
    }
}
