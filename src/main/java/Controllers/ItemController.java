package Controllers;

import Entites.ProduitEntity;
import Services.ProductSelectionListener;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.ByteArrayInputStream;
import java.io.IOException;

public class ItemController {
    @FXML
    private ImageView img;

    @FXML
    private Label nameLabel;

    @FXML
    private Label priceLable;
    private ProduitEntity produit;
    private StoreController store;
    private Image image;
    private final String CURRENCY = "TND";
    private ProductSelectionListener selectionListener;


    public void setData(ProduitEntity produit, ProductSelectionListener listener) {
        this.produit = produit;
        this.selectionListener = listener;
        nameLabel.setText(produit.getNom_p());
        priceLable.setText(produit.getPrix() +CURRENCY);
        try {
            String imageUrl = produit.getImage();
            if (imageUrl != null && !imageUrl.isEmpty()) {
                Image image = new Image(imageUrl);
                img.setImage(image);
            } else {
                // Gestion du cas où l'URL de l'image est null ou vide
                img.setImage(null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            // Gestion d'une image par défaut en cas d'erreur
            img.setImage(null);
        }
    }
  @FXML
  private void click(MouseEvent mouseEvent) {
      if (selectionListener != null) {
          selectionListener.onProductSelected(produit);
      }
  }
}
