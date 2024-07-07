package GUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.application.Application;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.geometry.Rectangle2D;

public class Chat extends Application {

    @Override
    public void start(Stage primaryStage) {

        try {
            // Load the FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ChatLogin.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            // Set the scene to the stage
            primaryStage.setScene(scene);
            primaryStage.setTitle("ProFit Chat");
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
