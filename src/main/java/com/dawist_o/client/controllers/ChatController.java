package com.dawist_o.client.controllers;

import com.dawist_o.client.util.Client;
import com.dawist_o.client.util.Message;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;


import java.io.*;
import java.net.Socket;

public class ChatController {
    @FXML
    public TextArea textArea;
    @FXML
    private TextField msgField;

    public void setController(StageController stageController) {

    }

    private Client client;
    private ObjectInputStream reader;
    private ObjectOutputStream writer;

    public void init(Client client, Socket socket) {
        this.client = client;
        try {
            reader = new ObjectInputStream(socket.getInputStream());
            writer = new ObjectOutputStream(socket.getOutputStream());
            new Thread(new IncomingReaderThread()).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void onSendButtonPressed() {
        if (msgField.getText().isBlank())
            return;
        try {
            writer.writeObject(new Message(client, msgField.getText()));
            writer.flush();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        msgField.setText("");
        msgField.requestFocus();
    }

    public class IncomingReaderThread implements Runnable {

        @Override
        public void run() {
            try {
                Message message;
                while ((message = (Message) reader.readObject()) != null) {
                    System.out.println("incoming:" + message);
                    final StringBuilder chat = new StringBuilder(textArea.getText());
                    chat.append("\n").append(message.getClient().getName()).append(" : ").append(message.getMsg());
                    textArea.setText(chat.toString());
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

}
