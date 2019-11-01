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
public class ChatClient extends Chat {

    private Registry registry;
    private String remoteName;
    private List<ChatListener> chatListeners = new ArrayList<>();
    ChatServiceImpl chatService;
    ChatService stub;

    public void StartClient() {
        try {
            chatService = new ChatServiceImpl(this);
            registry = LocateRegistry.getRegistry(22223);

            stub = (ChatService) UnicastRemoteObject.exportObject(chatService, 0);
            registry.rebind("ChatClient", stub);
        } catch (Exception e) {
            notifyListeners("", e.getMessage());
        }
    }

    @Override
    public void sendMessageToRemote(String playerName, String message) throws RemoteException {
        ChatService client;
        try {
            client = (ChatService) registry.lookup("ChatServer");
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
            registry.unbind("ChatClient");
        } catch (NotBoundException ex) {

        }
        removeChatListeners();
    }

}
