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
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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
    private TableColumn<ProgressionEntity, Integer> colid;

    @FXML
    private TableColumn<ProgressionEntity, Integer> colimc;

    @FXML
    private TableColumn<ProgressionEntity, Integer> collongueur;

    @FXML
    private TableColumn<ProgressionEntity, Integer> colpoids;



    @FXML
    private Button modifierprogression;

    ProgressionService progres = new ProgressionService();


    @FXML
    public void initialize() {
        // Lier les colonnes aux propriétés de ProgressionEntity
        colid.setCellValueFactory(new PropertyValueFactory<>("id"));
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
    void Updatebtn(ActionEvent event) {

    }




}






