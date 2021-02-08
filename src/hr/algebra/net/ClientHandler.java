/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra.net;

import hr.algebra.model.GameStateModel;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author IgorKvakan
 */
public class ClientHandler extends Thread {

    private static Socket client;
    private LinkedList<ClientHandler> clients;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;

    public ClientHandler(Socket client, ObjectInputStream ois, ObjectOutputStream oos,LinkedList<ClientHandler> clients) {
        this.client = client;
        this.ois = ois;
        this.oos = oos;
        this.clients=clients;
    }

    @Override
    public void run() {

        try {
            while (true) {

                sendToClient();

            }

        } catch (IOException ex) {
            try {
                oos.close();
                clients.remove(this);

                ex.printStackTrace();

            } catch (IOException ex1 ) {
                Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex1);
               

            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
            

        }

    }

    private  void sendToClient() throws IOException, ClassNotFoundException {

        GameStateModel gameStateModel = (GameStateModel) ois.readObject();

        for (ClientHandler client : clients) {

            client.oos.writeObject(gameStateModel);
            client.oos.flush();

        }

    }

}
