package Services;

import Controllers.PanierCommandeController;
import Entites.LignePanierEntity;
import Entites.PanierEntity;
import Entites.ProduitEntity;
import Utils.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PanierService  {
    private Connection conn;

    {
        try {
            conn = DataSource.getInstance().getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Statement ste;


    public PanierService(){
        try {
            ste=conn.createStatement();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    public int ajouterProduitAuPanier(ProduitEntity produit, int quantite) throws SQLException {
        int id_pan = -1;

        // Vérifiez si un panier existe déjà
        PreparedStatement prePanier = conn.prepareStatement("SELECT id_pan FROM `panier` LIMIT 1;");
        ResultSet rsPanier = prePanier.executeQuery();

        if (rsPanier.next()) {
            id_pan = rsPanier.getInt("id_pan");
        } else {
            // Si le panier n'existe pas, créez un nouveau panier
            PreparedStatement pre = conn.prepareStatement("INSERT INTO `panier`(`total`) VALUES (?);", Statement.RETURN_GENERATED_KEYS);
            pre.setDouble(1, 0); // Initialiser le total à 0
            pre.executeUpdate();

            // Obtenez l'ID généré du nouveau panier
            ResultSet rsGeneratedKeys = pre.getGeneratedKeys();
            if (rsGeneratedKeys.next()) {
                id_pan = rsGeneratedKeys.getInt(1);
            } else {
                throw new SQLException("Échec de la création du panier, aucun ID généré.");
            }
        }

        // Ajoutez le produit au panier
        PreparedStatement preProduit = conn.prepareStatement("INSERT INTO `lingepanier`(`id_pan`, `id_pro`, `quantite`) VALUES (?, ?, ?);");
        preProduit.setInt(1, id_pan);
        preProduit.setInt(2, produit.getId_pro());
        preProduit.setInt(3, quantite);
        preProduit.executeUpdate();

        // Mettez à jour le total du panier
        updateTotalPanier(id_pan);

        return id_pan; // Retourne l'ID du panier
    }

    public void supprimerProduitDuPanier(int id_pan, ProduitEntity produit) throws SQLException {
        PreparedStatement pre = conn.prepareStatement("DELETE FROM `lingepanier` WHERE `id_pan` = ? AND `id_pro` = ?;");
        pre.setInt(1, id_pan);
        pre.setInt(2, produit.getId_pro());
        pre.executeUpdate();

        updateTotalPanier(id_pan);
    }

    public void viderPanier(int id_pan) throws SQLException {
        PreparedStatement pre = conn.prepareStatement("DELETE FROM `lingepanier` WHERE `id_pan` = ?;");
        pre.setInt(1, id_pan);
        pre.executeUpdate();

        updateTotalPanier(id_pan);
    }
    public PanierEntity getPanier(int id_pan) throws SQLException {
        PreparedStatement pre = conn.prepareStatement("SELECT * FROM `panier` WHERE id_pan = ?;");
        pre.setInt(1, id_pan);
        ResultSet rs = pre.executeQuery();

        if (rs.next()) {
            PanierEntity panier = new PanierEntity(rs.getInt("id_pan"));
            panier.setTotal(rs.getDouble("total"));

            List<ProduitEntity> produits = new ArrayList<>();

            PreparedStatement preProduits = conn.prepareStatement(
                    "SELECT lp.id_lp, lp.id_pro, lp.quantite,p.id_pro, p.nom_p, p.prix, p.image " +
                            "FROM `lingepanier` lp " +
                            "JOIN `produit` p ON lp.id_pro = p.id_pro " +
                            "WHERE lp.id_pan = ?;"
            );
            preProduits.setInt(1, id_pan);
            ResultSet rsProduits = preProduits.executeQuery();

            while (rsProduits.next()) {
                ProduitEntity produit = new ProduitEntity(
                        rsProduits.getInt("id_pro"),
                        rsProduits.getString("nom_p"),
                        rsProduits.getInt("prix"),
                        rsProduits.getString("image")
                );
                produits.add(produit);

                LignePanierEntity lignePanier = new LignePanierEntity(
                        rsProduits.getInt("id_lp"),
                        id_pan,
                        rsProduits.getInt("id_pro"),
                        rsProduits.getInt("quantite")
                );
                // Assuming you want to do something with lignePanier here.
                // For example, you could add them to another list if needed.
            }
            panier.setProduits(produits);
            return panier;
        }
        return null;
    }
    /*public  PanierEntity getPanier(int pan ) throws SQLException {
        PreparedStatement pre = con.prepareStatement("SELECT * FROM `panier` WHERE id_pan = ?;");
        pre.setInt(1,pan);
        ResultSet rs = pre.executeQuery();

        if (rs.next()) {
            PanierEntity panier = new PanierEntity(rs.getInt("id_pan"));
            panier.setTotal(rs.getDouble("total"));

            List<ProduitEntity> produits = new ArrayList<>();
            PreparedStatement preProduits = con.prepareStatement(
                    "SELECT lp.id_pro, lp.quantite, p.nom_p, p.prix,p.image " +
                            "FROM `lingepanier` lp " +
                            "JOIN `produit` p ON lp.id_pro = p.id_pro " +
                            "WHERE lp.id_pan = ?;"
            );
            preProduits.setInt(1, pan);
            ResultSet rsProduits = preProduits.executeQuery();
            while (rsProduits.next()) {
                produits.add(new ProduitEntity(
                        rsProduits.getString("nom_p"),
                        rsProduits.getInt("prix"),
                        rsProduits.getString("image")
                ));
            }
            panier.setProduits(produits);
            return panier;
        }
        return null;
    }*/



    public void updateTotalPanier(int id_pan) throws SQLException {
        PreparedStatement pre = conn.prepareStatement("SELECT SUM(p.prix * lp.quantite) AS total FROM `lingepanier` lp JOIN `produit` p ON lp.id_pro = p.id_pro WHERE lp.id_pan = ?;");
        pre.setInt(1, id_pan);
        ResultSet rs = pre.executeQuery();

        if (rs.next()) {
            double total = rs.getDouble("total");
            PreparedStatement preUpdate = conn.prepareStatement("UPDATE `panier` SET `total` = ? WHERE `id_pan` = ?;");
            preUpdate.setDouble(1, total);
            preUpdate.setInt(2, id_pan);
            preUpdate.executeUpdate();
        }
    }


}
