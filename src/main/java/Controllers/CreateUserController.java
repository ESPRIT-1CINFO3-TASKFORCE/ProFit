package Controllers;

import Entites.UserEntity;
import Services.UserService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.PasswordGenerator;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

public class CreateUserController {

    public static UserEntity current_user;
    public static ObservableList<UserEntity> allusers;
    Parent root = null;
    ObservableList<String> roleList = FXCollections.observableArrayList("ADMIN", "COACH", "NUTRITIONNISTE", "ADHERENT");
    private Connection connection;
    UserService us = new UserService(connection);

    @FXML
    private Label labage;

    @FXML
    private Label labemail;

    @FXML
    private Label labname;

    @FXML
    private Label labnumber;

    @FXML
    private Label labprename;

    @FXML
    private TextField txtage;

    @FXML
    private TextField txtemail;

    @FXML
    private TextField txtname;

    @FXML
    private TextField txtnumber;

    @FXML
    private TextField txtprename;

    @FXML
    private Label lbienvenue;

    @FXML
    private Label lsuccess;

    @FXML
    void createUser(ActionEvent event) {

        System.out.println("enter");

        try {
            UserEntity user = new UserEntity();
            user.setNom(txtname.getText());
            user.setPrenom(txtprename.getText());
            user.setAge(Integer.parseInt(txtage.getText()));
            user.setEmail(txtemail.getText());
            user.setN_tel(Integer.parseInt(txtnumber.getText()));
            user.setLogin(user.getPrenom().toLowerCase().substring(0, 1) + user.getNom().toLowerCase().replace(" ", "") + user.getAge());
            user.setMdp(generatePassword());
            user.setRole("ADHERENT");

            if (!us.getUserByEmail(user.getEmail())) {
                us.ajouter(user);
                System.out.println("User added successfully.");
                sendFirstMail(user);
            } else System.out.println("this email is already used");
        } catch (Exception e) {
            e.printStackTrace();
        }

        FXMLLoader l = new FXMLLoader(getClass().getResource("/Success.fxml"));
        Parent root = null;
        try {
            root = l.load();
            txtname.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
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
                "Cordialement,+" +
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
    private Label lfitness;

    @FXML
    void pagePrecedente (ActionEvent event) {
        try {
            // Load the FXML file for the previous page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Accueil.fxml"));
            Parent root = loader.load();
            if (txtname != null)
                txtname.getScene().setRoot(root);
            if (lfitness != null)
                lfitness.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




}
