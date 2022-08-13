package controller;


import com.jfoenix.controls.JFXTextField;

import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.io.*;
import java.net.ServerSocket;

public class ServerDashboardController {

    public JFXTextField txtMessage;
    public VBox vBox;
    public ScrollPane scrollPane;
    private String message;
    private Server server;

    public void initialize() throws IOException {
        new Thread(() -> {
            try {
                System.out.println("Server is Running");
                ServerSocket serverSocket = new ServerSocket(8000);
                server = new Server(serverSocket, vBox);

                server.receiveMessage();
                server.startServer();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void sendOnAction(MouseEvent mouseEvent) throws IOException {
        message = txtMessage.getText();

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

            server.sendMessages(message);
            txtMessage.clear();

        }
    }

    public static void addLabel(String Smsg, VBox vBox) {
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.setPadding(new Insets(5, 10, 5, 10));

        Text text = new Text(Smsg);
        TextFlow textFlow = new TextFlow(text);
        textFlow.setStyle("-fx-background-color: rgb(233,233,235); " +
                "-fx-background-radius: 20px");
        textFlow.setPadding(new Insets(5, 10, 5, 10));
        hBox.getChildren().add(textFlow);

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                vBox.getChildren().add(hBox);
            }
        });
    }
}
