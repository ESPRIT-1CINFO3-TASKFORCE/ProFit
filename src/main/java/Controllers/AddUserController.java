package Controllers;


import Entites.UserEntity;
import Services.UserService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class AddUserController {

    public static UserEntity current_user;
    public static ObservableList<UserEntity> allusers;
    Parent root = null;
    ObservableList<String> roleList = FXCollections.observableArrayList("ADMIN", "COACH", "NUTRITIONNISTE", "ADHERENT");
    private Connection connection;
    UserService us = new UserService(connection);

    @FXML
    private ChoiceBox<String> chrole = new ChoiceBox<>();
    @FXML
    private TableView<UserEntity> tableview = new TableView<>();

    @FXML
    private Label lage;

    @FXML
    private Label lemail;

    @FXML
    private Label linom;

    @FXML
    private Label llogin2;

    @FXML
    private Label lmdp2;

    @FXML
    private Label lmenu;

    @FXML
    private Label lnumtel;

    @FXML
    private Label lprenom;

    @FXML
    private Label lrole;

    @FXML
    private TextField tfage;

    @FXML
    private TextField tfemail;

    @FXML
    private TextField tflogin2;

    @FXML
    private TextField tfmdp2;

    @FXML
    private TextField tfnom;

    @FXML
    private TextField tfnumtel;

    @FXML
    private TextField tfprenom;

   /* @FXML
    void AjouterUser(ActionEvent event) {
        FXMLLoader a = new FXMLLoader(getClass().getResource("/ListUsers.fxml"));
        try {
            root = a.load();
            lmenu.getScene().setRoot(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }*/

    @FXML
    void InsertUser(ActionEvent event) {

        FXMLLoader l = new FXMLLoader(getClass().getResource("/AjouterUser.fxml"));
        Parent root = null;
        try {
            root = l.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            UserEntity user = new UserEntity();
            user.setAge(Integer.parseInt(tfage.getText()));
            user.setN_tel(Integer.parseInt(tfnumtel.getText()));
            user.setNom(tfnom.getText());
            user.setPrenom(tfprenom.getText());
            user.setLogin(tflogin2.getText());
            user.setMdp(tfmdp2.getText());
            user.setEmail(tfemail.getText());
            user.setRole(chrole.getValue());

            if (!us.getUserByEmail(user.getEmail())) {
                us.ajouter(user);
                System.out.println("User added successfully.");
            } else System.out.println("this email is already used");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void pagePrecedente(ActionEvent event) {
        try {
            // Load the FXML file for the previous page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ListUsers.fxml"));
            Parent root = loader.load();
            if (linom != null)
                linom.getScene().setRoot(root);
            if (tableview != null)
                tableview.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void initialize () {

        chrole.setValue("ADMIN");
        chrole.setItems(roleList);

    }

}

