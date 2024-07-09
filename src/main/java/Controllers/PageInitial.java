package Controllers;

import Entites.UserEntity;
import Services.UserService;
import Utils.SessionManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.io.IOException;

public class PageInitial {
    private UserEntity current_user;

    @FXML
    void logout(ActionEvent event) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Déconnexion");
        alert.setHeaderText("Êtes-vous sûr de vouloir vous déconnecter ?");
        alert.setContentText("Toutes les données non sauvegardées seront perdues.");

        // Option de bouton pour confirmer la déconnexion
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                // Effectuer la déconnexion
                if (current_user != null) {
                    UserService.logout(current_user);
                }
                SessionManager.clearSession();

                // Charger la scène de connexion
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/Login.fxml"));
                    Parent root = loader.load();

                    // Obtenez la scène actuelle
                    Scene scene = new Scene(root);

                    // Obtenez la fenêtre principale
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

                    // Définissez la nouvelle scène
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }




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

    @FXML
    void BouttonNaviguerAcceuilPrin(ActionEvent event) throws IOException {
        FXMLLoader l = new FXMLLoader(getClass().getResource("/SideBar.fxml"));//donner notre resources ,donner l'interface a naviguer
        try {
            Parent root = l.load();//recharger notre resources
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Aceeuil Principal");
            stage.show();
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
