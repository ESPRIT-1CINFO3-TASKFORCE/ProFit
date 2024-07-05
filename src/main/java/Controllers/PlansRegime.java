package Controllers;

import Entites.ProgressionEntity;
import Entites.RegimeEntity;

import java.io.*;
import java.nio.file.Files;
import java.util.Properties;

import Entites.UserEntity;
import Services.ProgressionService;
import Services.RegimeService;
import Services.RegimeUserService;

import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.image.WritableImage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.LogManager;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;


public class PlansRegime  {
    @FXML
    private TextField tfDatDebut;

    @FXML
    private TextField tfDatFin;

    @FXML
    private TextField tfemail;

    @FXML
    private TextField tfnomRegime;

    static {
        try (InputStream is = PlansRegime.class.getResourceAsStream("/logging.properties")) {
            LogManager.getLogManager().readConfiguration(is);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void ajouterRegime(ActionEvent event) {
        RegimeUserService regimeUserService = new RegimeUserService();

        // Formater et analyser la date
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
        String dateDebutText = tfDatDebut.getText();
        String dateFinText = tfDatFin.getText();

        LocalDate dateDebut;
        LocalDate dateFin;
        try {
            // Vérifiez que le texte de la date correspond au format attendu
            if (!dateDebutText.matches("\\d{1,2}/\\d{1,2}/\\d{4}")) {
                throw new DateTimeParseException("Format de date invalide", dateDebutText, 0);
            }

            if (!dateFinText.matches("\\d{1,2}/\\d{1,2}/\\d{4}")) {
                throw new DateTimeParseException("Format de date invalide", dateFinText, 0);
            }

            // Analyser la date
            dateDebut = LocalDate.parse(dateDebutText, formatter);
            dateFin = LocalDate.parse(dateFinText, formatter);
        } catch (DateTimeParseException e) {
            // Afficher une alerte si le format de la date est incorrect
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de format de date");
            alert.setHeaderText(null);
            alert.setContentText("Le format de la date est invalide. Utilisez le format 'd/M/yyyy'.");
            alert.showAndWait();
            return;
        }

        try {
            String nomRegime = tfnomRegime.getText();
            String recepientemail = tfemail.getText();

            // Convertir les dates en chaîne pour l'insertion dans la base de données
            String dateDebutStr = dateDebut.format(DateTimeFormatter.ISO_LOCAL_DATE);
            String dateFinStr = dateFin.format(DateTimeFormatter.ISO_LOCAL_DATE);
            int id = regimeUserService.getIdByEmail(recepientemail);

            // Créer et initialiser l'objet RegimeEntity
            RegimeEntity newRegime = new RegimeEntity();
            newRegime.setNom_regime(nomRegime);
            newRegime.setDate_debut(LocalDate.parse(dateDebutStr));
            newRegime.setDate_fin(LocalDate.parse(dateFinStr));

            // Insérer les données dans la base de données via le service
            regimeUserService.insererDonnees(dateDebutStr, dateFinStr, nomRegime, id);

            // Envoyer l'e-mail avec les données du régime
            sendEmail(recepientemail, newRegime);

            // Générer le PDF de l'interface
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            generatePDFWithSceneCapture(stage);

            // Affichez une alerte de succès
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Succès");
            alert.setHeaderText(null);
            alert.setContentText("Le régime a été ajouté avec succès !");
            alert.showAndWait();
        } catch (NumberFormatException e) {
            // Affichez une alerte si le format des coordonnées est incorrect
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de format de coordonnées");
            alert.setHeaderText(null);
            alert.setContentText("Le format des coordonnées est invalide. Veuillez entrer des nombres valides pour la latitude et la longitude.");
            alert.showAndWait();
        } catch (Exception e) {
            // Affichez une alerte en cas d'erreur
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Une erreur est survenue lors de l'ajout du régime.");
            alert.showAndWait();
            e.printStackTrace();
        }
    }


    private void sendEmail(String recipientEmail, RegimeEntity reg) {
        // Vérifier que reg est initialisé correctement avec les bonnes valeurs
        if (reg.getDate_debut() == null || reg.getDate_fin() == null) {
            System.err.println("Dates not properly initialized in RegimeEntity.");
            new Alert(Alert.AlertType.ERROR, "Dates not properly initialized.").show();
            return;
        }

        Properties prop = new Properties();
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");

        String myEmail = "malek.moalla888@gmail.com"; // Remplacez par votre adresse e-mail Gmail
        String mdp = "skhn azqu wjtn wepf";

        Session session = Session.getInstance(prop, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(myEmail, mdp);
            }
        });

        String emailBody = "Cher Utilisateur,\n" +
                "\n" +
                "Le nom de régime est: " + reg.getNom_regime() + "\n" +
                "La date de début est: " + reg.getDate_debut() + "\n" +
                "La date de fin est : " + reg.getDate_fin() + "\n" +
                "\n" +
                "Nous vous encourageons à suivre ce régime avec détermination et à respecter les règles pour atteindre vos objectifs de santé.\n" +
                "Cordialement,\n" +
                "L'équipe ProFit";

        Message message = prepareMessage(session, myEmail, recipientEmail, emailBody);

        try {
            if (message != null) {
                Transport.send(message);
                new Alert(Alert.AlertType.INFORMATION, "Send Email Successfully").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Please Try Again").show();
            }
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    private Message prepareMessage(Session session, String myEmail, String recipientEmail, String msg) {
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(myEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            message.setSubject("Welcome to ProFit");
            message.setText(msg);
            return message;
        } catch (MessagingException e) {
            e.printStackTrace();
            return null;
        }

    }


    private void generatePDFWithSceneCapture(Stage stage) {
        Scene scene = stage.getScene();
        WritableImage snapshot = scene.snapshot(null);

        // Convert WritableImage to BufferedImage
        BufferedImage bufferedImage = SwingFXUtils.fromFXImage(snapshot, null);

        String pdfPath = "interface_snapshot.pdf";

        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            // Save the image to a temporary file
            File tempFile = Files.createTempFile("snapshot", ".png").toFile();
            ImageIO.write(bufferedImage, "png", tempFile);

            // Load the image as a PDImageXObject
            PDImageXObject pdImage = PDImageXObject.createFromFileByContent(tempFile, document);

            // Add the image to the PDF
            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                contentStream.drawImage(pdImage, 50, 500, pdImage.getWidth() / 2, pdImage.getHeight() / 2); // Adjust the position and size as needed
            }

            document.save(pdfPath);
            System.out.println("PDF created successfully at " + pdfPath);

            // Clean up the temporary file
            tempFile.delete();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    void navigationHome (ActionEvent event) throws IOException {
        FXMLLoader l = new FXMLLoader(getClass().getResource("/PageInitial.fxml"));//donner notre resources ,donner l'interface a naviguer
        try {
            Parent root = l.load();//recharger notre resources

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Affichage des Progressions");
            stage.show();
        } catch (IOException e) {
            System.out.println(e);
        }


    }

}

































/*
    static {
        try (InputStream is = PlansRegime.class.getResourceAsStream("/logging.properties")) {
            LogManager.getLogManager().readConfiguration(is);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void ajouterRegime(ActionEvent event) {
        RegimeUserService regimeUserService = new RegimeUserService();

        // Formater et analyser la date
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
        String dateDebutText = tfDatDebut.getText();
        String dateFinText = tfDatFin.getText();

        LocalDate dateDebut;
        LocalDate dateFin;
        try {
            // Vérifiez que le texte de la date correspond au format attendu
            if (!dateDebutText.matches("\\d{1,2}/\\d{1,2}/\\d{4}")) {
                throw new DateTimeParseException("Format de date invalide", dateDebutText, 0);
            }

            if (!dateFinText.matches("\\d{1,2}/\\d{1,2}/\\d{4}")) {
                throw new DateTimeParseException("Format de date invalide", dateFinText, 0);
            }

            // Analyser la date
            dateDebut = LocalDate.parse(dateDebutText, formatter);
            dateFin = LocalDate.parse(dateFinText, formatter);
        } catch (DateTimeParseException e) {
            // Afficher une alerte si le format de la date est incorrect
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de format de date");
            alert.setHeaderText(null);
            alert.setContentText("Le format de la date est invalide. Utilisez le format 'd/M/yyyy'.");
            alert.showAndWait();
            return;
        }

        try {
            String nomRegime = tfnomRegime.getText();
            String recepientemail = tfemail.getText();

            // Convertir les dates en chaîne pour l'insertion dans la base de données
            String dateDebutStr = dateDebut.format(DateTimeFormatter.ISO_LOCAL_DATE);
            String dateFinStr = dateFin.format(DateTimeFormatter.ISO_LOCAL_DATE);
            int id = regimeUserService.getIdByEmail(recepientemail);

            // Créer et initialiser l'objet RegimeEntity
            RegimeEntity newRegime = new RegimeEntity();
            newRegime.setNom_regime(nomRegime);
            newRegime.setDate_debut(LocalDate.parse(dateDebutStr));
            newRegime.setDate_fin(LocalDate.parse(dateFinStr));

            // Insérer les données dans la base de données via le service
            regimeUserService.insererDonnees(dateDebutStr, dateFinStr, nomRegime, id);

            // Envoyer l'e-mail avec les données du régime
            sendEmail(recepientemail, newRegime);

            // Générer le PDF
            generatePDF(newRegime);

            // Affichez une alerte de succès
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Succès");
            alert.setHeaderText(null);
            alert.setContentText("Le régime a été ajouté avec succès !");
            alert.showAndWait();
        } catch (NumberFormatException e) {
            // Affichez une alerte si le format des coordonnées est incorrect
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de format de coordonnées");
            alert.setHeaderText(null);
            alert.setContentText("Le format des coordonnées est invalide. Veuillez entrer des nombres valides pour la latitude et la longitude.");
            alert.showAndWait();
        } catch (Exception e) {
            // Affichez une alerte en cas d'erreur
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Une erreur est survenue lors de l'ajout du régime.");
            alert.showAndWait();
            e.printStackTrace();
        }
    }

    private void sendEmail(String recipientEmail, RegimeEntity reg) {
        // Vérifier que reg est initialisé correctement avec les bonnes valeurs
        if (reg.getDate_debut() == null || reg.getDate_fin() == null) {
            System.err.println("Dates not properly initialized in RegimeEntity.");
            new Alert(Alert.AlertType.ERROR, "Dates not properly initialized.").show();
            return;
        }

        Properties prop = new Properties();
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");

        String myEmail = "malek.moalla888@gmail.com"; // Remplacez par votre adresse e-mail Gmail
        String mdp = "skhn azqu wjtn wepf";

        Session session = Session.getInstance(prop, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(myEmail, mdp);
            }
        });

        String emailBody = "Cher Utilisateur,\n" +
                "\n" +
                "Le nom de régime est: " + reg.getNom_regime() + "\n" +
                "La date de début est: " + reg.getDate_debut() + "\n" +
                "La date de fin est : " + reg.getDate_fin() + "\n" +
                "\n" +
                "Nous vous encourageons à suivre ce régime avec détermination et à respecter les règles pour atteindre vos objectifs de santé.\n" +
                "Cordialement,\n" +
                "L'équipe ProFit";

        Message message = prepareMessage(session, myEmail, recipientEmail, emailBody);

        try {
            if (message != null) {
                Transport.send(message);
                new Alert(Alert.AlertType.INFORMATION, "Send Email Successfully").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Please Try Again").show();
            }
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    private Message prepareMessage(Session session, String myEmail, String recipientEmail, String msg) {
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(myEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            message.setSubject("Welcome to ProFit");
            message.setText(msg);
            return message;
        } catch (MessagingException e) {
            e.printStackTrace();
            return null;
        }

    }


    private void generatePDF(RegimeEntity reg) {
        String pdfPath = "regime_" + reg.getNom_regime() + ".pdf";

        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                contentStream.newLineAtOffset(50, 750);
                contentStream.showText("Régime Details:");
                contentStream.newLineAtOffset(0, -15);
                contentStream.setFont(PDType1Font.HELVETICA, 12);
                contentStream.showText("Nom du régime: " + reg.getNom_regime());
                contentStream.newLineAtOffset(0, -15);
                contentStream.showText("Date de début: " + reg.getDate_debut());
                contentStream.newLineAtOffset(0, -15);
                contentStream.showText("Date de fin: " + reg.getDate_fin());
                contentStream.endText();
            }

            document.save(new File(pdfPath));
            System.out.println("PDF created successfully at " + pdfPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


   /* @FXML
    void ajouterRegime(ActionEvent event) {
        RegimeUserService regimeUserService = new RegimeUserService();

        // Formater et analyser la date
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
        String dateDebutText = tfDatDebut.getText();
        String dateFinText = tfDatFin.getText();

        LocalDate dateDebut;
        LocalDate dateFin;
        try {
            // Vérifiez que le texte de la date correspond au format attendu
            if (!dateDebutText.matches("\\d{1,2}/\\d{1,2}/\\d{4}")) {
                throw new DateTimeParseException("Format de date invalide", dateDebutText, 0);
            }

            if (!dateFinText.matches("\\d{1,2}/\\d{1,2}/\\d{4}")) {
                throw new DateTimeParseException("Format de date invalide", dateFinText, 0);
            }

            // Analyser la date
            dateDebut = LocalDate.parse(dateDebutText, formatter);
            dateFin = LocalDate.parse(dateFinText, formatter);
        } catch (DateTimeParseException e) {
            // Afficher une alerte si le format de la date est incorrect
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de format de date");
            alert.setHeaderText(null);
            alert.setContentText("Le format de la date est invalide. Utilisez le format 'd/M/yyyy'.");
            alert.showAndWait();
            return;
        }

        try {
            String nomRegime = tfnomRegime.getText();
            String recepientemail = tfemail.getText();

            // Convertir les dates en chaîne pour l'insertion dans la base de données
            String dateDebutStr = dateDebut.format(DateTimeFormatter.ISO_LOCAL_DATE);
            String dateFinStr = dateFin.format(DateTimeFormatter.ISO_LOCAL_DATE);
            int id = regimeUserService.getIdByEmail(recepientemail);

            // Créer et initialiser l'objet RegimeEntity
            RegimeEntity newRegime = new RegimeEntity();
            newRegime.setNom_regime(nomRegime);
            newRegime.setDate_debut(LocalDate.parse(dateDebutStr));
            newRegime.setDate_fin(LocalDate.parse(dateFinStr));

            // Insérer les données dans la base de données via le service
            regimeUserService.insererDonnees(dateDebutStr, dateFinStr, nomRegime, id);

            // Envoyer l'e-mail avec les données du régime
            sendEmail(recepientemail, newRegime);

            // Générer le PDF
            generatePDF(newRegime);

            // Affichez une alerte de succès
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Succès");
            alert.setHeaderText(null);
            alert.setContentText("Le régime a été ajouté avec succès !");
            alert.showAndWait();
        } catch (NumberFormatException e) {
            // Affichez une alerte si le format des coordonnées est incorrect
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de format de coordonnées");
            alert.setHeaderText(null);
            alert.setContentText("Le format des coordonnées est invalide. Veuillez entrer des nombres valides pour la latitude et la longitude.");
            alert.showAndWait();
        } catch (Exception e) {
            // Affichez une alerte en cas d'erreur
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Une erreur est survenue lors de l'ajout du régime.");
            alert.showAndWait();
            e.printStackTrace();
        }
    }

    private void sendEmail(String recipientEmail, RegimeEntity reg) {
        // Vérifier que reg est initialisé correctement avec les bonnes valeurs
        if (reg.getDate_debut() == null || reg.getDate_fin() == null) {
            System.err.println("Dates not properly initialized in RegimeEntity.");
            new Alert(Alert.AlertType.ERROR, "Dates not properly initialized.").show();
            return;
        }

        Properties prop = new Properties();
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");

        String myEmail = "malek.moalla888@gmail.com"; // Remplacez par votre adresse e-mail Gmail
        String mdp = "skhn azqu wjtn wepf";

        Session session = Session.getInstance(prop, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(myEmail, mdp);
            }
        });

        String emailBody = "Cher Utilisateur,\n" +
                "\n" +
                "Le nom de régime est: " + reg.getNom_regime() + "\n" +
                "La date de début est: " + reg.getDate_debut() + "\n" +
                "La date de fin est : " + reg.getDate_fin() + "\n" +
                "\n" +
                "Nous vous encourageons à suivre ce régime avec détermination et à respecter les règles pour atteindre vos objectifs de santé.\n" +
                "Cordialement,\n" +
                "L'équipe ProFit";

        Message message = prepareMessage(session, myEmail, recipientEmail, emailBody);

        try {
            if (message != null) {
                Transport.send(message);
                new Alert(Alert.AlertType.INFORMATION, "Send Email Successfully").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Please Try Again").show();
            }
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    private Message prepareMessage(Session session, String myEmail, String recipientEmail, String msg) {
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(myEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            message.setSubject("Welcome to ProFit");
            message.setText(msg);
            return message;
        } catch (MessagingException e) {
            e.printStackTrace();
            return null;
        }


   /* @FXML
    void ajouterRegime(ActionEvent event) {
        RegimeUserService regimeUserService = new RegimeUserService();

        // Formater et analyser la date
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
        String dateDebutText = tfDatDebut.getText();
        String dateFinText = tfDatFin.getText();

        LocalDate dateDebut;
        LocalDate dateFin;
        try {
            // Vérifiez que le texte de la date correspond au format attendu
            if (!dateDebutText.matches("\\d{1,2}/\\d{1,2}/\\d{4}")) {
                throw new DateTimeParseException("Format de date invalide", dateDebutText, 0);
            }

            if (!dateFinText.matches("\\d{1,2}/\\d{1,2}/\\d{4}")) {
                throw new DateTimeParseException("Format de date invalide", dateFinText, 0);
            }

            // Analyser la date
            dateDebut = LocalDate.parse(dateDebutText, formatter);
            dateFin = LocalDate.parse(dateFinText, formatter);
        } catch (DateTimeParseException e) {
            // Afficher une alerte si le format de la date est incorrect
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de format de date");
            alert.setHeaderText(null);
            alert.setContentText("Le format de la date est invalide. Utilisez le format 'd/M/yyyy'.");
            alert.showAndWait();
            return;
        }

        try {
            String nomRegime = tfnomRegime.getText();
            String recepientemail = tfemail.getText();

            // Convertir les dates en chaîne pour l'insertion dans la base de données
            String dateDebutStr = dateDebut.format(DateTimeFormatter.ISO_LOCAL_DATE);
            String dateFinStr = dateFin.format(DateTimeFormatter.ISO_LOCAL_DATE);
            int id = regimeUserService.getIdByEmail(recepientemail);

            // Créer et initialiser l'objet RegimeEntity
            RegimeEntity newRegime = new RegimeEntity();
            newRegime.setNom_regime(nomRegime);
            newRegime.setDate_debut(LocalDate.parse(dateDebutStr));
            newRegime.setDate_fin(LocalDate.parse(dateFinStr));

            // Insérer les données dans la base de données via le service
            regimeUserService.insererDonnees(dateDebutStr, dateFinStr, nomRegime, id);

            // Envoyer l'e-mail avec les données du régime
            sendEmail(recepientemail, newRegime);

            // Affichez une alerte de succès
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Succès");
            alert.setHeaderText(null);
            alert.setContentText("Le régime a été ajouté avec succès !");
            alert.showAndWait();
        } catch (NumberFormatException e) {
            // Affichez une alerte si le format des coordonnées est incorrect
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de format de coordonnées");
            alert.setHeaderText(null);
            alert.setContentText("Le format des coordonnées est invalide. Veuillez entrer des nombres valides pour la latitude et la longitude.");
            alert.showAndWait();
        } catch (Exception e) {
            // Affichez une alerte en cas d'erreur
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Une erreur est survenue lors de l'ajout du régime.");
            alert.showAndWait();
            e.printStackTrace();
        }
    }

    private void sendEmail(String recipientEmail, RegimeEntity reg) {
        // Vérifier que reg est initialisé correctement avec les bonnes valeurs
        if (reg.getDate_debut() == null || reg.getDate_fin() == null) {
            System.err.println("Dates not properly initialized in RegimeEntity.");
            new Alert(Alert.AlertType.ERROR, "Dates not properly initialized.").show();
            return;
        }

        Properties prop = new Properties();
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");

        String myEmail = "malek.moalla888@gmail.com"; // Remplacez par votre adresse e-mail Gmail
        String mdp = "skhn azqu wjtn wepf";

        Session session = Session.getInstance(prop, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(myEmail, mdp);
            }
        });

        String emailBody = "Cher Utilisateur,\n" +
                "\n" +
                "Le nom de régime est: " + reg.getNom_regime() + "\n" +
                "La date de début est: " + reg.getDate_debut() + "\n" +
                "La date de fin est : " + reg.getDate_fin() + "\n" +
                "\n" +
                "Nous vous encourageons à suivre ce régime avec détermination et à respecter les règles pour atteindre vos objectifs de santé.\n" +
                "Cordialement,\n" +
                "L'équipe ProFit";

        Message message = prepareMessage(session, myEmail, recipientEmail, emailBody);

        try {
            if (message != null) {
                Transport.send(message);
                new Alert(Alert.AlertType.INFORMATION, "Send Email Successfully").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Please Try Again").show();
            }
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    private Message prepareMessage(Session session, String myEmail, String recipientEmail, String msg) {
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(myEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            message.setSubject("Welcome to ProFit");
            message.setText(msg);
            return message;
        } catch (MessagingException e) {
            e.printStackTrace();
            return null;
        }
    }*/


//RegimeEntity reg=new RegimeEntity();
//    UserEntity user=new UserEntity();
//    private void sendEmail(String recepientemail) {
//
//
//        Properties prop = new Properties();
//        prop.put("mail.smtp.auth", "true");
//        prop.put("mail.smtp.starttls.enable", "true");
//        prop.put("mail.smtp.host", "smtp.gmail.com");
//       prop.put("mail.smtp.port", "587");
//
//        String myEmail = "malek.moalla888@gmail.com"; // Remplacez par votre adresse e-mail Gmail
//        String mdp = "skhn azqu wjtn wepf";
//
//        Session session = Session.getInstance(prop, new Authenticator() {
//            @Override
//            protected PasswordAuthentication getPasswordAuthentication() {
//                return new PasswordAuthentication(myEmail, mdp);
//
//            }
//        });
//        String emailBody = "Dear User,\n" +
//                "\n" +
//                "Your new Login is: " + reg.getDate_fin() + "\n" +
//                "\n" +
//                "Your new Password is: " + reg.getDate_fin() + "\n" +
//                "\n" +
//                "Please keep this password secure and consider changing it after logging in.\n" +
//                "\n" +
//                "Best regards,\n" +
//                "ProFit Team";
//        Message message = prepareMessage(session, myEmail, user.getEmail(), emailBody);
//        if (message != null) {
//            new Alert(Alert.AlertType.INFORMATION, "Send Email Successfully").show();
//        } else {
//            new Alert(Alert.AlertType.ERROR, "Please Try Again").show();
//        }
//        try {
//            Transport.send(message);
//        } catch (MessagingException e) {
//            throw new RuntimeException(e);
//        }
//    }
//    private Message prepareMessage(Session session, String myEmail, String recipientEmail, String msg) {
//        try {
//            Message message = new MimeMessage(session);
//            message.setFrom(new InternetAddress(myEmail));
//            message.setRecipients(Message.RecipientType.TO,
//                    new InternetAddress[]
//                            {
//                                    new InternetAddress(recipientEmail)
//                            });
//            message.setSubject("Welcome to ProFit");
//            message.setText(msg);
//            return message;
//
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//

      /*  @FXML
        void navigationHome (ActionEvent event) throws IOException {
            FXMLLoader l = new FXMLLoader(getClass().getResource("/PageInitial.fxml"));//donner notre resources ,donner l'interface a naviguer
            try {
                Parent root = l.load();//recharger notre resources

                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.setTitle("Affichage des Progressions");
                stage.show();
            } catch (IOException e) {
                System.out.println(e);
            }


        }
    }/*













   /* @Override
    public void start(Stage primaryStage) {
        Text text = new Text("Hello, JavaFX!");
        StackPane root = new StackPane();
        root.getChildren().add(text);

        Scene scene = new Scene(root, 300, 200);

        primaryStage.setTitle("JavaFX to PDF Example");
        primaryStage.setScene(scene);
        primaryStage.show();

        // Capture the scene as an image
        WritableImage snapshot = scene.snapshot(null);
        File imageFile = new File("interface.png");

        // Save the image to a file
        BufferedImage bufferedImage = SwingFXUtils.fromFXImage(snapshot, null);
        try {
            ImageIO.write(bufferedImage, "png", imageFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Create PDF from the captured image
        String pdfPath = "interface.pdf";
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            PDImageXObject pdImage = PDImageXObject.createFromFile(imageFile.getAbsolutePath(), document);
            PDPageContentStream contentStream = new PDPageContentStream(document, page);

            // Adjust the image to fit the page
            float scale = Math.min(page.getMediaBox().getWidth() / pdImage.getWidth(),
                    page.getMediaBox().getHeight() / pdImage.getHeight());

            float imageWidth = pdImage.getWidth() * scale;
            float imageHeight = pdImage.getHeight() * scale;

            float x = (page.getMediaBox().getWidth() - imageWidth) / 2;
            float y = (page.getMediaBox().getHeight() - imageHeight) / 2;

            contentStream.drawImage(pdImage, x, y, imageWidth, imageHeight);
            contentStream.close();

            document.save(new File(pdfPath));
            System.out.println("PDF created successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }






    }
*/




//    private void sendFirstMail(RegimeEntity reg) {
//        // Propriétés pour la configuration de la session SMTP
//        UserEntity userEntity=new UserEntity();
//        Properties prop = new Properties();
//        prop.put("mail.smtp.auth", "true");
//        prop.put("mail.smtp.starttls.enable", "true");
//        prop.put("mail.smtp.host", "smtp.gmail.com");
//        prop.put("mail.smtp.port", "587");
//
//        // Informations d'identification pour l'envoi de l'e-mail
//        String myEmail = "your-email@gmail.com"; // Remplacez par votre adresse e-mail Gmail
//        String mdp = "your-password"; // Remplacez par votre mot de passe Gmail ou un jeton d'accès OAuth2
//
//        // Création de la session SMTP avec authentification
//        Session session = Session.getInstance(prop, new Authenticator() {
//            @Override
//            protected PasswordAuthentication getPasswordAuthentication() {
//                return new PasswordAuthentication(myEmail, mdp);
//            }
//        });
//
//        // Corps de l'e-mail à envoyer
//        String emailBody = "Dear User,\n\n" +
//                "Le nom de régime est: " + reg.getNom_regime() + "\n" +
//                "La date de début est: " + reg.getDate_debut() + "\n" +
//                "La date de fin est : " + reg.getDate_fin() + "\n\n" +
//                "Please keep this password secure and consider changing it after logging in.\n\n" +
//                "Best regards,\n" +
//                "ProFit Team";
//
//        // Préparation du message à envoyer
//        Message message = prepareMessage(session, myEmail, userEntity.getEmail(), emailBody);
//
//        try {
//            // Vérification si le message est null avant l'envoi
//            if (message != null) {
//                Transport.send(message); // Envoi du message
//                new Alert(Alert.AlertType.INFORMATION, "Send Email Successfully").show();
//            } else {
//                new Alert(Alert.AlertType.ERROR, "Please Try Again").show();
//            }
//        } catch (MessagingException e) {
//            // Gestion des exceptions lors de l'envoi de l'e-mail
//            e.printStackTrace();
//            new Alert(Alert.AlertType.ERROR, "Failed to send email").show();
//        }
//    }
//
//    // Méthode pour préparer le message e-mail avec ses détails
//    private Message prepareMessage(Session session, String myEmail, String recipientEmail, String msg) {
//        try {
//            Message message = new MimeMessage(session);
//            message.setFrom(new InternetAddress(myEmail)); // Définir l'expéditeur du message
//            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail)); // Définir le destinataire
//            message.setSubject("Welcome to ProFit"); // Définir le sujet du message
//            message.setText(msg); // Définir le contenu du message
//            return message; // Retourner le message préparé
//        } catch (MessagingException e) {
//            // Gérer les exceptions liées à la création du message
//            e.printStackTrace();
//            return null; // Retourner null en cas d'erreur
//        }
//    }
//

