package Controllers;

import Entites.UserEntity;
import Services.UserService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class UserController {

    public static UserEntity current_user;

    @FXML
    private TextField loginField;

    @FXML
    private TextField mdpField;

    /* @FXML
     */
    @FXML
    private TextField tfage;

    @FXML
    private TextField tfemail;

    @FXML
    private TextField tfnom;

    @FXML
    private TextField tfnumtel;

    @FXML
    private TextField tfprenom;

    @FXML
    private TableColumn<UserEntity, Integer> colage;

    @FXML
    private TableColumn<UserEntity, String> colemail;

    @FXML
    private TableColumn<UserEntity, String> colnom;

    @FXML
    private TableColumn<UserEntity, Integer> colnum;

    @FXML
    private TableColumn<UserEntity, String> colprenom;
    @FXML
    private TableView<UserEntity> tableview;

    @FXML
    private Label tflogin;

    @FXML
    private Label tfmdp;

    @FXML
    private ImageView tfphoto;

    @FXML
    void login(ActionEvent event) {

    }

    @FXML
    void mdp(ActionEvent event) {

    }

    @FXML
     void signin (ActionEvent event) throws IOException {
        FXMLLoader l = new FXMLLoader(getClass().getResource("/Login.fxml"));
        Parent root = null;
        try {
            root = l.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String login  = loginField.getText();
        String mdp = mdpField.getText();

        // controle
        if(login.matches("^(.+)@(\\S+) $")&& mdp.length()>=8)
        {
        UserEntity user = UserService.login(login,mdp);
        if (user == null) {

         }//erreur
            else {
            current_user = UserService.login(login, mdp);
            if(current_user.getRole()==1){}
                else if(current_user.getRole()==2){}
                    else if(current_user.getRole()==3){}
                        else if(current_user.getRole()==4){}
        }

        }
    }


    UserService us = new UserService();

    @FXML
    void AjouterUser(ActionEvent event) {

        try {
            us.ajouter(new UserEntity(0,Integer.parseInt(tfage.getText()), 5, 7, 6, 5, Integer.parseInt(tfnumtel.getText()), tfnom.getText(), tfprenom.getText(), "", "", tfemail.getText(), 1));
            System.out.println("User added successfully.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void Afficheruser(ActionEvent event) {
        FXMLLoader l = new FXMLLoader(getClass().getResource("/AfficherUser.fxml"));


        try {
            Parent root = l.load();
            tfnom.getScene().setRoot(root);

            // Fetch the last user
            UserEntity lastUser = us.readLastUser();

            if (lastUser != null) {
                // Assuming you have labels to display user details
                Label lname = (Label) root.lookup("#lname");
                Label lprename = (Label) root.lookup("#lprename");
                Label lage = (Label) root.lookup("#lage");
                Label lemail = (Label) root.lookup("#lemail");
                Label lnumber = (Label) root.lookup("#lnumber");

                lname.setText(lastUser.getNom());
                lprename.setText(lastUser.getPrenom());
                lage.setText(String.valueOf(lastUser.getAge()));
                lemail.setText(lastUser.getEmail());
                lnumber.setText(String.valueOf(lastUser.getN_tel()));
            }
        } catch (IOException | SQLException e) {
            System.out.println(e);
        }
    }

    @FXML
    void pagePrecedente(ActionEvent event) {
        try {
            // Load the FXML file for the previous page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GererUser.fxml"));
            Parent root = loader.load();

            // Get the current stage and set the new scene
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    void AfficherAllUsers() throws IOException {


    }

    @FXML
    void afficherAll(ActionEvent event) {
        System.out.println("test");

        FXMLLoader l = new FXMLLoader(getClass().getResource("/AfficherAllUsers.fxml"));


        try {
            Parent root = l.load();
            tfnom.getScene().setRoot(root);
        } catch (Exception e) {
            System.out.println(e);
        }


    }

    private void loadData() throws SQLException {
        List<UserEntity> list = us.readAll();
        ObservableList<UserEntity> data = FXCollections.observableArrayList(list);
        tableview.setItems(data);
    }

    @FXML
    void pagePrecedenteu(ActionEvent event) throws IOException {


        // Load the FXML file for the previous page
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherAllUsers.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Get the current stage and set the new scene
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.getScene().setRoot(root);
    }


}

