package Controllers;

import Entites.UserEntity;
import Services.UserService;
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
import java.util.Properties;

public class ForgotPwdController {

    @FXML
    private Label labemail;
    @FXML
    private TextField txtemailforgot;
    private Connection connection;
    UserService us = new UserService(connection);

    private UserService userService = new UserService();

    @FXML
    void Sendemail(ActionEvent event) {
        String email = txtemailforgot.getText();
        UserEntity user = userService.geEmailUser(email);

        if (user != null) {
            String newPassword = generatePassword();
            user.setMdp(newPassword); // Assurez-vous que le mot de passe est haché si nécessaire
            userService.updateUserPassword(user);

            try {
                sendResetPasswordEmail(user, newPassword);
                showAlert("Succès", "Un email contenant votre nouveau mot de passe a été envoyé.", Alert.AlertType.INFORMATION);
            } catch (MessagingException e) {
                e.printStackTrace();
                showAlert("Erreur", "Erreur lors de l'envoi de l'email.", Alert.AlertType.ERROR);
            }
        } else {
            showAlert("Erreur", "Utilisateur non trouvé.", Alert.AlertType.ERROR);
        }
    }

    private String generatePassword() {
        CharacterRule alphabets = new CharacterRule(EnglishCharacterData.Alphabetical);
        CharacterRule digits = new CharacterRule(EnglishCharacterData.Digit);
        CharacterRule special = new CharacterRule(EnglishCharacterData.Special);

        PasswordGenerator passwordGenerator = new PasswordGenerator();
        String password = passwordGenerator.generatePassword(8, alphabets, digits, special);
        System.out.println(password);
        return password;
    }

    private void sendResetPasswordEmail(UserEntity user, String newPassword) throws MessagingException {
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

        String emailBody = "Bonjour,\n\n" +
                "Votre nouveau mot de passe est : " + newPassword + "\n\n" +
                "Veuillez garder ce mot de passe sécurisé et envisagez de le changer après votre connexion.\n\n" +
                "Cordialement,\n" +
                "L'équipe ProFit";

        Message message = prepareMessage(session, myEmail, user.getEmail(), emailBody);
        Transport.send(message);
    }

    private Message prepareMessage(Session session, String myEmail, String recipientEmail, String msg) {
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(myEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            message.setSubject("Réinitialisation du mot de passe ProFit");
            message.setText(msg);
            return message;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private Label lfitness;

    @FXML
    void pagePrecedente (ActionEvent event) {
        try {
            // Load the FXML file for the previous page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Accueil.fxml"));
            Parent root = loader.load();
            if (labemail != null)
                labemail.getScene().setRoot(root);
            if (lfitness != null)
                lfitness.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

