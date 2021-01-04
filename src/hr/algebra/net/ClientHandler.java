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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author IgorKvakan
 */
public class ClientHandler extends Thread {

    private static Socket client;

    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    
    
    public ClientHandler(Socket client, ObjectInputStream ois, ObjectOutputStream oos) {
        this.client = client;
        this.ois= ois;
        this.oos=oos;
       
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
                GameServer.clients.remove(this);
                
                ex.printStackTrace();
                
            } catch (IOException ex1) {
                Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex1);
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
        } 

    }

    private void sendToClient() throws IOException, ClassNotFoundException {

        GameStateModel gameStateModel=(GameStateModel) ois.readObject();
        

        for (ClientHandler client : GameServer.clients) {

            client.oos.writeObject(gameStateModel);

            client.oos.flush();

        }

    }
    
    
    
    

}
