/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package NetworkGame.Server;

import NetworkGame.Chat.Chat;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import screens.NetScreenController;

/**
 *
 * @author Drazen Dragovic
 */
public class Server implements Runnable {

    private NetScreenController nsc;
    private int port;
    private ServerSocket serverSocket;
    private boolean running = false;
   
    public Server(int port) {
        this.port = port;

        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start() {
        new Thread(this).start();
    }

    @Override
    public void run() {

        running = true;

        nsc = NetScreenController.getNsc();
        nsc.addInfoText("Player1 is connected!\n");

        while (running) {
            try {
                nsc.addInfoText("Waiting for Player2 to connect\n");

                Socket socket = serverSocket.accept();
                nsc.addInfoText("Player2 is connected\n");

                initSocket(socket);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        shutDown();
    }

    private void initSocket(Socket socket) {
        Connection connection = new Connection(socket);
        new Thread(connection).start();
    }

    public void shutDown() {
        running = false;
        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
