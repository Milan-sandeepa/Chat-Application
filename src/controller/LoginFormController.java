package controller;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.layout.AnchorPane;
import util.SetNavigation;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class LoginFormController {
    BufferedReader in;
    PrintWriter out;
    String name;

    public JFXTextField txtName;
    public AnchorPane context;

    public void initialize(){
//        new Thread(() -> {
//            try {
//                Socket socket = new Socket("localhost",8000);
//
////                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
////                out = new PrintWriter(socket.getOutputStream(),true);
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }).start();
    }

    public void LoginOnAction(ActionEvent actionEvent) throws IOException {
        name = txtName.getText();

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

}
