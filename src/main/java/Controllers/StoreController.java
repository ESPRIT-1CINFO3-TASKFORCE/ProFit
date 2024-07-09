package Controllers;

import Entites.PanierEntity;
import Entites.ProduitEntity;
import Services.PanierService;
import Services.ProductSelectionListener;
import Services.ProduitService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class StoreController implements Initializable, ProductSelectionListener {
    @FXML
    private ImageView ProduitImg;

    @FXML
    private Label ProduitNameLable;

    @FXML
    private Label ProduitPriceLabel;


    @FXML
    private VBox chosenProduitCard;

    @FXML
    private GridPane grid;

    @FXML
    private ScrollPane scroll;
    @FXML
    private TextField qnt;

    private List<ProduitEntity> produits = new ArrayList<>();
    //private  Image image;
    private static final String CURRENCY = "TND";
    private ProduitService produitService = new ProduitService();
    private static PanierService panierService = new PanierService();
    private ProductSelectionListener selectionListener;
    private  ProduitEntity produit ;
    private int panierId;
    private ProduitEntity selectedProduit;
    @FXML
    private TextField nomp;

    public int getIdpanier() {
        return panierId;
    }

    public void setIdpanier(int panierId) {
        this.panierId = panierId;
    }

    private List<ProduitEntity> getData() {
        try {
            return produitService.readAll1();
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    private void setChosenFruit(ProduitEntity produit) {
        this.selectedProduit = produit;
        ProduitNameLable.setText(produit.getNom_p());
        ProduitPriceLabel.setText(produit.getPrix() +CURRENCY  );
        try {
            String imageUrl = produit.getImage();
            if (imageUrl != null && !imageUrl.isEmpty()) {
                Image image = new Image(imageUrl);
                ProduitImg.setImage(image);
            } else {
                // Gestion du cas où l'URL de l'image est null ou vide
                ProduitImg.setImage(null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            // Gestion d'une image par défaut en cas d'erreur
            ProduitImg.setImage(null);
        }
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        produits.addAll(getData());
        if (produits.size() > 0) {
            setChosenFruit(produits.get(0));
        }
        int column = 0;
        int row = 1;
        try {
            for (int i = 0; i < produits.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/Item.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                ItemController itemController = fxmlLoader.getController();
                itemController.setData(produits.get(i), this); // Pass this as ProductSelectionListener

                if (column == 3) {
                    column = 0;
                    row++;
                }

                grid.add(anchorPane, column++, row); // (child, column, row)
                // Set grid width
                grid.setMinWidth(Region.USE_COMPUTED_SIZE);
                grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                grid.setMaxWidth(Region.USE_PREF_SIZE);

                // Set grid height
                grid.setMinHeight(Region.USE_COMPUTED_SIZE);
                grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                grid.setMaxHeight(Region.USE_PREF_SIZE);

                GridPane.setMargin(anchorPane, new Insets(10));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int  onProductSelected(ProduitEntity produit) {
        setChosenFruit(produit);
        return panierId;
    }
    @FXML
    void BackHome(ActionEvent event) {
        FXMLLoader L = new FXMLLoader(getClass().getResource("/Side_bar.fxml"));
        try {
            Parent root = L.load();
            grid.getScene().setRoot(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    void Panier(ActionEvent event) {
        System.out.println("Navigating to Panier with panierId: " + panierId); // Log avant de naviguer
        FXMLLoader L = new FXMLLoader(getClass().getResource("/PanierCommande.fxml"));
        try {
            Parent root = L.load();
            PanierCommandeController controller = L.getController();
            controller.setPanierId(panierId); // Passer l'id du panier au contrôleur suivant
            System.out.println("Panier ID passed to PanierCommandeController: " + panierId);
            grid.getScene().setRoot(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void ajouterPanier(ActionEvent event) {
        if (selectedProduit != null) {
            try {
                if (qnt != null && !qnt.getText().isEmpty()) {
                    int qntP = Integer.parseInt(qnt.getText());
                    int panierId = panierService.ajouterProduitAuPanier(selectedProduit, qntP);
                    setIdpanier(panierId);
                    qnt.clear();
                    showAlert("Success", "Produit ajouter au panier avec succès!");
                }else {
                    showAlert("Error", "Saisie invalide pour la quantité.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Aucun produit sélectionné pour ajouter au panier.");
        }
    }

    public void setProductSelectionListener(ProductSelectionListener listener) {
        this.selectionListener = listener;
    }

    @FXML
    void chercher(ActionEvent event) {
        try {
            String nomProduit = nomp.getText();
            ProduitEntity pro = produitService.findbyId(nomProduit) ;
            if (pro != null) {
                showAlert("Information", "Produit Disponible!"
                        + "\n Voila la quantité disponible:"+" "+ pro.getQnt()
                        + "\n Le prix de ce produit est : " +pro.getPrix() );
            } else {
                showAlert("Information", "Produit n'est pas Disponible!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
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
