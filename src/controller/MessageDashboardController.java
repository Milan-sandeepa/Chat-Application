package controller;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.layout.AnchorPane;
import util.SetNavigation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class MessageDashboardController {
    public AnchorPane context;
    public JFXTextField txtMessage;

    BufferedReader in;
    PrintWriter out;

    String name;

    public void initialize() throws IOException {
        Handler client = new Handler();
        client.run();

//        new Thread(() -> {
//            try {
//                Socket socket = new Socket("localhost",8000);
//
//                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//                out = new PrintWriter(socket.getOutputStream(),true);
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }).start();
//
//        while (true){
//            String line = in.readLine();
//
//            if (line.startsWith("SUBMITNAME")){
//                out.println(name);
//
//            }else if (line.startsWith("NAMEACCEPTED")){
//                SetNavigation.setUI("MessageDashboard","Dashboard",this.context);
//            }
//        }
    }

    private static class Handler implements Runnable {
        BufferedReader in;
        PrintWriter out;
        Socket socket;

        public Handler() {
            this.socket=socket;
        }

        @Override
        public void run() {
            String serverAddress="localhost";
            try {
                Socket socket = new Socket(serverAddress,8000);
                in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out=new PrintWriter(socket.getOutputStream(),true);

                while (true){
                    String line = in.readLine();
                    if (line.startsWith("SUBMITNAME")){
                        out.println(getName());
                    }else if (line.startsWith("NAMEACCEPTED")){
                        System.out.println("Success stablish");
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private String getName(){

            return "admin";
        }
    }



    public void SendOnAction(ActionEvent actionEvent) {
        String messageText = txtMessage.getText();

        txtMessage.setText("");
    }

}
