package controllers;

import entites.ProgressionEntity;
import services.ProgressionService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;

public class AfficherProgres {

    @FXML
    private Label labDateFinR;
    @FXML
    private Label labDateInscri;
    @FXML
    private Label labDescri;
    @FXML
    private Label labIMC;
    @FXML
    private Label labEmail;
    @FXML
    private Label labLongueur;
    @FXML
    private Label labNom;
    @FXML
    private Label labNomRegime;
    @FXML
    private Label labPoids;
    @FXML
    private Label labPrenom;
    @FXML
    private Label labMasseMusc;  // New Label for Masse Musculaire

    public void initialize() {
        ProgressionService service = new ProgressionService();
        try {
            ProgressionEntity progression = service.afficherProgres("test@example.com");
            if (progression != null) {
                labNom.setText(progression.getNom());
                labPrenom.setText(progression.getPrenom());
                labEmail.setText(progression.getEmail());
                labPoids.setText(String.valueOf(progression.getPoids()));
                labLongueur.setText(String.valueOf(progression.getLongueur()));
                labIMC.setText(String.format("%.2f", progression.getIMC()));
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                labDateInscri.setText(progression.getDate_inscri().format(formatter));
                labDescri.setText(progression.getDescription());
                labNomRegime.setText(progression.getNomregime());
                labDateFinR.setText(progression.getDate_finR().format(formatter));
                labMasseMusc.setText(String.valueOf(progression.getMasse_musc()));
            } else {
                showAlert(Alert.AlertType.ERROR, "Erreur", "Aucune progression trouvée pour cet email.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Erreur", "Une erreur est survenue lors de l'affichage de la progression.");
        }
    }

    @FXML
    void retourHome(ActionEvent event) throws IOException {
        navigateToScene(event, "/PageInitial.fxml", "Page Initiale");
    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void navigateToScene(ActionEvent event, String fxmlFile, String title) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle(title);
        stage.show();
    }
}
