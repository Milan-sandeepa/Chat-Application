package controller;

import javafx.scene.layout.VBox;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    String name;

    public Client(Socket socket, String userName) throws IOException {
        this.socket = socket;
        this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.name = userName;
    }

    public void sendUsername() throws IOException {
        System.out.println("Joined Success " + name);
        try {
            bufferedWriter.write(name);
            bufferedWriter.newLine();
            bufferedWriter.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void receiveMessage(VBox vBox) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (socket.isConnected()) {
                    try {

                        String msgFromChat = bufferedReader.readLine();

                        ClientFormController.addLabel(msgFromChat, vBox);

                    } catch (IOException e) {
                        break;
                    }
                }
            }
        }).start();
    }

    public void sendMessage(String msg) throws IOException {
        try {
            bufferedWriter.write(name + " : " + msg);
            bufferedWriter.newLine();
            bufferedWriter.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
