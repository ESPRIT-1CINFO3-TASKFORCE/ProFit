package Controllers;

import Entites.UserEntity;
import Services.UserService;
import Utils.SessionManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

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
    @FXML
    void chat(ActionEvent event) {}

    @FXML
    void coach(ActionEvent event) {}

    @FXML
    void forum(ActionEvent event) {}

    @FXML
    void nutritionniste(ActionEvent event)
        throws IOException {
            FXMLLoader l = new FXMLLoader(getClass().getResource("/PageInitial.fxml"));//donner notre resources ,donner l'interface a naviguer
            try {
                Parent root = l.load();//recharger notre resources

                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.setTitle("Acceuil nutritionniste");
                stage.show();
            } catch (IOException e) {
                System.out.println(e);
            }
    }

    @FXML
    void planning(ActionEvent event) {}

    @FXML
    void store(ActionEvent event) {}

    @FXML
    private Label lprofit;

    @FXML
    void naviguerversliste(ActionEvent event) {

        FXMLLoader a = new FXMLLoader(getClass().getResource("/ListUsers.fxml"));
        try {
            Parent root = a.load();
            tfrecherche.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    public void initialize() {

        // Initialize database connection
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/profit_db", "root", "");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        us = new UserService(connection);
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
    private Label lajout;

    @FXML
    void AjouterUser(ActionEvent event) {

        FXMLLoader a = new FXMLLoader(getClass().getResource("/AjouterUser.fxml"));
        try {
            root = a.load();
            tfrecherche.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();

        }

    }

    @FXML
    void chercherUser(ActionEvent event) {
        String email = tfrecherche.getText().trim();
        if (!email.isEmpty()) {
            List<UserEntity> users = us.getUserEmail(email);
            if (!users.isEmpty()) {
                allusers = FXCollections.observableArrayList(users);
                tableview.setItems(allusers);
            } else {
                showAlert("Information", "Aucun utilisateur trouvé avec cet email", Alert.AlertType.INFORMATION);
                refreshTable();
            }
        } else {
            showAlert("Erreur", "Veuillez entrer un email", Alert.AlertType.WARNING);
        }
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

    @FXML
    void handleDeleteUser(ActionEvent event) {
        UserEntity selectedUser = tableview.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            try {
                us.supprimer(selectedUser);
                refreshTable();
                showAlert("Succès", "Utilisateur supprimé avec succès", Alert.AlertType.INFORMATION);
            } catch (SQLException e) {
                e.printStackTrace();
                showAlert("Erreur", "Erreur lors de la suppression de l'utilisateur", Alert.AlertType.ERROR);
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

    /*@FXML
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
    }*/

    @FXML
    void handelEdit(ActionEvent event) {
        UserEntity selectedUser = tableview.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierUser.fxml"));
                Parent editUserView = loader.load();
                EditUserController editUserController = loader.getController();
                editUserController.setUserData(selectedUser, new EditUserController.UpdateUserCallback() {
                    @Override
                    public void onUserUpdated(UserEntity user) {
                        refreshTable();
                    }
                });

                Stage stage = new Stage();
                stage.setScene(new Scene(editUserView));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            showAlert("Erreur", "Veuillez sélectionner un utilisateur à modifier", Alert.AlertType.WARNING);
        }
    }

    /*@FXML
    void retourmenu(ActionEvent event) {
        FXMLLoader a = new FXMLLoader(getClass().getResource("/sample.fxml"));
        try {
            root = a.load();
            lmenu.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/



    @FXML
    void logout (ActionEvent event) {
        if (current_user != null) {
            UserService.logout(current_user);
        }
        SessionManager.clearSession();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Login.fxml"));
            Parent root = loader.load();
            tfrecherche.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}