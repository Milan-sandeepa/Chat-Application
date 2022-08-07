import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;

public class ServerApp {
    private static final int PORT=8000;
    private static HashSet<String>names = new HashSet<>();
    private static HashSet<PrintWriter>writers=new HashSet<>();

    public static void main(String[] args) throws IOException {

        ServerSocket serverSocket = new ServerSocket(PORT);
        System.out.println("Server is running in port: " + PORT);

        try {
            while (true){

                Socket localSocket = serverSocket.accept();
                System.out.println("Client accepted..!");

                Thread handlerThread = new Thread(new Handler(localSocket));
                handlerThread.start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            serverSocket.close();
        }
    }

    private static class Handler implements Runnable{

        private String name;
        private Socket socket;
        private BufferedReader in;
        private PrintWriter out;

        public Handler(Socket socket){
            this.socket=socket;
        }

        @Override
        public void run() {
            try {
                in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out=new PrintWriter(socket.getOutputStream(),true);

                while (true){
                    out.println("SUBMINAME");
                    name= in.readLine();

                    if (name == null){
                        return;
                    }
                    if (!names.contains(name)){
                        names.add(name);
                        break;
                    }
                }
                out.println("NAMEACCEPTED");
                writers.add(out);

                while (true){
                    String input = in.readLine();
                    if (input == null){
                        return;
                    }
                    for (PrintWriter writer :writers){
                        writer.println("MESSAGE" + name + ": " + input);
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                if (names!= null){
                    names.remove(name);
                }
                if (out!=null){
                    writers.remove(out);
                }
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }
}
