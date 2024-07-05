package Controllers;


import Entites.ProgressionEntity;
import Entites.UserEntity;
import Services.ProgressionService;
import Services.UserService;
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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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
    private TextField tfEmail;

    @FXML
    private TextField tfLongueur;


    @FXML
    private TextField tfPoids;

    @FXML
    private TextField tfdateInscri;





    @FXML
    void AjouterProg(ActionEvent event) {
        ProgressionService progres = new ProgressionService();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
        String dateText = tfdateInscri.getText();
        LocalDate dateInscri;
        try {
            if (!dateText.matches("\\d{1,2}/\\d{1,2}/\\d{4}")) {
                throw new DateTimeParseException("Format de date invalide", dateText, 0);
            }
            dateInscri = LocalDate.parse(dateText, formatter);
        } catch (DateTimeParseException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de format de date");
            alert.setHeaderText(null);
            alert.setContentText("Le format de la date est invalide. Utilisez le format 'd/M/yyyy'.");
            alert.showAndWait();
            return;
        }

        try {
            String email = tfEmail.getText();
            double longueur = Double.parseDouble(tfLongueur.getText());
            int poids = Integer.parseInt(tfPoids.getText());

            int id = progres.getIdByEmailAjoutProg(email);
            if (id == -1) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText(null);
                alert.setContentText("Aucun utilisateur trouvé avec cet email.");
                alert.showAndWait();
                return;
            }

            ProgressionEntity progression = new ProgressionEntity(id, poids, longueur, dateInscri);
           // progres.AjouterProgression(progression);

            // Calculer et mettre à jour l'IMC dans la base de données
            ProgressionService.calculerEtAfficherIMC(progression.getId(), poids, longueur,dateInscri);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Succès");
            alert.setHeaderText(null);
            alert.setContentText("La progression a été ajoutée avec succès !");
            alert.showAndWait();

        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de format de nombre");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez vérifier que tous les champs numériques sont correctement remplis.");
            alert.showAndWait();
        }
    }



    /*@FXML
    void AjouterProg(ActionEvent event) {
        ProgressionService progres = new ProgressionService();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
        String dateText = tfdateInscri.getText();
        LocalDate dateInscri;
        try {
            if (!dateText.matches("\\d{1,2}/\\d{1,2}/\\d{4}")) {
                throw new DateTimeParseException("Format de date invalide", dateText, 0);
            }
            dateInscri = LocalDate.parse(dateText, formatter);
        } catch (DateTimeParseException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de format de date");
            alert.setHeaderText(null);
            alert.setContentText("Le format de la date est invalide. Utilisez le format 'd/M/yyyy'.");
            alert.showAndWait();
            return;
        }

        try {
            String Email = tfEmail.getText();
            double longueur = Double.parseDouble(tfLongueur.getText());
            int poids = Integer.parseInt(tfPoids.getText());

            int id = progres.getIdByEmailAjoutProg(Email);
            if (id == -1) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText(null);
                alert.setContentText("Aucun utilisateur trouvé avec cet email.");
                alert.showAndWait();
                return;
            }

            ProgressionEntity progression = new ProgressionEntity(id, poids, longueur, dateInscri);
            progres.AjouterProgression(progression);

            // Calculer et mettre à jour l'IMC dans la base de données
            calculerEtAfficherIMC(progression.getId_prg(), poids, longueur);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Succès");
            alert.setHeaderText(null);
            alert.setContentText("La progression a été ajoutée avec succès !");
            alert.showAndWait();

        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de format de nombre");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez vérifier que tous les champs numériques sont correctement remplis.");
            alert.showAndWait();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de base de données");
            alert.setHeaderText(null);
            alert.setContentText("Une erreur est survenue lors de l'ajout de la progression : " + e.getMessage());
            alert.showAndWait();
            e.printStackTrace();
        }
    }*/


     /*   public static void calculerEtAfficherIMC ( int id_prg, int poids, double longueur){
            // Calcul de l'IMC

            double imc = poids / (longueur * longueur);

            // Affichage du message correspondant à l'IMC
            String description;
            if (imc < 18.5) {
                description = "Insuffisance pondérale";
            } else if (imc < 25) {
                description = "Poids normal";
            } else if (imc < 30) {
                description = "Surpoids";
            } else {
                description = "Obésité";
            }

            // Affichage du résultat
            System.out.println("IMC calculé : " + imc);
            System.out.println("Message : " + description);

            insererIMCDansBaseDeDonnees(id_prg, poids, longueur, imc, description);
        }

    private static void insererIMCDansBaseDeDonnees(int id_prg, int poids, double longueur, double imc, String description) {
        String DB_URL = "jdbc:mysql://localhost:3306/profit_db";
        String DB_USER = "root";
        String DB_PASSWORD = "";

        String sql = "UPDATE progression SET poids = ?, longueur = ?, IMC = ?, description= ?  WHERE id_prg = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, poids);
            pstmt.setDouble(2, longueur);
            pstmt.setDouble(3, imc);
            pstmt.setString(4, description);
            pstmt.setInt(5, id_prg);

            pstmt.executeUpdate();
            System.out.println("IMC inséré avec succès pour l'adhérent avec l'ID " +id_prg);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }*/





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

}


