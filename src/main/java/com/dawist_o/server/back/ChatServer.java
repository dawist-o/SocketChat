package com.dawist_o.server.back;

import com.dawist_o.client.model.Message;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

public class ChatServer {
    private List<ObjectOutputStream> outputStreams;
    private int port;

    public ChatServer(String ip, int port) {
        this.port = port;
    }

    public ChatServer() {
        this.port = 4242;
    }

    public void start() throws IOException {
        outputStreams = new LinkedList<>();

        ServerSocket serverSocket = new ServerSocket(port);
        while (true) {
            Socket socket = serverSocket.accept();

            ObjectOutputStream writer = new ObjectOutputStream(socket.getOutputStream());
            outputStreams.add(writer);

            Thread thread = new Thread(new ClientHandler(socket));
            thread.start();

            System.out.println("new client connected");

        }
    }

    private void tellEveryone(Message message) {
        System.out.println("Server tell everyone" + message);
        for (ObjectOutputStream writer : outputStreams) {
            try {
                writer.writeObject(message);
                writer.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private class ClientHandler implements Runnable {

        private ObjectInputStream reader;
        private Socket socket;

        public ClientHandler(Socket socket) {
            this.socket = socket;
            try {
                //InputStreamReader streamReader = new InputStreamReader(socket.getInputStream());
                reader = new ObjectInputStream(socket.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            try {
                Message message;
                while ((message = (Message) reader.readObject()) != null) {
                    tellEveryone(message);
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
