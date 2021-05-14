package com.dawist_o.client.controllers;

import com.dawist_o.client.model.Client;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.*;
import java.net.Socket;

public class LoginController {

    @FXML
    public TextField nameField;
    @FXML
    public TextField chatField;
    @FXML
    public TextField chatPort;
    private StageController stageController;

    public void setController(StageController stageController) {
        this.stageController = stageController;
    }

    @FXML
    public void onConnectButtonClicked() {

        if (nameField.getText().isBlank())
            return;
        try {
            System.out.println(chatField.getText());
            System.out.println( Integer.parseInt(chatPort.getText()));
            Socket socket = new Socket(chatField.getText(), Integer.parseInt(chatPort.getText()));
            stageController.enjoyChat(new Client(nameField.getText()),socket);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        System.out.println("network sot up");
    }

}
