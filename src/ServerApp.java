import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerApp {
    public static void main(String[] args) {
        final int PORT=8000;
        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            System.out.println("Server is running in port: " + PORT);

            Socket localSocket = serverSocket.accept();
            System.out.println("Client accepted..!");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
