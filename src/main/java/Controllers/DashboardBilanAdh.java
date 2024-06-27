package Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import Entites.ProgressionEntity;
import Services.ProgressionService;
import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.scene.layout.AnchorPane;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class DashboardBilanAdh {

    @FXML
    private BarChart<String, Integer> StatistiquePoidsDate;

    @FXML
    private NumberAxis idypoids;

    @FXML
    private CategoryAxis idxdate;
    @FXML
    private PieChart masse_musc;
    ProgressionService progres = new ProgressionService();

    @FXML
    public void initialize() {

        //************BarChart Statistique poids et date*********
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        List<ProgressionEntity> progres = ProgressionService.getInstance().getProgressionsByClientId(72);

        XYChart.Series<String, Integer> poidsSeries = new XYChart.Series<>();
        poidsSeries.setName("Évolution du poids");

        XYChart.Series<String, Integer> masseMuscSeries = new XYChart.Series<>();
        masseMuscSeries.setName("Évolution de la masse musculaire");
        XYChart.Series<String, Integer> IMCSeries = new XYChart.Series<>();
        IMCSeries.setName("Évolution de IMC");



        for (ProgressionEntity progression : progres) {
            // Convertir LocalDate en String
            String dateFormatted = progression.getDate_inscri().format(formatter);
            poidsSeries.getData().add(new XYChart.Data<>(dateFormatted, progression.getPoids()));
            masseMuscSeries.getData().add(new XYChart.Data<>(dateFormatted, progression.getMasse_musc()));
            IMCSeries.getData().add(new XYChart.Data<>(dateFormatted, progression.getIMC()));
        }

        // Créez un axe Y avec une échelle maximale de 200
        // ou 150 selon vos besoins

        // Ajoutez les séries de données au graphique
        StatistiquePoidsDate.getData().addAll(poidsSeries, masseMuscSeries,IMCSeries);
        idypoids.setAutoRanging(false);
        idypoids.setUpperBound(130);


        /*ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                new PieChart.Data("IMC 1", progres.getFirst().getIMC()),
                new PieChart.Data("IMC 2", progres.getLast().getIMC())
                // Ajoutez d'autres données si nécessaire
        );*/

      //  masse_musc.setData(pieChartData);

    }



        //************Piechart masse musculaire*********
       /* for (ProgressionEntity progression : progres) {
            masse_musc.getData().add(new PieChart.Data(progression.getDate_inscri().toString(), progression.getMasse_musc()));
        }*/
    }












