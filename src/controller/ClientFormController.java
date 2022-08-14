package controller;

import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.FileChooser;

import java.io.*;
import java.net.Socket;

public class ClientFormController {

    public JFXTextField txtMessage;
    public BorderPane context;
    public ScrollPane scrollPane;
    public VBox vBox;
    private Client client;
    final FileChooser fileChooser = new FileChooser();

    public void initialize() throws IOException {
        try {
            String userName = LoginFormController.userName;
            System.out.println("Client running");
            Socket socket = new Socket("localhost", 8000);
            client = new Client(socket, userName);
            client.sendUsername();
            client.receiveMessage(vBox);

        } catch (IOException e) {
            e.printStackTrace();
        }

        vBox.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                scrollPane.setVvalue((Double) newValue);
            }
        });
    }

    public void sendOnAction(MouseEvent mouseEvent) throws IOException {
        String message = txtMessage.getText();

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

    public static void addLabel(String msg, VBox vBox) {
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.setPadding(new Insets(5, 10, 5, 10));

        Text text = new Text(msg);
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

    public void sendImageOnAction(MouseEvent mouseEvent) throws IOException {
        fileChooser.setTitle("Choose Image");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("All Files", "*.*"));
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {

            HBox hBox = new HBox();
            hBox.setAlignment(Pos.CENTER_RIGHT);
            hBox.setPadding(new Insets(5, 5, 5, 15));

            Image image =new Image(file.toURI().toString());
            ImageView imageView=new ImageView(image);
            imageView.setFitWidth(200);
            imageView.setFitHeight(300);
            imageView.setStyle("-fx-border-style:solid;"+"-fx-border-color: rgb(15,125,242)");
            hBox.getChildren().add(imageView);
            vBox.getChildren().add(hBox);

            client.sendImage(file.toURI().toString());

        } else {
            System.out.println("A file is not Selected");
        }

    }

    public static void addImageLabel(Image image, VBox vBox) {
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.setPadding(new Insets(5, 10, 5, 10));

        ImageView imageView=new ImageView(image);
        imageView.setFitWidth(200);
        imageView.setFitHeight(300);
        imageView.setStyle("-fx-background-color: rgb(233,233,235); " +
                "-fx-background-radius: 20px");
        hBox.getChildren().add(imageView);
        vBox.getChildren().add(hBox);

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                vBox.getChildren().add(hBox);
            }
        });
    }
}
