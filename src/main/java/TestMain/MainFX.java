package TestMain;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class MainFX extends Application {
    public static void main (String[] args){
       launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
       FXMLLoader loader = new FXMLLoader(getClass().getResource("/GestionDesListesExercice.fxml"));
      //  FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterExercice.fxml"));

        try {
            Parent root=loader.load();
            Scene scene=new Scene(root);
            stage.setScene(scene);
            stage.setTitle("App  ");
            stage.show();

        } catch (IOException e){
            throw new RuntimeException(e);
        }

    }

}
