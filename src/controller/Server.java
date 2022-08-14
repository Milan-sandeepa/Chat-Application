package controller;

import javafx.scene.layout.VBox;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;


import static controller.ClientHandler.writers;

public class Server {
    private ServerSocket serverSocket;
    private Socket socket;
    private BufferedWriter bufferedWriter;
    public VBox vBox;

    public Server(ServerSocket serverSocket, VBox vBox) throws IOException {
        this.serverSocket = serverSocket;
        this.vBox = vBox;
    }

    public void startServer() throws IOException {
        try {
            while (!serverSocket.isClosed()) {
                socket = serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler(socket);

                Thread thread = new Thread(clientHandler);
                thread.start();

            }

            System.out.println("Thread start after");

        } catch (IOException e) {
            closeServerSocket();
        }
    }

    public void closeServerSocket() {
        try {
            if (serverSocket != null) {
                serverSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessages(String msg) throws IOException {
        if (!writers.isEmpty()) {
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            for (BufferedWriter out : writers
            ) {
                try {
                    out.write("server : " + msg);
                    out.newLine();
                    out.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void receiveMessage() throws IOException {
        ServerDashboardController.addLabel("Server Started", vBox);

        Receiving receiving = new Receiving();
        Thread thread = new Thread(receiving);
        thread.start();
    }

    private class Receiving implements Runnable {

        @Override
        public void run() {
            while (!serverSocket.isClosed()) {
                String msg = ClientHandler.input;
                if (msg != null) {
                    ServerDashboardController.addLabel(msg, vBox);
                    ClientHandler.input = null;
                }

            }
        }
    }
}
