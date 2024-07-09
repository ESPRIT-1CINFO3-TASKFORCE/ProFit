package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;

import java.io.IOException;

public class SidBarController {
    @FXML
    private Button tfgestion;

    @FXML
    private Button tfstore;
    @FXML
    void store(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Store.fxml"));
        try {
            Parent root = loader.load();
            tfstore.getScene().setRoot(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    void GestionProduit(ActionEvent event) {
        FXMLLoader L = new FXMLLoader(getClass().getResource("/AjouterProduit.fxml"));
        try {
            Parent root = L.load();
            tfgestion.getScene().setRoot(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    void Commande(ActionEvent event) {
        FXMLLoader L = new FXMLLoader(getClass().getResource("/CommandeDash.fxml"));
        try {
            Parent root = L.load();
            tfgestion.getScene().setRoot(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
