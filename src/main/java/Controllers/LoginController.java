package Controllers;

import Entites.UserEntity;
import Services.UserService;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

public class LoginController {

    @FXML
    private Label lcreer;

    @FXML
    private Label llogin;

    @FXML
    private Label lmdp;

    @FXML
    private TextField tflogin;

    @FXML
    private TextField tfmdp;

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

    @FXML
    void signin(ActionEvent event) throws IOException {
        String login = tflogin.getText();
        String mdp = tfmdp.getText();
        System.out.println(login);
        System.out.println(mdp);

        if (login.length() > 0 && mdp.length() > 6) {
            current_user = UserService.login(login, mdp);
            if (current_user == null) System.out.println("erreur");
            else {
                System.out.println("role" + current_user.getRole());
                if (current_user.getRole().equals("ADMIN")) {
                    System.out.println("IS ADMIN");

                    FXMLLoader a = new FXMLLoader(getClass().getResource("/ListUsers.fxml"));
                    try {
                        Parent e = a.load();
                        tfmdp.getScene().setRoot(e);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                } else if (current_user.getRole().equals("COACH")) {
                    System.out.println("IS COACH");

                    //nom d'interface à modifier avec interface coach
                    FXMLLoader a = new FXMLLoader(getClass().getResource("/GererUsers.fxml"));
                    try {
                        Parent e = a.load();
                        tfmdp.getScene().setRoot(e);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                } else if (current_user.getRole().equals("NUTRITIONNISTE")) {
                    System.out.println("IS NUTRITIONNISTE");

                    //nom d'interface à modifier avec interface nutritionniste
                    FXMLLoader a = new FXMLLoader(getClass().getResource("/GererUsers.fxml"));
                    try {
                        Parent e = a.load();
                        tfmdp.getScene().setRoot(e);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                } else if (current_user.getRole().equals("ADHERENT")) {
                    System.out.println("IS ADHERENT");

                    //nom d'interface à modifier avec interface adherent
                    FXMLLoader a = new FXMLLoader(getClass().getResource("/GererUsers.fxml"));
                    try {
                        Parent e = a.load();
                        tfmdp.getScene().setRoot(e);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                }
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
            if (tfmdp != null)
                tfmdp.getScene().setRoot(root);
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
            tfmdp.getScene().setRoot(e);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}

