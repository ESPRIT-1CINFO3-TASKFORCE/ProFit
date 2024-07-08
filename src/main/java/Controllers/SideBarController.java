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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

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
    void coach(ActionEvent event) {

    }
    @FXML
    void chat(ActionEvent event) {
        try {
            // Load the Chat.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Chat.fxml"));
            Parent chatRoot = loader.load();

            // Get the controller associated with Chat.fxml
            ChatController chatController = loader.getController();

            // Call the proceedToChat method on the ChatController
            chatController.proceedToChat();

            // Display the chat scene
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(chatRoot));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void forum(ActionEvent event) {
        FXMLLoader a = new FXMLLoader(getClass().getResource("/forums.fxml"));
        try {
            Parent root = a.load();
            Scene scene = new Scene(root, 900, 600);

            // Get the current stage
            Stage currentStage = (Stage) lprofit.getScene().getWindow();
            currentStage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    @FXML
    void nutritionniste(ActionEvent event) throws IOException {
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
