package Services;

import Entites.ProduitEntity;
import Entites.UserEntity;
import Utils.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public  class ProduitService implements IServices<ProduitEntity> {
    private Connection conn;

    {
        try {
            conn = DataSource.getInstance().getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Statement ste;


    public ProduitService(){

        try {
            ste=conn.createStatement();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    //public List<UserEntity> readAll() throws SQLException;

    public void ajouter(ProduitEntity p) throws SQLException
    {
        PreparedStatement pre= conn.prepareStatement("INSERT INTO `produit`(`id_pro`, `nom_p`, `prix`, `qnt`, `image`) VALUES ( ?,?,?,?,?);");
        pre.setInt(1,p.getId_pro());
        pre.setString(2,p.getNom_p());
        pre.setInt(3,p.getPrix());
        pre.setInt(4,p.getQnt());
        pre.setString(5,p.getImage());
        pre.executeUpdate();
    }

    @Override
    public void supprimer(ProduitEntity p) throws SQLException {
        PreparedStatement pre = conn.prepareStatement("DELETE FROM `produit` WHERE `id_pro` = ?;");
        pre.setInt(1, p.getId_pro());
        pre.executeUpdate();
    }

    @Override
    public void update(ProduitEntity p,int id) throws SQLException {
        PreparedStatement pre = conn.prepareStatement("UPDATE `produit` SET `nom_p` = ?, `prix` = ?, `qnt` = ?, `image` = ? WHERE `id_pro` = ?;");
        pre.setString(1, p.getNom_p());
        pre.setInt(2, p.getPrix());
        pre.setInt(3, p.getQnt());
        pre.setString(4, p.getImage());
        pre.setInt(5, id);
        pre.executeUpdate();
    }

    @Override
    public ProduitEntity findbyId(String nom) throws SQLException {
        PreparedStatement pre = conn.prepareStatement("SELECT * FROM `produit` WHERE `nom_p` = ?;");
        pre.setString(1, nom);
        ResultSet rs = pre.executeQuery();
        if (rs.next()) {
            return new ProduitEntity(
                    rs.getInt("id_pro"),
                    rs.getString("nom_p"),
                    rs.getInt("prix"),
                    rs.getInt("qnt"),
                    rs.getString("image")
            );
        }
        return null;
    }

    @Override
    public List<ProduitEntity> readAll1() throws SQLException {
        List<ProduitEntity> produits = new ArrayList<>();
        ResultSet rs = ste.executeQuery("SELECT * FROM `produit`;");
        while (rs.next()) {
            produits.add(new ProduitEntity(
                    rs.getInt("id_pro"),
                    rs.getString("nom_p"),
                    rs.getInt("prix"),
                    rs.getInt("qnt"),
                    rs.getString("image")
            ));
        }
        return produits;
    }

    @Override
    public List<UserEntity> readAll() throws SQLException {
        return null;
    }

    @Override
    public UserEntity findbyId(int e) throws SQLException {
        return null;
    }
}

