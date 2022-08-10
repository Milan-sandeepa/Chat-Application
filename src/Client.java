import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;

    public Client(Socket socket) throws IOException {
        this.socket = socket;
        this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        this.bufferedReader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    public void sendMessage() throws IOException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String chat;
                System.out.println("Client Server running");
                try {
                    Scanner scanner = new Scanner(System.in);
                    while (socket.isConnected()){
                        String msg=scanner.nextLine();
                        bufferedWriter.write(msg);
                        bufferedWriter.newLine();
                        bufferedWriter.flush();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void listenMassage(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                String chat;
                while (socket.isConnected()){
                    try {
                        chat = bufferedReader.readLine();
                        System.out.println(chat);
                        // msgSend(msgclient);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost",8000);
        Client client = new Client(socket);
        client.sendMessage();
        client.listenMassage();
    }

}
