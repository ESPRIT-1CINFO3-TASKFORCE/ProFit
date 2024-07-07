package controllers;

import entites.ProgressionEntity;
import services.ProgressionService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class TestAffichage {

    @FXML
    private TableView<ProgressionEntity> Table;

    @FXML
    private TableColumn<ProgressionEntity, String> colemail;

    @FXML
    private TableColumn<ProgressionEntity, Integer> colpoids;

    @FXML
    private TableColumn<ProgressionEntity, Double> collongueur;

    @FXML
    private TableColumn<ProgressionEntity, Integer> colimc;

    @FXML
    private TableColumn<ProgressionEntity, LocalDate> coldateInscri;

    @FXML
    private TableColumn<ProgressionEntity, String> colDescri;
    @FXML
    private TableColumn<ProgressionEntity, Double> colMassMusc;;


    @FXML
    private Button SupprimerProgression;

    @FXML
    private TextField tfsearch;

    @FXML
    private Button modifierprogression;

    private ProgressionService progres = new ProgressionService();

    @FXML
    public void initialize() {
        colemail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colpoids.setCellValueFactory(new PropertyValueFactory<>("poids"));
        collongueur.setCellValueFactory(new PropertyValueFactory<>("longueur"));
        colimc.setCellValueFactory(new PropertyValueFactory<>("IMC"));
        coldateInscri.setCellValueFactory(new PropertyValueFactory<>("date_inscri"));
        colDescri.setCellValueFactory(new PropertyValueFactory<>("description"));
        colMassMusc.setCellValueFactory(new PropertyValueFactory<>("masse_musc"));

        try {
            loadData();
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Database Error", "Could not load data.");

        }

        Table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                populateForm(newSelection);
            }
        });
    }
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void populateForm(ProgressionEntity selectedProgression) {
        // Here you can populate a form with selectedProgression data if needed
    }

    @FXML
    void handleRowSelect() {
        ProgressionEntity selectedProgression = Table.getSelectionModel().getSelectedItem();
        if (selectedProgression != null) {
            populateForm(selectedProgression);
        }
    }

    @FXML
    void Updatebtn(ActionEvent event) {
        ProgressionEntity selectedProgression = Table.getSelectionModel().getSelectedItem();
        if (selectedProgression == null) {
            showAlert(Alert.AlertType.WARNING, "No Selection", "No Progression Selected", "Please select a progression in the table.");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FormulaireModifierProgres.fxml"));
            Parent root = loader.load();

            FormulaireModifierProgres controller = loader.getController();
            controller.setProgressionEntity(selectedProgression);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Modifier des Progressions");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String header, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void loadData() throws SQLException {
        List<ProgressionEntity> list = progres.readAll();
        ObservableList<ProgressionEntity> data = FXCollections.observableArrayList(list);
        Table.setItems(data);
    }
    @FXML
    void AjouternewProg(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterProgres.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Ajout de Progression");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void navigationHome(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/PageInitial.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Affichage des Progressions");
        stage.show();
    }

    @FXML
    void searchProgression(ActionEvent event) {
        try {
            String email = tfsearch.getText().trim();
            if (!email.isEmpty()) {
                List<ProgressionEntity> progressions = progres.chercherParEmail(email);
                Table.getItems().setAll(progressions);
            } else {
                showAlert(Alert.AlertType.ERROR, "Search Error", "Empty Search Field", "Please enter an email to search.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to Search", "An error occurred while searching: " + e.getMessage());
        }
    }

    @FXML
    void Deletebtn(ActionEvent event) {
        ProgressionEntity selectedProgression = Table.getSelectionModel().getSelectedItem();
        if (selectedProgression == null) {
            showAlert(Alert.AlertType.WARNING, "No Selection", "No Progression Selected", "Please select a progression in the table.");
            return;
        }

        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Progression");
            alert.setHeaderText("Are you sure you want to delete this progression?");
            alert.setContentText("Email: " + selectedProgression.getEmail());

            if (alert.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK) {
                progres.deleteProgression(selectedProgression.getId_prg());
                loadData();
                showAlert(Alert.AlertType.INFORMATION, "Deletion Successful", "Progression Deleted", "The progression has been deleted successfully.");
            }
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to delete progression", "An error occurred while deleting the progression: " + e.getMessage());
        }
    }
}
