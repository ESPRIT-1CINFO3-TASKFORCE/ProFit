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

public class Forum extends Application {

    @Override
    public void start(Stage primaryStage) {
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();

        try {
            // Load the FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("forums.fxml"));
            Parent root = loader.load();

            // Create the scene with the screen dimensions
            Scene scene = new Scene(root, screenBounds.getWidth(), screenBounds.getHeight());

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
