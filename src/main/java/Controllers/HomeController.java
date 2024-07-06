package Controllers;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;

import java.io.IOException;

public class HomeController {

    @FXML
    private Label lconnect;

    @FXML
    private Label lfitness;

    @FXML
    private Label linscription;

    @FXML
    void connexion(ActionEvent event) {
        try {
            FXMLLoader l = new FXMLLoader(getClass().getResource("/Login.fxml"));
            Parent root = l.load();
            lconnect.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Erreur lors du chargement de Login.fxml : " + e.getMessage());
        }
    }

    @FXML
    void inscription(ActionEvent event) {
        FXMLLoader l = new FXMLLoader(getClass().getResource("/CreerUser.fxml"));
        Parent root = null;
        try {
            root = l.load();
            lconnect.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}