package Controllers;

import Entites.ProduitEntity;
import Services.ProduitService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.sql.*;

import java.io.InputStream;
import java.util.List;

public class AfficherProduitController {
    @FXML
    private TableView<ProduitEntity> Table;

    @FXML
    private TableColumn<ProduitEntity, Void> colDelete;

    @FXML
    private TableColumn<ProduitEntity, Void> colEdit;

    @FXML
    private TableColumn<ProduitEntity, Integer> colid;

    @FXML
    private TableColumn<ProduitEntity, InputStream> colim;

    @FXML
    private TableColumn<ProduitEntity, Integer> colnp;

    @FXML
    private TableColumn<ProduitEntity, Integer> colp;

    @FXML
    private TableColumn<ProduitEntity, Integer> colqtp;
ProduitService pro = new ProduitService();
    public void initialize() {
        // Lier les colonnes aux propriétés de ProduitEntity
        colid.setCellValueFactory(new PropertyValueFactory<>("id_pro"));
        colnp.setCellValueFactory(new PropertyValueFactory<>("nom_p"));
        colp.setCellValueFactory(new PropertyValueFactory<>("prix"));
        colqtp.setCellValueFactory(new PropertyValueFactory<>("qnt"));
        colim.setCellValueFactory(new PropertyValueFactory<>("image"));

        // Ajouter les boutons d'action
        addButtonToTable();

        try {
            loadData();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadData() throws SQLException {
        List<ProduitEntity> list = pro.readAll1();
        ObservableList<ProduitEntity> data = FXCollections.observableArrayList(list);
        Table.setItems(data);
    }

    private void addButtonToTable() {
        // Ajouter le bouton Modifier
        Callback<TableColumn<ProduitEntity, Void>, TableCell<ProduitEntity, Void>> cellFactoryEdit = new Callback<>() {
            @Override
            public TableCell<ProduitEntity, Void> call(final TableColumn<ProduitEntity, Void> param) {
                final TableCell<ProduitEntity, Void> cell = new TableCell<>() {
                    private final Button btnEdit = new Button("Modifier");

                    {
                        btnEdit.setStyle("-fx-background-color: #FFA500; -fx-text-fill: white;"); // Orange background with white text
                        btnEdit.setOnAction((ActionEvent event) -> {
                            ProduitEntity data = getTableView().getItems().get(getIndex());
                            try {
                                // Charger la vue de modification
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Update.fxml"));
                                Parent root = loader.load();

                                // Passer les données du produit au contrôleur de modification
                                UpdateController controller = loader.getController();
                                controller.setProduit(data);

                                // Afficher la vue de modification dans une nouvelle fenêtre
                                Stage stage = new Stage();
                                stage.initModality(Modality.APPLICATION_MODAL);
                                stage.setTitle("Modifier Produit");
                                stage.setScene(new Scene(root));
                                stage.showAndWait();

                                // Recharger les données après la modification
                                loadData();
                            } catch (IOException | SQLException e) {
                                e.printStackTrace();
                            }
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btnEdit);
                        }
                    }
                };
                return cell;
            }
        };
        colEdit.setCellFactory(cellFactoryEdit);

        // Ajouter le bouton Supprimer
        Callback<TableColumn<ProduitEntity, Void>, TableCell<ProduitEntity, Void>> cellFactoryDelete = new Callback<>() {
            @Override
            public TableCell<ProduitEntity, Void> call(final TableColumn<ProduitEntity, Void> param) {
                final TableCell<ProduitEntity, Void> cell = new TableCell<>() {
                    private final Button btnDelete = new Button("Supprimer");

                    {
                        btnDelete.setStyle("-fx-background-color: #FF0000; -fx-text-fill: white;"); // Red background with white text
                        btnDelete.setOnAction((ActionEvent event) -> {
                            ProduitEntity data = getTableView().getItems().get(getIndex());
                            try {
                                // Appeler la méthode de suppression du service
                                pro.supprimer(data);
                                showAlert("Success", "Produit à supprimer avec succès!");
                                loadData(); // Recharger les données après la suppression
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btnDelete);
                        }
                    }
                };
                return cell;
            }
        };
        colDelete.setCellFactory(cellFactoryDelete);
    }


    /*@FXML
    public void initialize() {
        // Lier les colonnes aux propriétés de ProgressionEntity
        colid.setCellValueFactory(new PropertyValueFactory<>("id_pro"));
        colnp.setCellValueFactory(new PropertyValueFactory<>("nom_p"));
        colp.setCellValueFactory(new PropertyValueFactory<>("prix"));
        colqtp.setCellValueFactory(new PropertyValueFactory<>("qnt"));
        colim.setCellValueFactory(new PropertyValueFactory<>("image"));
        try {
            loadData();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }*/
    /*private void loadData() throws SQLException {
        List<ProduitEntity> list = pro.readAll();
        ObservableList<ProduitEntity> data = FXCollections.observableArrayList(list);
        Table.setItems(data);
    }*/

    @FXML
    void Back(ActionEvent event) {
        FXMLLoader L = new FXMLLoader(getClass().getResource("/AjouterProduit.fxml"));
        try {
            Parent root = L.load();
            Table.getScene().setRoot(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private void showAlert(String title, String message) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
