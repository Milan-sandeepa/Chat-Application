package controller;


import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
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
    public Pane emojiPanel;
    public GridPane gridPane;
    private String message;
    private Server server;
    private JFXButton button1, button2, button3, button4, button5;

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

        emojiPanel.setVisible(false);


        vBox.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                scrollPane.setVvalue((Double) newValue);
            }
        });

        String emoji1 = String.format("%c", 0x1F600);
        String emoji2 = String.format("%c", 0x1F602);
        String emoji3 = String.format("%c", 0x1F605);
        String emoji4 = String.format("%c", 0x1F608);
        String emoji5 = String.format("%c", 0x1F60D);

        button1 = new JFXButton(emoji1);
        button2 = new JFXButton(emoji2);
        button3 = new JFXButton(emoji3);
        button4 = new JFXButton(emoji4);
        button5 = new JFXButton(emoji5);

        gridPane.setPadding(new Insets(2, 2, 2, 0));
        gridPane.setAlignment(Pos.CENTER_LEFT);
        gridPane.setPadding(new Insets(2, 15, 2, 0));

        gridPane.setVgap(5);
        gridPane.setHgap(5);

        gridPane.add(button1, 1, 0);
        gridPane.add(button2, 2, 0);
        gridPane.add(button3, 3, 0);
        gridPane.add(button4, 4, 0);
        gridPane.add(button5, 5, 0);

        button1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                txtMessage.insertText(txtMessage.getText().length(), emoji1);
            }
        });
        button2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                txtMessage.insertText(txtMessage.getText().length(), emoji2);
            }
        });
        button3.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                txtMessage.insertText(txtMessage.getText().length(), emoji3);
            }
        });
        button4.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                txtMessage.insertText(txtMessage.getText().length(), emoji4);
            }
        });
        button5.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                txtMessage.insertText(txtMessage.getText().length(), emoji5);
            }
        });

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

    public void emojiHiddenOnAction(MouseEvent mouseEvent) {
        emojiPanel.setVisible(false);
    }

    public void OpenEmojiOnAction(MouseEvent mouseEvent) {
        emojiPanel.setVisible(true);
    }
}
