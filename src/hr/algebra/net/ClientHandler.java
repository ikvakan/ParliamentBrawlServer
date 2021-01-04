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
    
    
//private LinkedList<ClientHandler> clients;

    public ClientHandler(Socket client, ObjectInputStream ois, ObjectOutputStream oos) {
        this.client = client;
     
        this.ois= ois;
        this.oos=oos;
        //this.clients=clients;
    }

    @Override
    public void run() {
        // receive message from client
//        String receivedMessage = "";
//
//        while (true) {
//
//            try {
//                if (dis.readUTF() == null) {
//                    break;
//                }
//
//                receivedMessage = dis.readUTF();
//                System.out.println("Received message: " + receivedMessage);
//
//                //send message to all clients
//                for (ClientHandler clientHandler : GameServer.clients) {
//                    //add message to dataoutputstream
//                    clientHandler.dos.writeUTF(receivedMessage);
//                    
//
//                    if (client.isConnected()) {
//                        if (client.isClosed()) {
//                            GameServer.clients.remove(clientHandler);
//                        }
//                    }
//                }
//                   
//
//            } catch (IOException ex) {
//                System.out.println(client + " has disconnected.");
//                ex.printStackTrace();
//                GameServer.clients.remove(this);
//                try {
//                    client.close();
//                } catch (IOException ex1) {
//                    Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex1);
//                }
//                break;
//
//            }
//
//        }

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

        // DataOutputStream dos = new DataOutputStream(client.getOutputStream());
        
        GameStateModel gameStateModel=(GameStateModel) ois.readObject();
        

        for (ClientHandler client : GameServer.clients) {

            client.oos.writeObject(gameStateModel);

            client.oos.flush();

        }

    }
    
    
    
    
//    private void sendToClient() throws IOException {
//
//        //DataOutputStream dos = new DataOutputStream(client.getOutputStream());
//
//        String recieved;
//        try {
//            recieved = dis.readUTF();
//            
//            dos.writeUTF(recieved);
//            System.out.println("Send to client:" + recieved);
//            dos.flush();
//
//        } catch (IOException ex) {
//            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        
//    }

}
