package com.dawist_o.server.controller;

import com.dawist_o.client.controllers.StageController;
import com.dawist_o.client.model.Client;
import com.dawist_o.server.back.ChatServer;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.Socket;

public class ServerController {

    @FXML
    public TextField serverIPField;

    @FXML
    public TextField serverPortField;

    @FXML
    public void onStartButtonClicked() {
        try {
            System.out.println(Integer.parseInt(serverPortField.getText()));
            new ChatServer(serverIPField.getText(), Integer.parseInt(serverPortField.getText())).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
