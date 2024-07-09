package Controllers;

import Entites.UserEntity;
import Services.UserService;
import Utils.SessionManager;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    private Label lcreer;

    @FXML
    private Label llogin;

    @FXML
    private Label lmdp;

    @FXML
    private TextField tflogin;

    @FXML
    private PasswordField tfpmdp;

    public static UserEntity current_user;
    public static ObservableList<UserEntity> allusers;
    private Connection conn;
    UserService us = new UserService(conn);

    // INTERFACE LOGIN
    //SIGNIN

    private void loadUsersFromDatabase () {
        List<UserEntity> users = us.readAll();
        //System.out.println(users.size());
        allusers.setAll(users);
        System.out.println("ID:" + allusers.get(5).getId());
    }


    @Override
    public void initialize (URL url, ResourceBundle resources) {
        current_user = SessionManager.getSession();
        if (current_user != null) {
            //redirectToRoleInterface(current_user.getRole());
        }
    }

    private void redirectToRoleInterface(String role) {
        try {
            FXMLLoader loader = new FXMLLoader();
            switch (role) {
                case "ADMIN":
                    loader.setLocation(getClass().getResource("/SideBar.fxml"));
                    break;
                case "COACH":
                    loader.setLocation(getClass().getResource("/SideBar.fxml"));
                    break;
                case "NUTRITIONNISTE":
                    loader.setLocation(getClass().getResource("/SideBar.fxml"));
                    break;
                case "ADHERENT":
                    loader.setLocation(getClass().getResource("/SideBar.fxml"));
                    break;
                default:
                    return;
            }
            Parent root = loader.load();
            tflogin.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void signin (ActionEvent event) {
        String login = tflogin.getText();
        String mdp = tfpmdp.getText();

        if (login.length() > 0 && mdp.length() > 6) {
            current_user = UserService.login(login, mdp);
            if (current_user == null) {
                System.out.println("Erreur");
            } else {
                SessionManager.saveSession(current_user);
                redirectToRoleInterface(current_user.getRole());
            }
        }
    }

    @FXML
    private Label lfitness;
    @FXML
    void pagePrecedente (ActionEvent event) {
        try {
            // Load the FXML file for the previous page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Accueil.fxml"));
            Parent root = loader.load();
            if (tfpmdp != null)
                tfpmdp.getScene().setRoot(root);
            if (lfitness != null)
                lfitness.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void forgotpassword (ActionEvent event) {

        FXMLLoader a = new FXMLLoader(getClass().getResource("/MdpOublier.fxml"));
        try {
            Parent e = a.load();
            tfpmdp.getScene().setRoot(e);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}

