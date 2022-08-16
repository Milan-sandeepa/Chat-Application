package controller;

import javafx.scene.layout.VBox;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;
import java.util.HashSet;

public class ClientHandler implements Runnable {
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String userName;
    private static HashSet<String> names = new HashSet<String>();
    public static HashSet<BufferedWriter> writers = new HashSet<BufferedWriter>();
    public VBox vBox;
    public static String input;
    private BufferedInputStream bufferedInputStream;
    private BufferedImage bufferedImage;


    public ClientHandler(Socket socket) throws IOException {
        this.socket = socket;
        this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.bufferedInputStream=new BufferedInputStream(socket.getInputStream());
        this.userName = bufferedReader.readLine();
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
                input = bufferedReader.readLine();
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
    }

    private void notification() {

        for (BufferedWriter out : writers
        ) {
            try {
                if (!out.equals(bufferedWriter)) {
                    out.write("server : ------ " + userName + " has entered the chat -------");
                    out.newLine();
                    out.flush();

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        input = "------ "+userName + " has entered the chat -------";
    }

    public void removeClient() throws IOException {
        try {
            for (BufferedWriter out : writers
            ) {
                if (!out.equals(bufferedWriter)) {
                    out.write("Server : ------ " + userName + " has left the chat -------");
                    out.newLine();
                    out.flush();
                }
            }

            input = "------ "+userName + " has left the chat -------";
            names.remove(userName);
            writers.remove(bufferedWriter);
            socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
