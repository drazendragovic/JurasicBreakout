/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package NetworkGame.Client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketException;
import screens.GameScreenController;
import screens.NetScreenController;
import sun.rmi.transport.DGCAckHandler;

/**
 *
 * @author Drazen Dragovic
 */
public class Client implements Runnable {

    private NetScreenController nsc;
    private EventListener listener;
    private String host;
    private int port;
    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private boolean running = false;

    public Client(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void connectToServer() {
        try {
            socket = new Socket(host, port);

            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
            listener = new EventListener();
            nsc = nsc.getNsc();

            new Thread(this).start();

        } catch (ConnectException e) {
            nsc.addInfoText("Unable to connect to server" + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            running = false;
            in.close();
            out.close();
            socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendObject(Object packet) {
        try {
            out.writeObject(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            running = true;
            nsc.startGame(false);

            while (running) {
                try {
                    Object data = in.readObject();
                    listener.received(data);

                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (SocketException e) {
                    close();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
