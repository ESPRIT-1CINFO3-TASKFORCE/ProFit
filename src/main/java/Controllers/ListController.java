package Controllers;

import Entites.UserEntity;
import Services.UserService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class ListController {

    public static UserEntity current_user;
    public static ObservableList<UserEntity> allusers;
    Parent root = null;
    ObservableList<String> roleList = FXCollections.observableArrayList("ADMIN", "COACH", "NUTRITIONNISTE", "ADHERENT");
    private Connection connection;
    UserService us = new UserService(connection);

    @FXML
    private Label lmenu;

    @FXML
    private TableView<UserEntity> tableview;
    @FXML
    private TableColumn<UserEntity, String> coemail;
    @FXML
    private TableColumn<UserEntity, String> conom;
    @FXML
    private TableColumn<UserEntity, String> coprenom;
    @FXML
    private TableColumn<UserEntity, String> corole;
    @FXML
    private TextField tfrecherche;

    public void initialize() {
        conom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        coprenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        coemail.setCellValueFactory(new PropertyValueFactory<>("email"));
        corole.setCellValueFactory(new PropertyValueFactory<>("role"));

        try {
            allusers = FXCollections.observableArrayList(us.readAll());
        } catch (Exception e) {
            e.printStackTrace();
        }

        tableview.setItems(allusers);
    }

    @FXML
    void AjouterUser(ActionEvent event) {
        // Implementation for adding a user

        FXMLLoader a = new FXMLLoader(getClass().getResource("/ListUsers.fxml"));
        try {
            root = a.load();
            lmenu.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();

        }

    }

    @FXML
    void chercherUser(ActionEvent event) {
        // Implementation for searching a user
    }
    @FXML
    void handelActivate(ActionEvent event) {
        UserEntity selectedUser = tableview.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            try {
                us.activateUser(selectedUser.getId());
                refreshTable();
                showAlert("Succès", "Utilisateur activé avec succès", Alert.AlertType.INFORMATION);
            } catch (SQLException e) {
                e.printStackTrace();
                showAlert("Erreur", "Erreur lors de l'activation de l'utilisateur", Alert.AlertType.ERROR);
            }
        } else {
            showAlert("Erreur", "Veuillez sélectionner un utilisateur", Alert.AlertType.WARNING);
        }
    }

    @FXML
    void handelDesactivate(ActionEvent event) {
        UserEntity selectedUser = tableview.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            try {
                us.deactivateUser(selectedUser.getId());
                refreshTable();
                showAlert("Succès", "Utilisateur désactivé avec succès", Alert.AlertType.INFORMATION);
            } catch (SQLException e) {
                e.printStackTrace();
                showAlert("Erreur", "Erreur lors de la désactivation de l'utilisateur", Alert.AlertType.ERROR);
            }
        } else {
            showAlert("Erreur", "Veuillez sélectionner un utilisateur", Alert.AlertType.WARNING);
        }
    }
    private void refreshTable() {
        try {
            allusers = FXCollections.observableArrayList(us.readAll());
            tableview.setItems(allusers);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    void handelEdit(ActionEvent event) {
        UserEntity selectedUser = tableview.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierUser.fxml"));
                Parent root = loader.load();
                EditUserController editUserController = loader.getController();
                editUserController.setUserData(selectedUser);

                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
