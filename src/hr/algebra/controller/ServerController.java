package hr.algebra.controller;

import hr.algebra.net.GameServer;


import hr.algebra.utils.MessageNodeUtils;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author IgorKvakan
 */
public class ServerController implements Initializable {

    private static final int RMI_PORT = 1099;

    private GameServer server;



    @FXML
    private Button btnStartServer;
    @FXML
    private VBox vBoxServerMessage;
    private static final String RMI_SERVER = "rmi://localhost:1099/remote";

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }

    @FXML
    private void handleStartServer(ActionEvent event) {
        showStartMessage();
        initServer();
        

        btnStartServer.setDisable(true);

    }

    private void initServer() {
        server = new GameServer(this);
        server.setDaemon(true);
        server.start();

    }

    private void showStartMessage() {

        String message = "Server started... " + "\n"
                + "Waiting for client" + "\n";

        showMessage(message);
    }

    public void connectionInfo(String message) {
        Platform.runLater(() -> {
            showMessage(message);

        });

    }

    private void showMessage(String message) {
        vBoxServerMessage.getChildren().add(MessageNodeUtils.createMessageLabel(message));
    }

    

}
