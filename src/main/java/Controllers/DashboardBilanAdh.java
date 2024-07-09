package Controllers;

import Entites.RegimeEntity;
import Services.RegimeService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import Entites.ProgressionEntity;
import Services.ProgressionService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class DashboardBilanAdh {

    @FXML
    private BarChart<String, Integer> StatistiquePoidsDate;

    @FXML
    private VBox card1;

    @FXML
    private Label idaffichageimc;

    @FXML
    private Label idimc;

    @FXML
    private Label idlongueur;

    @FXML
    private Text idnom;

    @FXML
    private Label idpoids;

    @FXML
    private Text idprenom;

    @FXML
    private NumberAxis idypoids;


    @FXML
    private Label tfdescription;
    @FXML
    private TextField colemailDash;

    @FXML
    private Label tfnomPrenom;

    @FXML
    private Label tfpoids;
    @FXML
    private Label tfDatefin;

    @FXML
    private Label tfNomRegime;
    @FXML
    private Button SerachDashboardbtn;
    private static final int CLIENT_ID = 1234;


    ProgressionService progres = new ProgressionService();

    @FXML
    public void initialize() {

        // ************BarChart Statistique poids, masse_musc, imc et date*********

        // Formatter pour formater les dates dans le format "dd/MM/yyyy"
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        // Récupérer les progressions d'un client spécifique (par exemple, client avec ID 1234)
        List<ProgressionEntity> progres = ProgressionService.getInstance().getProgressionsByClientId(1234);

        // Séries pour stocker les données à afficher dans le graphique
        XYChart.Series<String, Integer> poidsSeries = new XYChart.Series<>();
        poidsSeries.setName("Évolution du poids");

        XYChart.Series<String, Integer> IMCSeries = new XYChart.Series<>();
        IMCSeries.setName("Évolution de IMC");

        // Parcourir toutes les progressions récupérées
        for (ProgressionEntity progression : progres) {
            // Convertir LocalDate en String
            String dateFormatted = progression.getDate_inscri().format(formatter);

            poidsSeries.getData().add(new XYChart.Data<>(dateFormatted, progression.getPoids()));
            IMCSeries.getData().add(new XYChart.Data<>(dateFormatted, progression.getIMC()));
        }

        // Ajouter les séries de données au graphique
        StatistiquePoidsDate.getData().addAll(poidsSeries, IMCSeries);
        idypoids.setAutoRanging(false);
        idypoids.setUpperBound(130);

        //*****************************Nom et Prénom******************************
        // Récupérer les données personnelles nom et prénom
        List<ProgressionEntity> progressions = ProgressionService.getInstance().getProgressionsWithUserDetails();
        System.out.println("Progressions retrieved: " + progressions.size());

        // Utiliser Optional pour vérifier et récupérer la première progression
        Optional<ProgressionEntity> optionalProgression = progressions.stream().findFirst();
        optionalProgression.ifPresent(progression -> {
            System.out.println("Setting labels for user: " + progression.getNom() + " et " + progression.getPrenom());
            // Vérifiez que les champs ne sont pas nuls
            String nom = progression.getNom();
            String prenom = progression.getPrenom();
            System.out.println("Nom : " + nom + ", Prénom : " + prenom);

            // Afficher le nom et le prénom dans le même label
            String nomPrenom = (nom != null ? nom : "") + " " + (prenom != null ? prenom : "");
            tfnomPrenom.setText(nomPrenom);

            //**************************Poids et Description*******************************************
            List<ProgressionEntity> progressDP = ProgressionService.getInstance().getDESCPOIDSdeatils();
            System.out.println("Progressions retrieved: " + progressDP.size());

            // Utiliser Optional pour vérifier et récupérer la première progression
            Optional<ProgressionEntity> optionalProgr = progressDP.stream().findFirst();
            optionalProgr.ifPresent(progressionEntity -> {


                try {
                    System.out.println("ProgressionEntity found");

                    // Vérifier que les getters ne causent pas d'erreurs
                    String poidsText = "Poids : " + progressionEntity.getPoids() + " kg";
                    System.out.println("Poids text: " + poidsText);
                    tfpoids.setText(poidsText);

                    // Afficher la description dans un autre label
                    String descriptionText = "Desc :" + (progressionEntity.getDescription() != null ? progressionEntity.getDescription() : "");
                    System.out.println("Desc text: " + descriptionText);
                    tfdescription.setText(descriptionText);
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("Error setting text fields: " + e.getMessage());
                }

            });
        })
        ;


        List<RegimeEntity> regimes = RegimeService.getInstance().getNomDateRegimAvecUserDetails(CLIENT_ID);

        if (!regimes.isEmpty()) {
            RegimeEntity regime = regimes.get(0); // Supposons que vous voulez afficher le premier résultat
            tfNomRegime.setText("NomRegime :" + regime.getNom_regime());
            tfDatefin.setText("date fin :" + regime.getDate_fin().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));

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
    void SerachDashboardbtn(ActionEvent event) {
        String email = colemailDash.getText();
        updateDashboardData(email);
    }

    private void updateDashboardData(String email) {
        List<ProgressionEntity> progressions = ProgressionService.getInstance().getProgressionsByEmail(email);
        System.out.println("Progressions retrieved for email " + email + ": " + progressions.size());

        // Trier les progressions par date d'inscription
        Collections.sort(progressions, Comparator.comparing(ProgressionEntity::getDate_inscri));

        if (!progressions.isEmpty()) {
            ProgressionEntity latestProgression = progressions.get(progressions.size() - 1);

            String nomPrenom = (latestProgression.getNom() != null ? latestProgression.getNom() : "") + " " +
                    (latestProgression.getPrenom() != null ? latestProgression.getPrenom() : "");
            tfnomPrenom.setText(nomPrenom);

            String poidsText = "Poids : " + latestProgression.getPoids() + " kg";
            tfpoids.setText(poidsText);

            String descriptionText = "Description :" + (latestProgression.getDescription() != null ? latestProgression.getDescription() : "");
            tfdescription.setText(descriptionText);

            tfNomRegime.setText("NomRegime :" + latestProgression.getNomregime());
            tfDatefin.setText("date fin :" + latestProgression.getDate_finR().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));

            StatistiquePoidsDate.getData().clear();
            XYChart.Series<String, Integer> poidsSeries = new XYChart.Series<>();
            poidsSeries.setName("Évolution du poids");
            XYChart.Series<String, Integer> IMCSeries = new XYChart.Series<>();
            IMCSeries.setName("Évolution de IMC");

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            for (ProgressionEntity progression : progressions) {
                String dateFormatted = progression.getDate_inscri().format(formatter);
                poidsSeries.getData().add(new XYChart.Data<>(dateFormatted, progression.getPoids()));
                IMCSeries.getData().add(new XYChart.Data<>(dateFormatted, progression.getIMC()));
            }
            StatistiquePoidsDate.getData().addAll(poidsSeries, IMCSeries);
        } else {
            tfnomPrenom.setText("");
            tfpoids.setText("");
            tfdescription.setText("");
            tfNomRegime.setText("");
            tfDatefin.setText("");
            StatistiquePoidsDate.getData().clear();
        }
    }
}














