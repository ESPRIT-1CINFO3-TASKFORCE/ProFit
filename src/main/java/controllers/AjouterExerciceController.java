package controllers;

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
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import services.ExerciceServices;
import utils.MySQLConnector;

import java.io.IOException;
import java.sql.Connection;


public class AjouterExerciceController {

    @FXML
    private TextField id_nomEx;

    @FXML
    private TextField id_description;

    @FXML
    private Slider id_difficulteSlider;

    @FXML
    private Slider id_dureeSlider;

    @FXML
    private Slider id_caloriesSlider;

    @FXML
    private ChoiceBox<String> id_musculesCibles;

    @FXML
    private Label label_nomEx;

    @FXML
    private Label label_description;

    @FXML
    private Label label_difficulte;

    @FXML
    private Label label_dureeMinutes;

    @FXML
    private Label label_caloriesBrulees;

    @FXML
    private Label label_musculesCibles;

    @FXML
    private GridPane gridPane;

    private ExerciceServices exerciceServices;

    public AjouterExerciceController() {
        // Récupération de la connexion à la base de données
        Connection connection = MySQLConnector.getInstance().getConnection();

        // Initialisation de ExerciceServices avec la connexion
        this.exerciceServices = new ExerciceServices(connection);
    }

    @FXML
    public void initialize() {
        // Initialisation du ChoiceBox muscules cibles
        ObservableList<String> musclesList = FXCollections.observableArrayList("Quadriceps", "Ischio-jambiers", "Mollets", "Pectoraux", "Dorsaux", "Abdominaux", "Biceps", "Triceps");
        id_musculesCibles.setItems(musclesList);

        // Réglage des valeurs par défaut pour les sliders
        id_difficulteSlider.setMin(1);
        id_difficulteSlider.setMax(100);
        id_difficulteSlider.setValue(50); // Valeur par défaut
        id_difficulteSlider.setShowTickLabels(true);
        id_difficulteSlider.setShowTickMarks(true);
        id_difficulteSlider.setMajorTickUnit(10);
        id_difficulteSlider.setMinorTickCount(1);

        id_dureeSlider.setMin(0);
        id_dureeSlider.setMax(120);
        id_dureeSlider.setValue(60); // Valeur par défaut
        id_dureeSlider.setShowTickLabels(true);
        id_dureeSlider.setShowTickMarks(true);
        id_dureeSlider.setMajorTickUnit(10);
        id_dureeSlider.setMinorTickCount(1);

        id_caloriesSlider.setMin(0);
        id_caloriesSlider.setMax(1000);
        id_caloriesSlider.setValue(500); // Valeur par défaut
        id_caloriesSlider.setShowTickLabels(true);
        id_caloriesSlider.setShowTickMarks(true);
        id_caloriesSlider.setMajorTickUnit(50);
        id_caloriesSlider.setMinorTickCount(5);
    }

    @FXML
    public void ajouterExercice() {
        // Récupération des valeurs des champs
        String nomEx = id_nomEx.getText().trim();
        String description = id_description.getText().trim();
        int difficulte = (int) id_difficulteSlider.getValue();
        int dureeMinutes = (int) id_dureeSlider.getValue();
        int caloriesBrulees = (int) id_caloriesSlider.getValue();
        String musculesCibles = id_musculesCibles.getValue();

        // Validation des champs
        boolean isNomValid = nomEx.length() >= 5;
        boolean isDescriptionValid = description.isEmpty() || description.length() <= 100;
        boolean isMusculesCiblesValid = musculesCibles != null && isValidMusculeCible(musculesCibles);

        if (isNomValid && isDescriptionValid && isMusculesCiblesValid) {
            // Création de l'objet Exercice et ajout à la base de données
            try {
                // Convertir la valeur de difficulté en String
                String difficulteStr = String.valueOf(difficulte);

                Exercice exercice = new Exercice(nomEx, description, difficulteStr, dureeMinutes, caloriesBrulees, musculesCibles);
                exerciceServices.ajouter(exercice);

                // Affichage d'un message de succès
                showAlert("Exercice ajouté avec succès !", Alert.AlertType.INFORMATION);

                // Effacer les champs après ajout
                effacerChamps();

            } catch (NumberFormatException e) {
                afficherErreur("Veuillez entrer des valeurs numériques valides pour Durée et Calories Brûlées.");
            }
        } else {
            afficherErreur("Veuillez remplir tous les champs correctement.");
        }
    }

    // Méthode pour afficher une erreur avec un message en rouge
    private void afficherErreur(String message) {
        showAlert(message, Alert.AlertType.ERROR);
    }

    // Méthode pour vérifier la validité du champ muscle cible
    private boolean isValidMusculeCible(String musculeCible) {
        // Liste des muscles cibles valides
        String[] musclesValides = {"Quadriceps", "Ischio-jambiers", "Mollets", "Pectoraux", "Dorsaux", "Abdominaux", "Biceps", "Triceps"};
        for (String muscle : musclesValides) {
            if (muscle.equalsIgnoreCase(musculeCible)) {
                return true;
            }
        }
        return false;
    }

    // Méthode pour afficher une alerte avec un message donné
    private void showAlert(String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(alertType == Alert.AlertType.ERROR ? "Erreur" : "Succès");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Méthode pour effacer les champs après ajout
    private void effacerChamps() {
        id_nomEx.clear();
        id_description.clear();
        id_difficulteSlider.setValue(50); // Réinitialisation du slider de difficulté
        id_dureeSlider.setValue(60); // Réinitialisation du slider de durée
        id_caloriesSlider.setValue(500); // Réinitialisation du slider de calories
        id_musculesCibles.getSelectionModel().clearSelection();
    }
    @FXML
    void ReturnHo(ActionEvent event) throws IOException {
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
