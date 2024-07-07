package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import services.ProgressionService;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class AjouterProgresController {

    @FXML
    private TextField tfEmail;
    @FXML
    private TextField tfLongueur;
    @FXML
    private TextField tfPoids;
    @FXML
    private TextField tfDateInscri;
    @FXML
    private TextField tfGender;

    private ProgressionService progressionService = new ProgressionService();

    @FXML
    public void ajouterProgression() {
        try {
            String email = tfEmail.getText();
            double longueur = Double.parseDouble(tfLongueur.getText());
            double poids = Double.parseDouble(tfPoids.getText());
            String dateStr = tfDateInscri.getText();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
            LocalDate dateInscri = LocalDate.parse(dateStr, formatter);
            String gender = tfGender.getText();

            // Calculate muscle mass (example formula)
            double massMusc = calculateMuscleMass(poids, longueur, gender);

            progressionService.ajouterProgression(email, longueur, poids, java.sql.Date.valueOf(dateInscri), gender, massMusc);
        } catch (NumberFormatException | DateTimeParseException e) {
            e.printStackTrace();
            // Handle parsing errors
        }
    }

    private double calculateMuscleMass(double poids, double longueur, String gender) {
        // Example formula for muscle mass calculation
        return (gender.equalsIgnoreCase("homme"))
                ? 0.407 * poids + 0.267 * longueur * 100 - 19.2
                : 0.252 * poids + 0.473 * longueur * 100 - 48.3;// Adjust this formula as needed
    }
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
    @FXML
    void ReturnHome(ActionEvent event) throws IOException {
        FXMLLoader l = new FXMLLoader(getClass().getResource("/PageInitial.fxml"));//donner notre resources ,donner l'interface a naviguer
        try {
            Parent root = l.load();//recharger notre resources
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("ReturnHome");
            stage.show();
        } catch (IOException e) {
            System.out.println(e);
        }

    }

}
