package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class PageInitial {
    @FXML
    void BouttonNaviguerAfficherProg(ActionEvent event) throws IOException {
            FXMLLoader l = new FXMLLoader(getClass().getResource("/TestAffichage.fxml"));//donner notre resources ,donner l'interface a naviguer
            try {
                Parent root = l.load();//recharger notre resources
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.setTitle("Affichage des Progressions");
                stage.show();
            } catch (IOException e) {
                System.out.println(e);
            }

    }

    @FXML
    void BouttonNaviguerAjouterProg(ActionEvent event) throws IOException {
        FXMLLoader l = new FXMLLoader(getClass().getResource("/AjouterProgres.fxml"));//donner notre resources ,donner l'interface a naviguer
        try {
            Parent root = l.load();//recharger notre resources
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Ajouter des Progressions");
            stage.show();
        } catch (IOException e) {
            System.out.println(e);
        }

    }

    @FXML
    void BouttonNaviguerDashboardAdh(ActionEvent event) throws IOException {
        FXMLLoader l = new FXMLLoader(getClass().getResource("/DashboardBilanAdh.fxml"));//donner notre resources ,donner l'interface a naviguer
        try {
            Parent root = l.load();//recharger notre resources
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Dashboard de l'adherant");
            stage.show();
        } catch (IOException e) {
            System.out.println(e);
        }

    }

    @FXML
    void BouttonNaviguerPlanningRegime(ActionEvent event) throws IOException {
        FXMLLoader l = new FXMLLoader(getClass().getResource("/PlansRegime.fxml"));//donner notre resources ,donner l'interface a naviguer
        try {
            Parent root = l.load();//recharger notre resources
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Planning de Régime");
            stage.show();
        } catch (IOException e) {
            System.out.println(e);
        }


    }
}
