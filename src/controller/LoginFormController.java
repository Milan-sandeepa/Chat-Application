package controller;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.layout.AnchorPane;
import util.SetNavigation;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class LoginFormController  {
    public static String userName;

    public JFXTextField txtName;
    public AnchorPane context;

    public void LoginOnAction(ActionEvent actionEvent) throws IOException {
        userName = txtName.getText();
        SetNavigation.setUI("ClientForm","Client",context);
    }

}
