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
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class AjouterProgresController {

    @FXML
    private TextField tfDescri;

    @FXML
    private TextField tfID;

    @FXML
    private TextField tfLongueur;


    @FXML
    private TextField tfPoids;

    @FXML
    private TextField tfdateInscri;

    @FXML
    private TextField tfimc;

    @FXML
    void AjouterProg(ActionEvent event) {

        //1ere etape creer instance de Service Progression
        ProgressionService progres = new ProgressionService();
        // Formater et analyser la date
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
        String dateText = tfdateInscri.getText();

        LocalDate dateInscri;
        try {
            // Vérifiez que le texte de la date correspond au format attendu
            if (!dateText.matches("\\d{1,2}/\\d{1,2}/\\d{4}")) {
                throw new DateTimeParseException("Format de date invalide", dateText, 0);
            }

            // Analyser la date
            dateInscri = LocalDate.parse(dateText, formatter);
        } catch (DateTimeParseException e) {
            // Afficher une alerte si le format de la date est incorrect
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de format de date");
            alert.setHeaderText(null);
            alert.setContentText("Le format de la date est invalide. Utilisez le format 'd/M/yyyy'.");
            alert.showAndWait();
            return;
        }

        try {
            // Ajouter la progression
            progres.AjouterProgression(new ProgressionEntity(
                    Integer.parseInt(tfID.getText()),
                    Integer.parseInt(tfPoids.getText()),
                    Double.parseDouble(tfLongueur.getText()),
                    Integer.parseInt(tfimc.getText()),
                    dateInscri,
                    tfDescri.getText())
            );

            // Afficher une alerte de succès
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Succès");
            alert.setHeaderText(null);
            alert.setContentText("La progression a été ajoutée avec succès !");
            alert.showAndWait();

        } catch (NumberFormatException e) {
            // Afficher une alerte si le format des nombres est incorrect
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de format de nombre");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez vérifier que tous les champs numériques sont correctement remplis.");
            alert.showAndWait();
        } catch (SQLException e) {
            // Afficher une alerte si une erreur SQL se produit
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de base de données");
            alert.setHeaderText(null);
            alert.setContentText("Une erreur est survenue lors de l'ajout de la progression.");
            alert.showAndWait();
        }
    }

    //cette methode pour naviguer d'une fenetre a une autre

    @FXML
    void navigation(ActionEvent event) throws IOException {
        FXMLLoader l = new FXMLLoader(getClass().getResource("/TestAffichage.fxml"));//donner notre resources ,donner l'interface a naviguer
        try {
            Parent root = l.load();//recharger notre resources
            //bch nbaath les valeurs mte3i
            TestAffichage controllerAfficher = l.getController();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Affichage des Progressions");
            stage.show();

           /* controllerAfficher.setcolid(tfID.getText());
            controllerAfficher.setcolpoids(tfPoids.getText());
            controllerAfficher.setlLongeur(tfLongueur.getText());
            controllerAfficher.setLimc(tfimc.getText());
            controllerAfficher.setLdateInsc(tfdateInscri.getText());
            controllerAfficher.setlDesc(tfDescri.getText());


            tfID.getScene().setRoot(root);*/
            //appel pour un composant graphique(attribut tfID exple)
            //donner la scene getscene
            //setroot pour changer la scene cad l'interface
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}


