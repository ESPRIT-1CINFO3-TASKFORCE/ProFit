package Controllers;

import Entites.CommandeDetailsEntity;
import Entites.CommandeEntity;
import Entites.ProduitEntity;
import Services.CommandeService;
import Services.PDFExporter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class CommandeDashController implements Initializable {
    @FXML
    private TextField searchField;

    @FXML
    private TableView<CommandeEntity> commandeTableView;

    @FXML
    private TableColumn<CommandeEntity, Integer> id_commandeColumn;

    @FXML
    private TableColumn<CommandeEntity, String> etat;

    @FXML
    private TableColumn<CommandeEntity, String> date_commandeColumn;

    @FXML
    private TableColumn<CommandeEntity, Integer> id_clientColumn;

    @FXML
    private TableColumn<CommandeEntity, Double> total_commandeColumn;
    @FXML
    private Label cmd;

    @FXML
    private Button detailsButton;

    @FXML
    private Button exportPDFButton;
    @FXML
    private Label cmdencours;

    @FXML
    private Label cmdnonlivre;

    private CommandeService commandeService;
    private ObservableList<CommandeEntity> commandeList;
    @FXML
    private TableColumn<CommandeEntity, Void > changerEtat;

    public void CommandeController() {
        commandeService = new CommandeService();
    }
    public void initialize(URL location, ResourceBundle resources) {
        // Initialisation du service de commandes
        commandeService = new CommandeService();
        commandeList = FXCollections.observableArrayList();

        // Initialisation des colonnes de la TableView
        initTableColumns();
        try {
            onPane1Clicked();
            updateCountEnCours();
            updateCountEnLivré();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        // Afficher toutes les commandes
        try {
            afficherToutesLesCommandes();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void initTableColumns() {
        id_commandeColumn.setCellValueFactory(new PropertyValueFactory<>("id_c"));
        id_clientColumn.setCellValueFactory(new PropertyValueFactory<>("client_id"));
        etat.setCellValueFactory(new PropertyValueFactory<>("etat"));
        date_commandeColumn.setCellValueFactory(new PropertyValueFactory<>("date_c"));
        total_commandeColumn.setCellValueFactory(new PropertyValueFactory<>("total"));
        addButtonToTable();
        //remiseColumn.setCellValueFactory(new PropertyValueFactory<>("remise"));
        //etatColumn.setCellValueFactory(new PropertyValueFactory<>("etat"));
    }
    public void afficherToutesLesCommandes() throws SQLException {
        if (commandeService != null) {
            var commandes = commandeService.afficherToutesLesCommandes();
            commandeTableView.setItems(FXCollections.observableArrayList(commandes));
        } else {
            System.out.println("commandeService est null");
        }
    }

    @FXML
    private void handleDetailsButtonAction(ActionEvent event) {
        CommandeEntity selectedCommande = commandeTableView.getSelectionModel().getSelectedItem();
        if (selectedCommande != null) {
            // Afficher les détails de la commande sélectionnée
            System.out.println("Commande sélectionnée : " + selectedCommande);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Aucune commande sélectionnée");
            alert.setContentText("Veuillez sélectionner une commande pour voir les détails.");
            alert.showAndWait();
        }
    }

    @FXML
    private void handleExportPDFButtonAction(ActionEvent event) {
        CommandeEntity selectedCommande = commandeTableView.getSelectionModel().getSelectedItem();
        if (selectedCommande != null) {
            try {
                CommandeDetailsEntity commandeDetails = commandeService.getCommandeDetails(selectedCommande.getId_c());
                PDFExporter.exportCommandeDetailsToPDF(commandeDetails);
                showAlert("Information", "Le PDF a été généré avec succès.");
            } catch (SQLException | IOException e) {
                e.printStackTrace();
                showAlert("Erreur", "Une erreur s'est produite lors de la génération du PDF.");
            }
        } else {
            showAlert("Erreur", "Aucune commande sélectionnée");
        }
    }

    @FXML
    private void onPane1Clicked() throws SQLException {
        //var commandes = commandeService.afficherToutesLesCommandes();
        List<CommandeEntity> commandes = commandeService.afficherToutesLesCommandes();
        commandeList.clear();
        commandeList.addAll(commandes);
        // Update the label with the total number of orders
        //cmd.setText("Total Commandes: " + commandes.size());
        cmd.setText(String.valueOf(commandes.size()));
        System.out.println(commandes.size());
    }
    private void updateCountEnCours() throws SQLException {
        int countEnCours = commandeService.countCommandesByEtat("en cours");
        cmdencours.setText("" + countEnCours);
    }
    private void updateCountEnLivré() throws SQLException {
        int countEnCours = commandeService.countCommandesByEtat("Livré");
        cmdnonlivre.setText("" + countEnCours);
    }
    private void addButtonToTable() {
        changerEtat.setCellFactory(param -> new TableCell<>() {
            private final Button btnChangeStatus = new Button("Changer État");

            {
                btnChangeStatus.setStyle(
                        "-fx-background-color: #4CAF50; " + // Background color
                                "-fx-text-fill: white; " +          // Text color
                                "-fx-border-color: black; " +       // Border color
                                "-fx-border-radius: 5; " +          // Border radius
                                "-fx-background-radius: 5;"         // Background radius
                );
                btnChangeStatus.setOnAction(event -> {
                    CommandeEntity commande = getTableView().getItems().get(getIndex());
                    try {
                        changeOrderStatus(commande);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(btnChangeStatus);
                }
            }
        });
    }

    private void changeOrderStatus(CommandeEntity commande) throws SQLException {
        // Example new status, you can add logic to choose the status
        String newStatus = "Livré";
        commandeService.changerEtatCommande(commande.getId_c(), newStatus);
        afficherToutesLesCommandes(); // Refresh the table view
    }
    @FXML
    void Home(ActionEvent event) {
        FXMLLoader L = new FXMLLoader(getClass().getResource("/Side_bar.fxml"));
        try {
            Parent root = L.load();
            searchField.getScene().setRoot(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void chercherCommande(ActionEvent event) {
        try {
            int ref = Integer.parseInt(searchField.getText());
            CommandeEntity com = commandeService.trouverCommandeParId(ref) ;
            if (com != null) {
                showAlert("Information", "Voila la détails de Commande!"
                        + "\n La date de ce Commande est : " + com.getDate_c()
                        + "\n Leur État : " +com.getEtat());
            } else {
                showAlert("Information", "Ce Commande n'est pas Disponible!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
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
