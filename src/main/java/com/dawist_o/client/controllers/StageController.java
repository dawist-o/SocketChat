package com.dawist_o.client.controllers;

import com.dawist_o.client.util.Client;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.Socket;


public class StageController {
    private Stage window;

    public StageController(Stage window) {
        this.window = window;
        setLoginController();

        window.setTitle("951008 Kovalenko Vladislav");
        window.show();
    }

    public void setLoginController() {
        LoginController controller = loadFxml(LoginController.class, "/login.fxml").getController();
        controller.setController(this);
    }

    public void enjoyChat(Client client, Socket socket) {
        ChatController controller = loadFxml(ChatController.class, "/chat.fxml").getController();
        controller.init(client, socket);
        controller.setController(this);
    }

    public Stage getCurrentStage() {
        return window;
    }

    private FXMLLoader loadFxml(Class controllerClass, String path) {
        FXMLLoader loader = new FXMLLoader();
        System.out.println(controllerClass.getResource(path));
        loader.setLocation(controllerClass.getResource(path));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(root);
        window.setScene(scene);
        return loader;
    }
}
