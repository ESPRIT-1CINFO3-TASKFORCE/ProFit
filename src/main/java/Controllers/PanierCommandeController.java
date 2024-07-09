package Controllers;

import Entites.PanierEntity;
import Entites.ProduitEntity;
import Services.CommandeService;
import Services.PanierService;
import Services.ProductSelectionListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.awt.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

public class PanierCommandeController{
    @FXML
    private Button btnValiderCommande;

    @FXML
    private Button btnViderPanier;

    @FXML
    private VBox productsContainer;

    @FXML
    private Label remiseid;

    @FXML
    private HBox soldeBand;

    @FXML
    private Label soustotaleid;

    @FXML
    private Label totaleid;

    @FXML
    private VBox vboxpanier;
    private PanierService panierService;
    private CommandeService commandeService;
    private int panierId; // L'ID du panier actuel, initialisez-le en fonction de votre application
    private StoreController store ;
    public PanierCommandeController() throws FileNotFoundException {
        panierService = new PanierService();
        commandeService = new CommandeService();
    }
    PanierEntity panier = new PanierEntity();
    @FXML
    public void initialize() throws SQLException {
        System.out.println("Displaying products for panierId: " + panierId); // Log avant d'afficher les produits
        //afficherProduits();
    }
    @FXML
    void chargerInterfaceCommande(ActionEvent event) {
        try {
            PanierEntity panier = panierService.getPanier(panierId);
            if (panier != null) {
                // Supposez que le client a un ID 1, vous pouvez ajuster cette ligne en fonction de votre application
                commandeService.creerCommande(1, panier);
                showAlert("Success", "Votre commande est gérer avec succès!");

                panierService.viderPanier(panierId);
                afficherProduits();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        /*try {
            panierService.viderPanier(panierId);
            soustotaleid.setText("0.0");
            remiseid.setText("0.0");
            totaleid.setText("0.0");
            productsContainer.getChildren().clear();
        } catch (SQLException e) {
            e.printStackTrace();
        }*/
    }

    @FXML
    void viderPanier(ActionEvent event) {
        try {
            panierService.viderPanier(panierId);
            showAlert("Success", "Panier est vider avec succès!");
            afficherProduits();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

   /* @FXML
    void validerCommande(ActionEvent event) {
        try {
            //PanierEntity panier = panierService.getPanier(panierId);
            if (panier != null) {
                // Supposez que le client a un ID 1, vous pouvez ajuster cette ligne en fonction de votre application
                commandeService.creerCommande(1, panier);
                panierService.viderPanier(panierId);
                afficherProduits();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }*/
    public void setPanierId(int panierId) {
        this.panierId = panierId;
        System.out.println("Panier ID received in PanierCommandeController: " + panierId);
        afficherProduits();
    }
    private void afficherProduits() {
        //System.out.println("Displaying products for panierId: " + panierId); // Log before displaying products

        try {
            PanierEntity pan = panierService.getPanier(panierId);
            System.out.println(panierId);
            if (pan != null) {
                productsContainer.getChildren().clear();
                List<ProduitEntity> produits = pan.getProduits();
                System.out.println(pan.getProduits());
                for (ProduitEntity produit : produits) {
                    HBox productBox = new HBox(80);
                    productBox.getStyleClass().add("product-box");
                    productsContainer.setStyle("-fx-padding:15px");

                    ImageView productImage = new ImageView(new Image(produit.getImage()));
                    productImage.setFitHeight(50);
                    productImage.setFitWidth(50);

                    Rectangle clip = new Rectangle(50, 50); // Set the dimensions as needed
                    clip.getWidth();// Adjust the corner radius
                    clip.getHeight();
                    productImage.setClip(clip);
                    productImage.setEffect(new DropShadow(10, Color.BLACK)); // Adjust the shadow parameters

                    Label labelProduit = new Label(produit.getNom_p() + " - " + produit.getPrix() + " TND");
                    labelProduit.setMinHeight(20);
                    labelProduit.setStyle("-fx-position:absolute ;-fx-top:12px;");
                    labelProduit.setAlignment(Pos.BOTTOM_CENTER); // Center-align the label

                    ImageView deleteIcon = new ImageView(new Image(getClass().getResourceAsStream("/SOURCE/des-ordures.png")));;
                    deleteIcon.setFitHeight(20);
                    deleteIcon.setFitWidth(20);

                    Button btnSupprimer = new Button("", deleteIcon);
                    btnSupprimer.setOnAction(event -> {
                        try {
                            panierService.supprimerProduitDuPanier(pan.getId_pan(), produit);
                            showAlert("Success", " Ce Produit est supprimer de panier avec succès!");

                            afficherProduits();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    });

                    Region spacer = new Region();
                    HBox.setHgrow(spacer, Priority.ALWAYS);

                    HBox actionBox = new HBox(30);
                    actionBox.getChildren().addAll(btnSupprimer);

                    HBox.setHgrow(productImage, Priority.NEVER);
                    HBox.setHgrow(actionBox, Priority.NEVER);

                    productBox.getChildren().addAll(productImage, labelProduit, spacer, actionBox);

                    productsContainer.getChildren().add(productBox);
                }
                //soustotaleid.setText(String.valueOf(panier.getTotal()));
               //remiseid.setText("0.0"); // Vous pouvez ajouter la logique pour calculer la remise
                //totaleid.setText(String.valueOf(panier.getTotal())); // Vous pouvez ajuster cette ligne si vous avez une remise
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'affichage du produit dans le panier : " + e.getMessage());
        }
    }
    @FXML
    void BackToStore(ActionEvent event) {
        FXMLLoader L = new FXMLLoader(getClass().getResource("/Store.fxml"));
        try {
            Parent root = L.load();
            productsContainer.getScene().setRoot(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private void showAlert(String title, String message) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
