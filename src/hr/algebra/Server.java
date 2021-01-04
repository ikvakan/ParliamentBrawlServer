/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author IgorKvakan
 */
public class Server extends Application {
    
    private static final String VIEW="view/Server.fxml";
    private static final String STAGE_TITLE="Server";
    
    @Override
    public void start(Stage primaryStage) throws IOException {
        
        
      Parent root = FXMLLoader.load(getClass().getResource(VIEW));

        Scene scene = new Scene(root, 600, 400);

        primaryStage.setTitle(STAGE_TITLE);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
