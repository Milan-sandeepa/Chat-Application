package controller;

import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import util.SetNavigation;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ClientFormController {

    public JFXTextField txtMessage;
    public BorderPane context;
    public ScrollPane scrollPane;
    public VBox vBox;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private Client client;

    public void initialize() throws IOException {
            try {
                String userName = LoginFormController.userName;
                System.out.println("Client Server running");
                Socket socket = new Socket("localhost", 8000);
                client = new Client(socket,userName);
                client.sendUsername();
                client.receiveMessage(vBox);


            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    public void sendOnAction(MouseEvent mouseEvent) throws IOException {
        String message=txtMessage.getText();

        if (!message.isEmpty()) {
            HBox hBox = new HBox();
            hBox.setAlignment(Pos.CENTER_RIGHT);
            hBox.setPadding(new Insets(5, 5, 5, 10));

            Text text = new Text(message);
            text.setFill(Color.color(0.934, 0.945, 0.996));
            TextFlow textFlow = new TextFlow(text);
            textFlow.setStyle("-fx-color: rgb(239,242,255);" + "-fx-background-color: rgb(15,125,242);" + "-fx-background-radius: 20px");
            textFlow.setPadding(new Insets(5, 10, 5, 10));

            hBox.getChildren().add(textFlow);
            vBox.getChildren().add(hBox);

            client.sendMessage(message);
            txtMessage.clear();
        }
    }

    public static void addLabel(String msg,VBox vBox){
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.setPadding(new Insets(5,10,5,10));

        Text text=new Text(msg);
        TextFlow textFlow=new TextFlow(text);
        textFlow.setStyle("-fx-background-color: rgb(233,233,235); " +
                "-fx-background-radius: 20px");
        textFlow.setPadding(new Insets(5,10,5,10));
        hBox.getChildren().add(textFlow);

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                vBox.getChildren().add(hBox);
            }
        });
    }
}
