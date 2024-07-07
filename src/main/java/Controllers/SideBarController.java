package Controllers;

import Entites.UserEntity;
import Services.UserService;
import Utils.SessionManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.Connection;

public class SideBarController {

    public static UserEntity current_user;
    public static ObservableList<UserEntity> allusers;
    Parent root = null;
    ObservableList<String> roleList = FXCollections.observableArrayList("ADMIN", "COACH", "NUTRITIONNISTE", "ADHERENT");
    private Connection connection;
    UserService us = new UserService(connection);

    @FXML
    private Label lprofit;

    @FXML
    private TextField tfrecherche;

    @FXML
    void naviguerversliste(ActionEvent event) {

        FXMLLoader a = new FXMLLoader(getClass().getResource("/ListUsers.fxml"));
        try {
            Parent root = a.load();
            lprofit.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();

        }
    }
    @FXML
    void chat(ActionEvent event) {

    }

    @FXML
    void coach(ActionEvent event) {

    }

    @FXML
    void forum(ActionEvent event) {

    }


    @FXML
    void nutritionniste(ActionEvent event) {

    }

    @FXML
    void planning(ActionEvent event) {

    }

    @FXML
    void store(ActionEvent event) {

    }

    @FXML
    void logout (ActionEvent event) {
        if (current_user != null) {
            UserService.logout(current_user);
        }
        SessionManager.clearSession();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Login.fxml"));
            Parent root = loader.load();
            lprofit.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
