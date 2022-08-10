package util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class SetNavigation {
    public static void setUI(String URI, String title, AnchorPane context) throws IOException {
        URL resource = SetNavigation.class.getResource("../views/" + URI + ".fxml");
        Parent load = FXMLLoader.load(resource);
        Stage window = (Stage) context.getScene().getWindow();
        window.setTitle(title);
        window.setScene(new Scene(load));
    }
}
