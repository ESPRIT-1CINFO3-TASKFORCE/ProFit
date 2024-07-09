package Controllers;

import Entites.ProduitEntity;
import Services.ProduitService;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import java.io.*;
import java.sql.SQLException;

public class UpdateController {

    @FXML
    private ImageView tfimage;

    @FXML
    private TextField tfnomp;

    @FXML
    private TextField tfprixp;

    @FXML
    private TextField tfqtp;
    private File selectedImageFile;
    ProduitService ps = new ProduitService();
    private ProduitEntity produit;
    private ProduitEntity produitExistante;
    @FXML
    void importimg(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif"));
        selectedImageFile = fileChooser.showOpenDialog(null);
        if (selectedImageFile != null) {
            Image image = new Image(selectedImageFile.toURI().toString());
            tfimage.setImage(image);
        }
    }
    private void clearForm() {
        tfnomp.clear();
        tfprixp.clear();
        tfqtp.clear();
        tfimage.setImage(null);
        selectedImageFile = null;
    }
    private void showAlert(String title, String message) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    public void setProduit(ProduitEntity produit) {
        this.produitExistante = produit;
        tfnomp.setText(produit.getNom_p());
        tfprixp.setText(String.valueOf(produit.getPrix()));
        tfqtp.setText(String.valueOf(produit.getQnt()));
        tfimage.setImage(new Image(produit.getImage()));
    }

    @FXML
    public void modifier(ActionEvent event) {
        try {
            // Capture des valeurs du formulaire
            String nomProduit = tfnomp.getText();
            int prixProduit = Integer.parseInt(tfprixp.getText());
            int quantiteProduit = Integer.parseInt(tfqtp.getText());
            String imageUrl = null;

            if (selectedImageFile != null) {
                // Définir le répertoire de destination pour les images
                File destDir = new File("images"); // Vous pouvez changer le répertoire selon vos besoins
                if (!destDir.exists()) {
                    destDir.mkdir();
                }

                // Définir le fichier de destination
                File destFile = new File(destDir, selectedImageFile.getName());

                // Copier le fichier image dans le répertoire de destination
                try (InputStream is = new FileInputStream(selectedImageFile);
                     OutputStream os = new FileOutputStream(destFile)) {
                    byte[] buffer = new byte[1024];
                    int length;
                    while ((length = is.read(buffer)) > 0) {
                        os.write(buffer, 0, length);
                    }
                } catch (IOException e) {
                    throw new RuntimeException("Failed to save the image", e);
                }

                // Générer l'URL de l'image enregistrée
                imageUrl = destFile.toURI().toString();
            } else {
                // Si aucune nouvelle image n'est sélectionnée, conserver l'ancienne URL de l'image
                imageUrl = produitExistante.getImage();
            }

            // Créer un objet ProduitEntity avec les nouvelles valeurs
            ProduitEntity produit = new ProduitEntity(nomProduit, prixProduit, quantiteProduit, imageUrl);

            // Appeler la méthode update du service en passant l'objet et l'ID du produit à mettre à jour
            int produitId = produitExistante.getId_pro();
            ps.update(produit, produitId);

            // Réinitialiser le formulaire
            clearForm();
            showAlert("Success", "Produit modifié avec succès!");

        } catch (NumberFormatException e) {
            showAlert("Error", "Saisie invalide pour le prix ou la quantité.");
        } catch (SQLException e) {
            showAlert("Error", "Une erreur de base de données s'est produite.");
        }
    }

    }



