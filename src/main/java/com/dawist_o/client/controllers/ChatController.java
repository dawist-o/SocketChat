package com.dawist_o.client.controllers;

import com.dawist_o.client.util.Client;
import com.dawist_o.client.util.Message;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;


import java.io.*;
import java.net.Socket;

public class ChatController {
    @FXML
    public TextArea textArea;
    @FXML
    private TextField msgField;

    public void setController(StageController stageController) {
        stageController.getCurrentStage().setOnCloseRequest(event -> {
            try {
                System.out.println("Closing sockets");
                writer.close();
                reader.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private Client client;
    private ObjectOutputStream writer;
    private ObjectInputStream reader;
    private Socket socket;

    public void init(Client client, Socket socket) {
        msgField.setOnDragOver(event -> {
            if (event.getGestureSource() != msgField && event.getDragboard().hasFiles())
                event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
            event.consume();
        });

        msgField.setOnDragDropped(event -> {
            Dragboard dragboard = event.getDragboard();
            if (dragboard.hasFiles()) {
                final File file = dragboard.getFiles().get(0);
                msgField.appendText(client.getName() + " : " + file.getName() + "\n");
                sendFile(file);
            }
            event.setDropCompleted(true);
            event.consume();

        });

        this.client = client;
        this.socket = socket;
        try {
            writer = new ObjectOutputStream(socket.getOutputStream());
            new Thread(new IncomingReaderThread()).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendFile(File file) {
        try {
            writer.writeObject(new Message(client, file));
            writer.flush();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        msgField.setText("");
        msgField.requestFocus();
    }

    @FXML
    public void onSendButtonPressed() {
        if (msgField.getText().isBlank()) return;

        try {
            writer.writeObject(new Message(client, msgField.getText()));
            writer.flush();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        msgField.setText("");
        msgField.requestFocus();
    }

    private final String fileDirectory = System.getProperty("user.dir") + "\\storage\\";

    private class IncomingReaderThread implements Runnable {
        @Override
        public void run() {
            try {
                reader = new ObjectInputStream(socket.getInputStream());
                Message message;
                while ((message = (Message) reader.readObject()) != null) {
                    if (message.containsFile()) {
                        System.out.println("incoming:" + message);
                        try (FileInputStream in = new FileInputStream(message.getFile());
                             FileOutputStream out = new FileOutputStream(fileDirectory + message.getFile().getName())) {
                            int n;
                            while ((n = in.read()) != -1) {
                                out.write(n);
                            }
                        }
                        textArea.appendText(message.getClient().getName() + " : " + message.getFile().getName() + "\n");
                    } else {
                        textArea.appendText(message.getClient().getName() + " : " + message.getMsg() + "\n");
                    }
                }
            } catch (IOException |
                    ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}


