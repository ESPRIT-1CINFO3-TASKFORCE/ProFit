package controllers;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import entites.Exercice;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import services.ExerciceServices;
import utils.MySQLConnector;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class AfficherExerciceController {

    @FXML
    private TableView<Exercice> tableView;

    @FXML
    private TableColumn<Exercice, String> nomCol;

    @FXML
    private TableColumn<Exercice, String> descriptionCol;

    @FXML
    private TableColumn<Exercice, String> difficulteCol;

    @FXML
    private TableColumn<Exercice, Number> dureeCol;

    @FXML
    private TableColumn<Exercice, Number> caloriesCol;

    @FXML
    private TableColumn<Exercice, String> musculesCol;

    private ExerciceServices exerciceServices;

    public AfficherExerciceController() {
        // Initialisation du service ExerciceServices avec la connexion
        Connection connection = MySQLConnector.getInstance().getConnection();
        this.exerciceServices = new ExerciceServices(connection);
    }

    @FXML
    private void initialize() {
        // Liaison des propriétés des colonnes aux propriétés des objets Exercice
        nomCol.setCellValueFactory(cellData -> cellData.getValue().nomExProperty());
        descriptionCol.setCellValueFactory(cellData -> cellData.getValue().descriptionProperty());
        difficulteCol.setCellValueFactory(cellData -> cellData.getValue().difficulteProperty());
        dureeCol.setCellValueFactory(cellData -> cellData.getValue().dureeMinutesProperty());
        caloriesCol.setCellValueFactory(cellData -> cellData.getValue().caloriesBruleesProperty());
        musculesCol.setCellValueFactory(cellData -> cellData.getValue().musculesCiblesProperty());

        // Configuration des colonnes supplémentaires
        configureTableColumns();

        // Chargement des données dans le TableView
        loadExercices();

        // Configuration de la sélection de ligne pour la modification
        tableView.setRowFactory(tv -> {
            TableRow<Exercice> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && !row.isEmpty()) {
                    Exercice exercice = row.getItem();
                    modifierExercice(exercice);
                }
            });
            return row;
        });
    }

    private void configureTableColumns() {
        // Configuration de la colonne "Modifier"
        TableColumn<Exercice, Void> modifierCol = new TableColumn<>("Modifier");
        modifierCol.setCellFactory(param -> new TableCell<>() {
            private final Button btn = new Button("Modifier");
            {
                btn.getStyleClass().add("button_inscrit"); //
            }


            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(btn);
                    btn.setOnAction(event -> {
                        Exercice exercice = getTableView().getItems().get(getIndex());
                        modifierExercice(exercice);
                    });
                }
            }
        });

        // Ajouter la colonne "Modifier" à la TableView
        tableView.getColumns().add(modifierCol);

        // Configuration de la colonne "Supprimer"
        TableColumn<Exercice, Void> supprimerCol = new TableColumn<>("Supprimer");
        supprimerCol.setCellFactory(param -> new TableCell<>() {
            private final Button btn = new Button("Supprimer");
            {
                btn.getStyleClass().add("button_inscrit"); //
            }


            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(btn);
                    btn.setOnAction(event -> {
                        Exercice exercice = getTableView().getItems().get(getIndex());
                        supprimerExercice(exercice);
                    });
                }
            }
        });

        // Ajouter la colonne "Supprimer" à la TableView
        tableView.getColumns().add(supprimerCol);

        // Configuration de la colonne "Imprimer"
        TableColumn<Exercice, Void> imprimerCol = new TableColumn<>("Imprimer");
        imprimerCol.setCellFactory(param -> new TableCell<>() {
            private final Button btn = new Button("Imprimer");
            {
                btn.getStyleClass().add("button_inscrit"); //
            }


            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(btn);
                    btn.setOnAction(event -> {
                        Exercice exercice = getTableView().getItems().get(getIndex());
                        imprimerExercice(exercice);
                    });
                }
            }
        });

        // Ajouter la colonne "Imprimer" à la TableView
        tableView.getColumns().add(imprimerCol);
    }

    private void modifierExercice(Exercice exercice) {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Modifier l'exercice");
        dialog.setHeaderText("Modifier les attributs de l'exercice");

        // Création des champs de texte pour la modification
        TextField nomField = new TextField(exercice.getNomEx());
        TextField descriptionField = new TextField(exercice.getDescription());
        TextField difficulteField = new TextField(exercice.getDifficulte());
        TextField musculesCiblesField = new TextField(exercice.getMusculesCibles());
        TextField dureeMinutesField = new TextField(String.valueOf(exercice.getDureeMinutes()));
        TextField caloriesBruleesField = new TextField(String.valueOf(exercice.getCaloriesBrulees()));

        dialog.getDialogPane().setContent(new VBox(10,
                new Label("Nom:"), nomField,
                new Label("Description:"), descriptionField,
                new Label("Difficulté:"), difficulteField,
                new Label("Muscles ciblés:"), musculesCiblesField,
                new Label("Durée (minutes):"), dureeMinutesField,
                new Label("Calories brûlées:"), caloriesBruleesField));

        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        dialog.setResultConverter(buttonType -> {
            if (buttonType == ButtonType.OK) {
                exercice.setNomEx(nomField.getText());
                exercice.setDescription(descriptionField.getText());
                exercice.setCaloriesBrulees(Integer.parseInt(caloriesBruleesField.getText()));
                exercice.setDifficulte(difficulteField.getText());
                exercice.setDureeMinutes(Integer.parseInt(dureeMinutesField.getText()));
                exercice.setMusculesCibles(musculesCiblesField.getText());

                try {
                    exerciceServices.update(exercice, exercice.getId());
                    // Rafraîchir la table après la mise à jour si nécessaire
                    loadExercices();

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Succès");
                    alert.setHeaderText(null);
                    alert.setContentText("Exercice mis à jour avec succès !");
                    alert.showAndWait();
                } catch (SQLException e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erreur");
                    alert.setHeaderText(null);
                    alert.setContentText("Erreur lors de la mise à jour de l'exercice : " + e.getMessage());
                    alert.showAndWait();
                }
            }
            return null;
        });

        dialog.showAndWait();
    }

    private void supprimerExercice(Exercice exercice) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation de suppression");
        alert.setHeaderText(null);
        alert.setContentText("Êtes-vous sûr de vouloir supprimer cet exercice ?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                exerciceServices.delete(exercice.getId());
                loadExercices(); // Recharger les exercices après suppression
            } catch (Exception e) {
                e.printStackTrace();
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Erreur");
                errorAlert.setHeaderText(null);
                errorAlert.setContentText("Erreur lors de la suppression de l'exercice : " + e.getMessage());
                errorAlert.showAndWait();
            }
        }
    }

    private void imprimerExercice(Exercice exercice) {
        Document document = new Document();

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Enregistrer le fichier PDF");

        // Filtre pour n'afficher que les fichiers PDF
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PDF files (*.pdf)", "*.pdf");
        fileChooser.getExtensionFilters().add(extFilter);

        // Afficher la boîte de dialogue pour choisir le fichier de sauvegarde
        File file = fileChooser.showSaveDialog(new Stage());

        if (file != null) {
            try {
                PdfWriter.getInstance(document, new FileOutputStream(file));
                document.open();

                // Ajouter les données de l'exercice dans le document PDF
                document.add(new Paragraph("ID: " + exercice.getId()));
                document.add(new Paragraph("Nom: " + exercice.getNomEx()));
                document.add(new Paragraph("Description: " + exercice.getDescription()));
                document.add(new Paragraph("Difficulté: " + exercice.getDifficulte()));
                document.add(new Paragraph("Durée (minutes): " + exercice.getDureeMinutes()));
                document.add(new Paragraph("Calories brûlées: " + exercice.getCaloriesBrulees()));
                document.add(new Paragraph("Muscles ciblés: " + exercice.getMusculesCibles()));

                System.out.println("Exercice imprimé avec succès !");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                document.close();
            }
        }
    }

    private void loadExercices() {
        List<Exercice> exercices = exerciceServices.getAll();
        ObservableList<Exercice> exercicesList = FXCollections.observableArrayList(exercices);
        tableView.setItems(exercicesList);
    }


    @FXML
    void ReturnH(ActionEvent event) throws IOException {
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
