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
import javafx.scene.control.*;
import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.PasswordGenerator;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class AddUserController {

    public static UserEntity current_user;
    public static ObservableList<UserEntity> allusers;
    Parent root = null;
    ObservableList<String> roleList = FXCollections.observableArrayList("ADMIN", "COACH", "NUTRITIONNISTE", "ADHERENT");
    private Connection connection;
    UserService us = new UserService(connection);


    @FXML
    void chat(ActionEvent event) {}

    @FXML
    void coach(ActionEvent event) {}

    @FXML
    void forum(ActionEvent event) {}

    @FXML
    private Label lprofit;

    @FXML
    private Label lajout;

    @FXML
    void naviguerversliste(ActionEvent event) {

        FXMLLoader a = new FXMLLoader(getClass().getResource("/ListUsers.fxml"));
        try {
            Parent root = a.load();
            lajout.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    @FXML
    void nutritionniste(ActionEvent event) {}

    @FXML
    void planning(ActionEvent event) {}

    @FXML
    void store(ActionEvent event) {}

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
    private TextField tfnom;

    @FXML
    private TextField tfnumtel;

    @FXML
    private TextField tfprenom;
    @FXML
    private TextField tfrecherche;

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

        try {
            UserEntity user = new UserEntity();
            user.setAge(Integer.parseInt(tfage.getText()));
            user.setN_tel(Integer.parseInt(tfnumtel.getText()));
            user.setNom(tfnom.getText());
            user.setPrenom(tfprenom.getText());
            user.setEmail(tfemail.getText());
            user.setLogin(user.getPrenom().toLowerCase().substring(0, 1) + user.getNom().toLowerCase().replace(" ", "") + user.getAge());
            user.setMdp(generatePassword());
            user.setRole(chrole.getValue());

            if (!us.getUserByEmail(user.getEmail())) {
                us.ajouter(user);
                System.out.println("User added successfully.");
                try {
                    sendFirstMail(user);
                } catch (MessagingException e) {
                    throw new RuntimeException(e);
                }
            } else System.out.println("this email is already used");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    public String generatePassword() {
        CharacterRule alphabets = new CharacterRule(EnglishCharacterData.Alphabetical);
        CharacterRule digits = new CharacterRule(EnglishCharacterData.Digit);
        CharacterRule special = new CharacterRule(EnglishCharacterData.Special);

        PasswordGenerator passwordGenerator = new PasswordGenerator();
        String password = passwordGenerator.generatePassword(8, alphabets, digits, special);
        System.out.println(password);
        return password;
    }

    private void sendFirstMail(UserEntity user) throws MessagingException {
        Properties prop = new Properties();
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        String myEmail = "malek.moalla888@gmail.com";
        String mdp = "skhn azqu wjtn wepf";
        Session session = Session.getInstance(prop, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(myEmail, mdp);

            }
        });
        String emailBody = "Bonjour,\n" +
                "\n" +
                "Votre nouveau Login est : " + user.getLogin() + "\n" +
                "\n" +
                "Votre mot de passe est : " + user.getMdp() + "\n" +
                "\n" +
                "Veuillez garder ce mot de passe sécurisé et envisagez de le changer après votre connexion.\n\n" +
                "\n" +
                "Cordialement," +
                "\n" +
                "L'équipe ProFit";

        Message message = prepareMessage(session, myEmail, user.getEmail(), emailBody);
        if (message != null) {
            new Alert(Alert.AlertType.INFORMATION, "Send Email Successfully").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Please Try Again").show();
        }
        Transport.send(message);
    }

    private Message prepareMessage(Session session, String myEmail, String recipientEmail, String msg) {
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(myEmail));
            message.setRecipients(Message.RecipientType.TO,
                    new InternetAddress[]
                            {
                                    new InternetAddress(recipientEmail)
                            });
            message.setSubject("Welcome to ProFit");
            message.setText(msg);
            return message;


        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @FXML
    void pagePrecedente(ActionEvent event) {
        try {
            // Load the FXML file for the previous page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ListUsers.fxml"));
            Parent root = loader.load();
            if (linom != null)
                linom.getScene().setRoot(root);
            if (tfrecherche != null)
                tfrecherche.getScene().setRoot(root);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void initialize () {

        chrole.setValue("ADMIN");
        chrole.setItems(roleList);

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
            linom.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}




/*import Entites.UserEntity;
import Services.UserService;
import Utils.SessionManager;
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
import java.sql.SQLException;*/

/*public class AddUserController {

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
    @FXML
    private TextField tfrecherche;

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

    /*@FXML
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
            if (tfrecherche != null)
                tfrecherche.getScene().setRoot(root);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void initialize () {

        chrole.setValue("ADMIN");
        chrole.setItems(roleList);

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
            linom.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}*/

