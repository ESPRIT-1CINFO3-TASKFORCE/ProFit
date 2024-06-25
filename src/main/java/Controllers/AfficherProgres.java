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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public class AfficherProgres {

    @FXML
    private Label lDesc;

    @FXML
    private Label lLongeur;

    @FXML
    private Label afficher;


    @FXML
    private Label ldateInsc;

    @FXML
    private Label lid;

    @FXML
    private Label limc;

    @FXML
    private Label lpoids;

    //creer des setters qui nous permet de modifier


    public void setLdateInsc(String ldateInsc) {
        this.ldateInsc.setText(ldateInsc);
    }

    public void setLid(String lid) {
        this.lid.setText(lid);
    }

    public void setLimc(String limc) {
        this.limc.setText(limc);
    }

    public void setLpoids(String lpoids) {
        this.lpoids.setText(lpoids);
    }

    public void setlDesc(String lDesc) {
        this.lDesc.setText(lDesc);
    }

    public void setlLongeur(String lLongeur) {
        this.lLongeur.setText(lLongeur);
    }

    ProgressionService prog =new ProgressionService();




    @FXML
    void AfficherFromDB(ActionEvent event )throws SQLException {


        try {
            List<ProgressionEntity> list = prog.readAll();
            afficher.setText(list.toString());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        FXMLLoader l = new FXMLLoader(getClass().getResource("/AjouterProgres.fxml"));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
        String dateText = ldateInsc.getText();

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
            prog.AjouterProgression(new ProgressionEntity(
                    Integer.parseInt(lid.getText()),
                    Integer.parseInt(limc.getText()),
                    Double.parseDouble(lLongeur.getText()),
                    Integer.parseInt(limc.getText()),
                    dateInscri,
                    lDesc.getText())
            );

            // Charger l'interface d'affichage des progressions
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherProgres.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Affichage des Progressions");
            stage.show();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            // Afficher une alerte si le format des nombres est incorrect
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de format de nombre");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez vérifier que tous les champs numériques sont correctement remplis.");
            alert.showAndWait();
        }

    }












    //finis
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


}