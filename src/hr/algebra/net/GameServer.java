/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra.net;

import hr.algebra.controller.ServerController;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;

/**
 *
 * @author IgorKvakan
 */
public class GameServer extends Thread {

    //interface u server
    private static final int PORT = 1089;

    private final ServerController serverController;

    // ExecutorService pool = Executors.newFixedThreadPool(2);
    public static LinkedList<ClientHandler> clients;

    private ObjectInputStream ois;
    private ObjectOutputStream oos;

    public GameServer(ServerController serverController) {
        this.serverController = serverController;
        clients = new LinkedList<>();
    }

    @Override // server sluzi samo da uspostavi konekciju i salje klijenta na handler
    public void run() {

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {

            while (true) {

                Socket clientSocket = serverSocket.accept();
                System.out.println("Client port: " + clientSocket.getPort());
                
                initIOStream(clientSocket);

                updateServerController(clientSocket);

                ClientHandler clientHandler = new ClientHandler(clientSocket,ois,oos); //proslijedim klijenta handleru
                clients.add(clientHandler);

                clientHandler.start();

                //pool.execute(clientHandler);
            }

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    ;

    private void updateServerController(final Socket clientSocket) throws IOException {

        serverController.connectionInfo(showClientConnectionInfo(clientSocket));
        serverController.connectionInfo("Connection established");

    }

    private static String showClientConnectionInfo(Socket client) {

        String message = "Client connected from port: " + client.getPort() + " " + client.getInetAddress();

        System.out.println(message);

        return message;

    }

    private void initIOStream(Socket clientSocket) throws IOException {
        oos= new ObjectOutputStream(clientSocket.getOutputStream());
        ois= new ObjectInputStream(clientSocket.getInputStream());
    }

}
