package com.dawist_o.server;

import com.dawist_o.client.util.Message;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

public class ChatServer {
    private List<ObjectOutputStream> outputStreams;

    public static void main(String[] args) {
        new ChatServer().start();
    }

    public void start() {
        outputStreams = new LinkedList<>();
        try {
            ServerSocket serverSocket = new ServerSocket(4242);
            while (true) {
                Socket socket = serverSocket.accept();

                ObjectOutputStream writer = new ObjectOutputStream(socket.getOutputStream());
                outputStreams.add(writer);

                Thread thread = new Thread(new ClientHandler(socket));
                thread.start();

                System.out.println("new client connected");
            }

        } catch (IOException e) {
            e.printStackTrace();
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
