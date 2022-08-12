package controller;

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
    private static HashSet<BufferedWriter> writers = new HashSet<BufferedWriter>();

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

    public void sendMessage(String msg) throws IOException {
        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    while (socket.isConnected()) {
                        for (BufferedWriter out : writers
                        ) {
                            out.write("server :" + msg);
                            out.newLine();
                            out.flush();
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void broadcastMsg() throws IOException {
        for (BufferedWriter out : writers
        ) {
            if (!out.equals(bufferedWriter)) {
                out.write("server :" + userName + " has entered the chat");
                out.newLine();
                out.flush();
            }
        }
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

    private void broadcast() {
        try {
            while (socket.isConnected()) {
                String input = bufferedReader.readLine();
                if (input == null) {
                    return;
                }
                if (input != null) {
                    System.out.println(input);
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
            e.printStackTrace();
        }
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
}
