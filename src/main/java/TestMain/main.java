package TestMain;
import Entites.*;
import Services.*;
import Utils.DataSource;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
public class main {
    public static void main(String[] args) throws FileNotFoundException, SQLException {

        System.out.println("hello!");

        ProduitService pro = new ProduitService();
        PanierService pan = new PanierService();
        CommandeService com = new CommandeService();
        //String imagePath = "C:\\Users\\DJAPPA\\Desktop\\ProFit\\src\\main\\java\\TestMain\\ballon de gym.png";
        //InputStream imagedata = new FileInputStream(imagePath);
        /*ProduitEntity p1 = new ProduitEntity(5,"test1",200,3,imagedata);
        ProduitEntity p2 = new ProduitEntity(6,"test2",500,3,imagedata);*/
        /*
        ProduitEntity p2 = new ProduitEntity(0);
        ProduitEntity p3 = new ProduitEntity();*/

        //*****************************************TESTER CRUD CLASSE PRODUIT ***************************

        //methode afficher tous les progression de le BD

      /* try {
            pro.readAll().forEach(e-> System.out.println(e));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }*/
       //methode ajouter un produit au BD
/*
        try {
            pro.ajouter(p1);
            pro.ajouter(p2);
            System.out.println("produit ajouteé");
        }catch (SQLException e){
            System.out.println(e);
        }*/
        /*//methode supprimer up produit en BD
        try {
            pro.supprimer(p2);
            System.out.println("produit supprimer ");
        }catch (SQLException e){
            System.out.println(e);
        }
        try {
            ProduitEntity foundProduit = pro.findbyId(1);
            if (foundProduit != null) {
                System.out.println("Produit trouvé : " + foundProduit.getNom_p());
            } else {
                System.out.println("Produit non trouvé");
            }
        }catch (SQLException e){
            System.out.println(e);
        }

        //methode modifier up produit
        try {
           //p3.setPrix(22);
            pro.update(p1,1);
        }catch (SQLException e){
            System.out.println(e);
        }*/
        try {
            //*****************************************TESTER CRUD CLASSE Panier ***************************


            ProduitEntity p1 = new ProduitEntity(26); // Produit avec ID 5
            ProduitEntity p2 = new ProduitEntity(27); // Produit avec ID 2

// Ajouter des produits au panier

// Afficher le panier après ajout des produits
                //PanierEntity panier = new PanierEntity();
                //System.out.println("Panier après ajout de produits : " +panier);
                //int idpan=pan.ajouterProduitAuPanier(p1, 1); // Ajouter 1 unité de produit1
                //int idpan=pan.ajouterProduitAuPanier(p2, 2); // Ajouter 2 unités de produit2
                // Debugging: afficher l'ID du panier après l'ajout des produits
                //System.out.println("ID du panier après ajout des produits : " +panier.getId_pan());
                // Récupération du panier après ajout des produits
                //PanierEntity panierApresAjout = pan.getPanier(idpan);
                //System.out.println("Panier après ajout de produits : " + panierApresAjout);

            // Supprimer le panier
            //pan.viderPanier(idpan);
            //pan.supprimerProduitDuPanier(idpan,p1);
            //PanierEntity panierApresAjout1 = pan.getPanier(idpan);
            //System.out.println("Panier supprimé" + panierApresAjout1);
//**************** Rechehrche Produit *******************//
           /*
            try {
                ProduitEntity foundProduit = pro.findbyId("Gants");
                if (foundProduit != null) {
                    System.out.println("Produit trouvé : " + foundProduit.getNom_p());
                } else {
                    System.out.println("Produit non trouvé");
                }
            }catch (SQLException e){
                System.out.println(e);
            }*/


/*
            // Mettre à jour la quantité d'un produit dans le panier
            pan.updateQuantiteProduit(1, 1, 5); // Changer la quantité de produit1 à 5
            System.out.println("Quantité de produit 1 mise à jour");

            // Afficher le panier après mise à jour de la quantité
            PanierEntity panierApresMiseAJour = pan.getPanier(1);
            System.out.println("Panier après mise à jour de la quantité : " + panierApresMiseAJour);

            // Supprimer un produit du panier
            pan.supprimerProduitDuPanier(1, 2); // Supprimer produit2 du panier
            System.out.println("Produit 2 supprimé du panier");

            // Afficher le panier après suppression du produit
            PanierEntity panierApresSuppression = pan.getPanier(1);
            System.out.println("Panier après suppression du produit : " + panierApresSuppression);

            // Mettre à jour le panier (par exemple, changer le total)
            panierApresSuppression.setTotal(50); // Juste un exemple, normalement le total est calculé automatiquement
            pan.updatePanier(panierApresSuppression);
            System.out.println("Panier mis à jour");

            // Supprimer le panier
            pan.supprimerPanier(1);
            System.out.println("Panier supprimé");*/

            //*****************************************TESTER CRUD CLASSE Panier ***************************
            // créez une commande à partir du panier
            //com.creerCommande(1, pan.getPanier(panierApresAjout.getId_pan()));
            // Afficher toutes les commandes
            List<CommandeEntity> commandes = com.afficherToutesLesCommandes();
            for (CommandeEntity commande : commandes) {
                System.out.println(commande);
            }
            //com.changerEtatCommande(28, "ok");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        int idCommande =76; // Remplacez ceci par l'ID de la commande que vous voulez afficher
        CommandeDetailsEntity commandeDetails = com.getCommandeDetails(idCommande);
        System.out.println(commandeDetails);
        if (commandeDetails != null) {
            System.out.println("Nom du client: " + commandeDetails.getNomClient());
            System.out.println("Email du client: " + commandeDetails.getEmailClient());
            System.out.println("Numéro de téléphone du client: " + commandeDetails.getNumTelClient());
            System.out.println("Total de la commande: " + commandeDetails.getTotalCommande());
            System.out.println("État de la commande: " + commandeDetails.getEtatCommande());
            System.out.println("Produits de la commande:");
            for (ProduitDetailsEntity produit : commandeDetails.getProduits()) {
                System.out.println(" - Nom: " + produit.getNomProduit());
                System.out.println("   Quantité: " + produit.getQuantiteProduit());
                System.out.println("   Prix: " + produit.getPrixProduit());
                System.out.println("   Image: " + produit.getImagProduit());
            }
        } else {
            System.out.println("Aucun détail trouvé pour la commande avec ID: " + idCommande);
        }





    }
}
