package com.dawist_o.server;

import com.dawist_o.client.controllers.StageController;
import com.dawist_o.server.back.ChatServer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ServerApp/* extends Application */{

    /*@Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(this.getClass().getResource("/server.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
    }

    public static void main(String[] args) {
        launch(args);
    }*/
    public static void main(String[] args) throws IOException {
        new ChatServer().start();
    }
}
