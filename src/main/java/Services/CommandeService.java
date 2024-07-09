package Services;

import Entites.*;
import Utils.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class CommandeService {
    private Connection conn;

    {
        try {
            conn = DataSource.getInstance().getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Statement ste;

    public CommandeService() {
        try {
            ste = conn.createStatement();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public  void creerCommande(int client, PanierEntity panier) throws SQLException {
        Date dateCommande = new Date(System.currentTimeMillis());
        double total = panier.getTotal();
        System.out.println(panier.getTotal());
        String etat = "En cours";

        // Insérer la commande dans la table commande
        PreparedStatement pre = conn.prepareStatement("INSERT INTO `commande`(`client_id`, `etat`, `date_c`, `total`) VALUES (?, ?, ?, ?);", Statement.RETURN_GENERATED_KEYS);
        pre.setInt(1, client);
        pre.setString(2, etat);
        pre.setDate(3, new java.sql.Date(dateCommande.getTime()));
        pre.setDouble(4, total);

        pre.executeUpdate();

        // Obtenir l'ID généré pour la commande
        ResultSet rs = pre.getGeneratedKeys();
        int id_c;
        if (rs.next()) {
            id_c = rs.getInt(1);
        } else {
            throw new SQLException("Échec de la création de la commande, aucun ID obtenu.");
        }

        // Insérer les lignes de commande à partir du panier
        for (ProduitEntity produit : panier.getProduits()) {
            LigneCommandeEntity ligneCommande = new LigneCommandeEntity(
                    0,  // l'id de ligne de commande est auto-généré
                    (int) id_c,
                    produit.getId_pro(),
                    produit.getQnt(),  // Assurez-vous que `ProduitEntity` a un champ `quantite`
                    produit.getPrix()  // Prix unitaire du produit
            );
            ajouterLigneCommande(ligneCommande);
        }
    }

    private void ajouterLigneCommande(LigneCommandeEntity ligneCommande) throws SQLException {
       /* // Étape 1: Récupérer l'id_pro à partir de la table produit
        PreparedStatement preProduit = con.prepareStatement("SELECT id_pro FROM `produit`;");
        ResultSet rsProduit = preProduit.executeQuery();
        int id_pro = -1;
        if (rsProduit.next()) {
            id_pro = rsProduit.getInt("id_pro");
        } else {
            throw new SQLException("Produit non trouvé dans la base de données");
        }*/
        PreparedStatement pre = conn.prepareStatement("INSERT INTO `lingecommande`(`id_c`, `id_pro`, `quantite`, `prix`) VALUES (?, ?, ?, ?);");
        pre.setInt(1, ligneCommande.getId_c());
        pre.setInt(2,ligneCommande.getId_pro());
        //pre.setInt(2, id_pro);
        pre.setInt(3, ligneCommande.getQuantite());
        pre.setDouble(4, ligneCommande.getPrix());
        pre.executeUpdate();
    }

    public void changerEtatCommande(int id_c, String nouvelEtat) throws SQLException {
        PreparedStatement pre = conn.prepareStatement("UPDATE `commande` SET `etat` = ? WHERE `id_c` = ?;");
        pre.setString(1, nouvelEtat);
        pre.setLong(2, id_c);
        pre.executeUpdate();
    }

    public CommandeEntity trouverCommandeParId(int id_c) throws SQLException {
        PreparedStatement pre = conn.prepareStatement("SELECT * FROM `commande` WHERE `id_c` = ?;");
        pre.setLong(1, id_c);
        ResultSet rs = pre.executeQuery();

        if (rs.next()) {
            CommandeEntity commande = new CommandeEntity();
            commande.setId_c(rs.getInt("id_c"));
            commande.setClient_id(rs.getInt("client_id"));
            commande.setEtat(rs.getString("etat"));
            commande.setDate_c(rs.getDate("date_c").toLocalDate());
            commande.setTotal(rs.getDouble("total"));
            // Vous pouvez également récupérer les lignes de commande ici
            return commande;
        }
        return null;
    }
    public List<CommandeEntity> afficherToutesLesCommandes() throws SQLException {
        List<CommandeEntity> commandes = new ArrayList<>();
        PreparedStatement pre = conn.prepareStatement("SELECT * FROM `commande`;");
        ResultSet rs = pre.executeQuery();

        while (rs.next()) {
            CommandeEntity commande = new CommandeEntity();
            commande.setId_c(rs.getInt("id_c"));
            commande.setClient_id(rs.getInt("client_id"));
            commande.setEtat(rs.getString("etat"));
            commande.setDate_c(rs.getDate("date_c").toLocalDate());
            commande.setTotal(rs.getDouble("total"));
            // Vous pouvez également récupérer les lignes de commande ici
            commandes.add(commande);
        }
        return commandes;
    }

    public void supprimerCommande(int id_c) throws SQLException {
        // Supprimer les lignes de commande associées
        PreparedStatement preLigne = conn.prepareStatement("DELETE FROM `ligne_commande` WHERE `id_c` = ?;");
        preLigne.setLong(1, id_c);
        preLigne.executeUpdate();

        // Supprimer la commande
        PreparedStatement preCommande = conn.prepareStatement("DELETE FROM `commande` WHERE `id` = ?;");
        preCommande.setLong(1, id_c);
        preCommande.executeUpdate();
    }
    public int countCommandesByEtat(String etat) throws SQLException {
        String query = "SELECT COUNT(*) FROM commande WHERE etat = ?";
        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setString(1, etat);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            return rs.getInt(1);
        }
        return 0;
    }
    public CommandeDetailsEntity getCommandeDetails(int idCommande) throws SQLException {
        CommandeDetailsEntity commandeDetails = null;
        String query = "SELECT c.id_c, u.nom, u.email, u.n_tel, c.total, c.etat, p.nom_p, p.prix,p.image, lc.quantite " +
                "FROM commande c " +
                "JOIN users u ON c.client_id = u.id " +
                "JOIN lingecommande lc ON c.id_c = lc.id_c " +
                "JOIN produit p ON lc.id_pro = p.id_pro " +
                "WHERE c.id_c = ?";

        PreparedStatement pre = conn.prepareStatement(query);
        pre.setInt(1, idCommande);
        ResultSet rs = pre.executeQuery();

        List<ProduitDetailsEntity> produits = new ArrayList<>();

        while (rs.next()) {
            if (commandeDetails == null) {
                commandeDetails = new CommandeDetailsEntity(
                        rs.getInt("id_c"),
                        rs.getString("nom"),
                        rs.getString("email"),
                        rs.getInt("n_tel"),
                        rs.getDouble("total"),
                        rs.getString("etat")
                );
            }
            produits.add(new ProduitDetailsEntity(
                    rs.getString("nom_p"),
                    rs.getInt("prix"),
                    rs.getInt("quantite"),
                    rs.getString("image")
            ));
        }
        if (commandeDetails != null) {
            commandeDetails.setProduits(produits);
        }
        return commandeDetails;
    }


}