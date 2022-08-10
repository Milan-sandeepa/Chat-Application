import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

public class Server {
    private ServerSocket serverSocket;
    private Socket socket;
    private static HashSet<String> names = new HashSet<String>();
    private static HashSet<BufferedWriter> writers = new HashSet<BufferedWriter>();

    public Server(ServerSocket serverSocket) throws IOException {
        this.serverSocket = serverSocket;
    }

    public void startServer() throws IOException {
        System.out.println("Server is Running");
        try {
            while (!serverSocket.isClosed()) {
                Socket socket = serverSocket.accept();
                System.out.println("A new Client Connected");
                ClientHandler clientHandler = new ClientHandler(socket);
                clientHandler.sendMessage();
                clientHandler.listenMassage();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8000);
        Server server = new Server(serverSocket);
        server.startServer();
    }

    private class ClientHandler {
        private Socket socket;
        private BufferedReader bufferedReader;
        private BufferedWriter bufferedWriter;
        String name = null;

        public ClientHandler(Socket socket) throws IOException {
            this.socket = socket;
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        }

        public void sendMessage() throws IOException {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Scanner scanner = new Scanner(System.in);
                        while (socket.isConnected()) {
                            String msg = scanner.nextLine();
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

        public void listenMassage() {
            new Thread(() -> {


                try {

                    while (socket.isConnected()) {

                        name = bufferedReader.readLine();
                        if (name == null) {
                            return;
                        }
                        if (!names.contains(names)) {
                            names.add(name);
                            break;
                        }
                    }

                    bufferedWriter.write("server :" + "Name Accepted");
                    bufferedWriter.newLine();
                    bufferedWriter.flush();
                    writers.add(bufferedWriter);

                    while (true) {

                        String input = bufferedReader.readLine();
                        if (input == null) {
                            return;
                        }
                        if (names.contains(name)) {

                            System.out.println(name + " :" + input);

                        }
                            for (BufferedWriter out : writers
                            ) {

                                if (!out.equals(bufferedWriter)){
                                    out.write(name + " :" + input);
                                    out.newLine();
                                    out.flush();
                                }

                            }

                    }

                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (names != null) {
                        names.remove(name);
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
    }

}
