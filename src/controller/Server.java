package controller;

import javafx.scene.layout.VBox;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

import static controller.ClientHandler.writers;

public class Server {
    private ServerSocket serverSocket;
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;

    public Server(ServerSocket serverSocket) throws IOException {
        this.serverSocket = serverSocket;
    }

    public void startServer() throws IOException {
        try {
            while (!serverSocket.isClosed()) {
                socket = serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler(socket);

                Thread thread = new Thread(clientHandler);
                thread.start();

            }
        } catch (IOException e) {
            closeServerSocket();
        }
    }

    public void closeServerSocket(){
        try {
            if (serverSocket != null){
                serverSocket.close();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void sendMessages(String msg) throws IOException {
        this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        for (BufferedWriter out : writers
        ) {
            try {
                out.write("server :" + msg);
                out.newLine();
                out.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void receiveMessage(VBox vBox) throws IOException {

    }
}
