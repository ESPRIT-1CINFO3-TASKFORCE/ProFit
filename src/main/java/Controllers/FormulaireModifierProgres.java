package Controllers;

import Entites.ProgressionEntity;
import Services.ProgressionService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

public class FormulaireModifierProgres {

    @FXML
    private TextField tfDateInsc;

    @FXML
    private TextField tfDesc;

    @FXML
    private TextField tfEmail;

    @FXML
    private TextField tfIMC;

    @FXML
    private TextField tfLongueur;

    @FXML
    private TextField tfPoids;
    private ProgressionEntity selectedProgression;
    private ProgressionService progressionService = new ProgressionService();

    public void setProgressionEntity(ProgressionEntity progression) {
        this.selectedProgression = progression;
        populateForm();
    }

    private void populateForm() {
        tfEmail.setText(selectedProgression.getEmail());
        tfPoids.setText(String.valueOf(selectedProgression.getPoids()));
        tfLongueur.setText(String.valueOf(selectedProgression.getLongueur()));
        tfIMC.setText(String.valueOf(selectedProgression.getIMC()));
        tfDateInsc.setText(String.valueOf(selectedProgression.getDate_inscri()));
        tfDesc.setText(selectedProgression.getDescription());
    }

    @FXML
    void Enregistrer(ActionEvent event) {
        try {
            // Get the updated details from the form
            String email = tfEmail.getText();
            int poids = Integer.parseInt(tfPoids.getText());
            double longueur = Double.parseDouble(tfLongueur.getText());
            int imc = Integer.parseInt(tfIMC.getText());
            LocalDate dateInscri = LocalDate.parse(tfDateInsc.getText());
            String description = tfDesc.getText();

            // Update the selected progression entity
            selectedProgression.setEmail(email);
            selectedProgression.setPoids(poids);
            selectedProgression.setLongueur(longueur);
            selectedProgression.setIMC(imc);
            selectedProgression.setDate_inscri(dateInscri);
            selectedProgression.setDescription(description);

            // Update the progression in the database
            progressionService.modifierProgres(Long.valueOf(selectedProgression.getId_prg()), selectedProgression);

            // Reload the main interface (assuming TestAffichage.fxml is your main interface)
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/TestAffichage.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Modifier des Progressions");
            stage.show();

            // Optionally, you can pass data to the main controller if needed
            // MainController mainController = loader.getController();
            // mainController.loadData(); // Assuming you have a method to reload data in your main controller

            showAlert(Alert.AlertType.INFORMATION, "Update Successful", "Progression Updated", "The progression has been updated successfully.");
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Invalid Input", "Invalid number format", "Please enter valid numbers for poids, longueur, and IMC.");
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to update progression", "An error occurred while updating the progression: " + e.getMessage());
        } catch (IOException e) {
            System.out.println(e);
        }
    }


    private void showAlert(Alert.AlertType alertType, String title, String header, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }




//    @FXML
//    void navigationHome(ActionEvent event) throws IOException {
//        FXMLLoader l = new FXMLLoader(getClass().getResource("/TestAffichage.fxml"));//donner notre resources ,donner l'interface a naviguer
//        try {
//            Parent root = l.load();//recharger notre resources
//
//            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//            Scene scene = new Scene(root);
//            stage.setScene(scene);
//            stage.setTitle("Modifier des  Progressions");
//            stage.show();
//        } catch (IOException e) {
//            System.out.println(e);
//        }
//    }
}


