package Controllers;

import Entites.ProgressionEntity;
import Services.ProgressionService;
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
    private TableColumn<ProgressionEntity, String> colDescri;

    @FXML
    private TableColumn<ProgressionEntity, LocalDate> coldateInscri;

    @FXML
    private TableColumn<ProgressionEntity, Integer> colemail;

    @FXML
    private TableColumn<ProgressionEntity, Integer> colimc;

    @FXML
    private TableColumn<ProgressionEntity, Integer> collongueur;

    @FXML
    private TableColumn<ProgressionEntity, Integer> colpoids;
    @FXML
    private Button SupprimerProgression;

    @FXML
    private TextField tfsearch;

    @FXML
    private Button modifierprogression;

    ProgressionService progres = new ProgressionService();





    @FXML
    public void initialize() {
        colemail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colpoids.setCellValueFactory(new PropertyValueFactory<>("poids"));
        collongueur.setCellValueFactory(new PropertyValueFactory<>("longueur"));
        colimc.setCellValueFactory(new PropertyValueFactory<>("IMC"));
        coldateInscri.setCellValueFactory(new PropertyValueFactory<>("date_inscri"));
        colDescri.setCellValueFactory(new PropertyValueFactory<>("description"));

        try {
            loadData();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                populateForm(newSelection);
            }
        });
    }

    private void populateForm(ProgressionEntity selectedProgression) {
        // If you have a form in the same view, populate it here
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



   /* @FXML
    public void initialize() {
        // Lier les colonnes aux propriétés de ProgressionEntity
        colemail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colpoids.setCellValueFactory(new PropertyValueFactory<>("poids"));
        collongueur.setCellValueFactory(new PropertyValueFactory<>("longueur"));
        colimc.setCellValueFactory(new PropertyValueFactory<>("IMC"));
        coldateInscri.setCellValueFactory(new PropertyValueFactory<>("date_inscri"));
        colDescri.setCellValueFactory(new PropertyValueFactory<>("description"));

        try {
            loadData();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                populateForm(newSelection);
            }
        });
    }
    private void populateForm(ProgressionEntity selectedProgression) {
        colpoids.setText(String.valueOf(selectedProgression.getPoids()));
        collongueur.setText(String.valueOf(selectedProgression.getLongueur()));
        colimc.setText(String.valueOf(selectedProgression.getIMC()));
        colDescri.setText(selectedProgression.getDescription());
        colemail.setText(selectedProgression.getDescription()); // This might be wrong, double-check what you want to display here
        coldateInscri.setText(String.valueOf(selectedProgression.getDate_inscri()));
    }
    @FXML
    void handleRowSelect() {
        // Get the selected item
        ProgressionEntity selectedProgression = Table.getSelectionModel().getSelectedItem();
        if (selectedProgression != null) {
            // Populate the fields with the selected item's data
            colpoids.setText(String.valueOf(selectedProgression.getPoids()));
            collongueur.setText(String.valueOf(selectedProgression.getLongueur()));
            colimc.setText(String.valueOf(selectedProgression.getIMC()));
            colDescri.setText(selectedProgression.getDescription());
            colemail.setText(selectedProgression.getDescription());
            coldateInscri.setText(String.valueOf(selectedProgression.getDate_inscri()));
        }
    }

    @FXML
    void Updatebtn(ActionEvent event) {
        // Get the selected progression
        ProgressionEntity selectedProgression = Table.getSelectionModel().getSelectedItem();
        if (selectedProgression == null) {
            showAlert(Alert.AlertType.WARNING, "No Selection", "No Progression Selected", "Please select a progression in the table.");
            return;
        }

        try {
            // Get the updated details from the form
            int poids = Integer.parseInt(colpoids.getText());
            double longueur = Double.parseDouble(collongueur.getText());
            int imc = Integer.parseInt(colimc.getText());
            int masseMusc = Integer.parseInt(colDescri.getText());
            String dateInscri = coldateInscri.getText();
            String description = colDescri.getText();

            // Create a new ProgressionEntity with the updated details
            ProgressionEntity updatedProgression = new ProgressionEntity();
            updatedProgression.setPoids(poids);
            updatedProgression.setLongueur(longueur);
            updatedProgression.setIMC(imc);
            updatedProgression.setMasse_musc(masseMusc);
            updatedProgression.setDate_inscri(LocalDate.parse(dateInscri));
            updatedProgression.setDescription(description);

            // Update the progression in the database
            progres.modifierProgres(Long.valueOf(selectedProgression.getId_prg()), updatedProgression);

            // Update the table view
            Table.refresh();
            showAlert(Alert.AlertType.INFORMATION, "Update Successful", "Progression Updated", "The progression has been updated successfully.");
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Invalid Input", "Invalid number format", "Please enter valid numbers for poids, longueur, IMC, and masse musculaire.");
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to update progression", "An error occurred while updating the progression: " + e.getMessage());
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
    }*/



  /*  @FXML
    public void initialize() {
        // Lier les colonnes aux propriétés de ProgressionEntity
        colemail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colpoids.setCellValueFactory(new PropertyValueFactory<>("poids"));
        collongueur.setCellValueFactory(new PropertyValueFactory<>("longueur"));
        colimc.setCellValueFactory(new PropertyValueFactory<>("IMC"));
        coldateInscri.setCellValueFactory(new PropertyValueFactory<>("date_inscri"));
        colDescri.setCellValueFactory(new PropertyValueFactory<>("desciption"));

        try {
            loadData();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadData() throws SQLException {
        List<ProgressionEntity> list = progres.readAll();
        ObservableList<ProgressionEntity> data = FXCollections.observableArrayList(list);
        Table.setItems(data);
    }*/


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
        FXMLLoader l = new FXMLLoader(getClass().getResource("/PageInitial.fxml"));//donner notre resources ,donner l'interface a naviguer
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
    void serachprogression(ActionEvent event) {
        try {
            String email = tfsearch.getText().trim();
            if (!email.isEmpty()) {
                List<ProgressionEntity> progressions = progres.chercherParEmail(email);
                // Mettre à jour la TableView avec les nouvelles données de progressions
                Table.getItems().setAll(progressions);
            } else {
                System.err.println("Veuillez entrer un email valide.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
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
            // Confirm deletion
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Progression");
            alert.setHeaderText("Are you sure you want to delete this progression?");
            alert.setContentText("Email: " + selectedProgression.getEmail());

            if (alert.showAndWait().get() == ButtonType.OK) {
                progres.deleteProgression(selectedProgression.getId_prg());

                // Refresh the table view
                loadData();
                showAlert(Alert.AlertType.INFORMATION, "Deletion Successful", "Progression Deleted", "The progression has been deleted successfully.");
            }
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to delete progression", "An error occurred while deleting the progression: " + e.getMessage());
        }
    }



}














