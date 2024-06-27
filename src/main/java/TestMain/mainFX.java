package TestMain;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class mainFX extends Application {


    @Override
    public void start(Stage stage) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/TestAffichage.fxml"));


        try {
            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Affichage des Progressions");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


   /* @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Login.fxml"));
        try {
            Parent root = loader.load();
            Scene scene =new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Inscription");
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        }
    public static void main(String[] args) {
        launch(args);

    }*/
}
