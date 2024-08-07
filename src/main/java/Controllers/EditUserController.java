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
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class EditUserController {
    @FXML
    private TextField edage;

    @FXML
    private TextField edemail;

    @FXML
    private TextField edlogin;

    @FXML
    private TextField ednom;

    @FXML
    private AnchorPane edpane;

    @FXML
    private TextField edprenom;

    @FXML
    private TextField edrole;

    @FXML
    private TextField edtel;

    @FXML
    private Label lage;

    @FXML
    private Label lemail;

    @FXML
    private Label linom;

    @FXML
    private Label llogin2;

    @FXML
    private Label lrole;

    @FXML
    private Label lmenu;

    @FXML
    private Label lnumtel;

    @FXML
    private Label lprenom;

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

    public static UserEntity current_user;
    public static ObservableList<UserEntity> allusers;
    Parent root = null;
    ObservableList<String> roleList = FXCollections.observableArrayList("ADMIN", "COACH", "NUTRITIONNISTE", "ADHERENT");
    private Connection connection;
    UserService us = new UserService(connection);


    private UserEntity currentUser;
    private UpdateUserCallback callback;

    @FXML
    private ChoiceBox<String> chbrole = new ChoiceBox<>();

    @FXML
    private Label lprofit;

    @FXML
    void naviguerversliste(ActionEvent event) {

        FXMLLoader a = new FXMLLoader(getClass().getResource("/ListUsers.fxml"));
        try {
            Parent root = a.load();
            lmodif.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();

        }
    }
    @FXML
    public void initialize () {

        chbrole.setValue("ADMIN");
        chbrole.setItems(roleList);

    }

    @FXML
    void saveEditedUser(ActionEvent event) {
        // Mise à jour de l'utilisateur dans la base de données
        currentUser.setNom(ednom.getText());
        currentUser.setPrenom(edprenom.getText());
        currentUser.setEmail(edemail.getText());
        currentUser.setN_tel(Integer.parseInt(edtel.getText()));
        currentUser.setAge(Integer.parseInt(edage.getText()));
        currentUser.setRole(chbrole.getValue());



        UserService userService = new UserService();
        try {
            userService.update(currentUser);
            showAlert("Succès", "Utilisateur mis à jour avec succès", Alert.AlertType.INFORMATION);
            refreshFields();
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Erreur", "Erreur lors de la mise à jour de l'utilisateur", Alert.AlertType.ERROR);
        }
    }

    /*public void setUserData(UserEntity user) {
        this.currentUser = user;
        ednom.setText(user.getNom());
        edprenom.setText(user.getPrenom());
        edemail.setText(user.getEmail());
        edtel.setText(String.valueOf(user.getN_tel()));
        chbrole.setValue(user.getRole());
        edage.setText(String.valueOf(user.getAge()));
    }*/

    public void setUserData(UserEntity user, UpdateUserCallback callback) {
        this.currentUser = user;
        this.callback = callback;
        ednom.setText(user.getNom());
        edprenom.setText(user.getPrenom());
        edemail.setText(user.getEmail());
        edtel.setText(String.valueOf(user.getN_tel()));
        chbrole.setValue(user.getRole());
        edage.setText(String.valueOf(user.getAge()));
    }

    public interface UpdateUserCallback {
        void onUserUpdated(UserEntity user);
    }

    private void refreshFields() {
        ednom.setText(currentUser.getNom());
        edprenom.setText(currentUser.getPrenom());
        edemail.setText(currentUser.getEmail());
        edtel.setText(String.valueOf(currentUser.getN_tel()));
        chbrole.setValue(currentUser.getRole());
        edage.setText(String.valueOf(currentUser.getAge()));
    }

    private void showAlert(String title, String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
    @FXML
    private TextField tfrecherche;

    @FXML
    void pagePrecedente (ActionEvent event) {
        FXMLLoader a = new FXMLLoader(getClass().getResource("/ListUsers.fxml"));
        try {
            Parent root = a.load();
            tfrecherche.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private Label lmodif;

    @FXML
    void logout (ActionEvent event) {
        if (current_user != null) {
            UserService.logout(current_user);
        }
        SessionManager.clearSession();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Login.fxml"));
            Parent root = loader.load();
            lmodif.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
