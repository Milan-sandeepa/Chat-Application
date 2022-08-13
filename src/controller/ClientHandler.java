package controller;

import javafx.scene.layout.VBox;

import java.io.*;
import java.net.Socket;
import java.util.HashSet;
import java.util.Scanner;

public class ClientHandler implements Runnable {
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String userName;
    private static HashSet<String> names = new HashSet<String>();
    public static HashSet<BufferedWriter> writers = new HashSet<BufferedWriter>();
    public VBox vBox;

    public ClientHandler(Socket socket) throws IOException {
        this.socket = socket;
        this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.userName = bufferedReader.readLine();
    }

    public void listenMassage() {
        new Thread(() -> {
            try {
                while (socket.isConnected()) {

                    if (userName == null) {
                        return;
                    }
                    if (!names.contains(names)) {
                        names.add(userName);

                        break;
                    }
                }

//                bufferedWriter.write("server :" + "Name Accepted");
//                bufferedWriter.newLine();
//                bufferedWriter.flush();

                writers.add(bufferedWriter);
                System.out.println(userName + " joined chat");

//                while (true) {
//                    String input = bufferedReader.readLine();
//                    if (input == null) {
//                        return;
//                    }
//                    if (names.contains(userName)) {
//
//                        System.out.println(userName + " :" + input);
//
//                    }
//                    for (BufferedWriter out : writers
//                    ) {
//                        if (!out.equals(bufferedWriter)) {
//                            out.write(userName + " :" + input);
//                            out.newLine();
//                            out.flush();
//                        }
//                    }
//                }
            } finally {
                if (names != null) {
                    names.remove(userName);
                }
                if (bufferedWriter != null) {
                    writers.remove(bufferedWriter);
                }
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }).start();
    }

    @Override
    public void run() {
        while (socket.isConnected()) {

            if (userName == null) {
                return;
            }
            if (!names.contains(names)) {
                names.add(userName);

                break;
            }
        }

        writers.add(bufferedWriter);
        System.out.println(userName + " joined chat");

        notification();

        broadcast();
    }

    public void receiveMessage(VBox vBox) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (socket.isConnected()) {
                    try {

                        String msgFromChat = bufferedReader.readLine();

                        ServerDashboardController.addLabel(msgFromChat,vBox);

                        //ClientFormController.addLabel(msgFromChat,vBox);
                        // msgSend(msgclient);
                    } catch (IOException e) {
                        break;
                    }
                }
            }
        }).start();
    }

    private void broadcast() {
        try {
            while (socket.isConnected()) {
                String input = bufferedReader.readLine();
                //  ServerDashboardController.addLabel(input,vBox);
                if (input == null) {
                    return;
                }
                if (input != null) {

                    for (BufferedWriter out : writers
                    ) {
                        try {
                            if (!out.equals(bufferedWriter)) {
                                out.write(input);
                                out.newLine();
                                out.flush();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        } catch (IOException e) {
            try {
                removeClient();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
//        finally {
//            if (names != null) {
//                names.remove(userName);
//            }
//            if (bufferedWriter != null) {
//                writers.remove(bufferedWriter);
//            }
//
//            try {
//                socket.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
    }

    private void notification() {

        for (BufferedWriter out : writers
        ) {
            try {
                if (!out.equals(bufferedWriter)) {
                    out.write("server :" + userName + " has entered the chat");
                    out.newLine();
                    out.flush();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void removeClient() throws IOException {
        try {
        for (BufferedWriter out : writers
        ) {
                if (!out.equals(bufferedWriter)) {
                    out.write("controller.Server : "+userName+" has left the chat");
                    out.newLine();
                    out.flush();
                }
        }
        names.remove(userName);
        writers.remove(bufferedWriter);
        socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
