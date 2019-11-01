/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package NetworkGame.Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import screens.NetScreenController;

/**
 *
 * @author Drazen Dragovic
 */
public class Connection implements Runnable {

    private NetScreenController nsc;
    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
   

    public Connection(Socket socket) {
        this.socket = socket;

        try {
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
            nsc = nsc.getNsc();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            nsc.startGame(true);
            
            while (socket.isConnected()) {
                try {

                    Object data = nsc.getGameState();
                    sendObject(data);
                    
                    Object indata = in.readObject();

                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            in.close();
            out.close();
            socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendObject(Object packet) {
        try {
            
            out.writeObject(packet);
            out.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
