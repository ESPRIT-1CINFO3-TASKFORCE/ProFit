package Services;
import Entites.ProgressionEntity;
import Entites.RegimeEntity;
import Utils.DataSource;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

import java.sql.Date;
import java.util.List;
public class RegimeService {

//********************************SINGLETON et CONNEXION BD****************************************

    private Connection con1;

    {
        try {
            con1 = DataSource.getInstance().getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Statement ste;
    // Déclaration de l'instance unique du singleton
    private static RegimeService instance;

    // Méthode privée pour le constructeur privé du singleton
    private RegimeService() {}

    // Méthode publique pour obtenir l'instance unique du singleton
    public static RegimeService getInstance() {
        if (instance == null) {
            instance = new RegimeService();
        }
        return instance;
    }

    // Obtenez la connexion à la base de données à partir de la source de données
    private Connection connection;

    {
        try {
            connection = DataSource.getInstance().getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //******************************************CRUD*************************************************
    // Méthode pour lire toutes les Régimes a partir de la base de données
    public List<RegimeEntity> readAll() throws SQLException {
        List<RegimeEntity> regimes = new ArrayList<>();
        String query = "SELECT * FROM régime";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                int id_regime = resultSet.getInt("id_regime");
                int id_client = resultSet.getInt("id_client");
                String nom_regime= resultSet.getString("nom_regime");
                String description = resultSet.getString("description");
                LocalDate date_debut = resultSet.getDate("date_debut").toLocalDate();
                LocalDate date_fin = resultSet.getDate("date_fin").toLocalDate();

                RegimeEntity regime = new RegimeEntity(id_regime, id_client, nom_regime, description, date_debut,date_fin);
                regimes.add(regime);
            }
        }
        return regimes;
    }


    // Méthode pour ajouter un Regime à la base de données
    public void AjouterRegime(RegimeEntity regime) throws SQLException {
        String ajou = "INSERT INTO `régime`(`id_regime`, `id_client`, `nom_regime`, `description`, `date_debut`, `date_fin`) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(ajou);
        preparedStatement.setInt(1, regime.getId_regime());
        preparedStatement.setInt(2, regime.getId_client());
        preparedStatement.setString(3, regime.getNom_regime());
        preparedStatement.setString(4, regime.getDescription());
        preparedStatement.setDate(5, java.sql.Date.valueOf(regime.getDate_debut()));
        preparedStatement.setDate(6, java.sql.Date.valueOf(regime.getDate_fin()));

        preparedStatement.executeUpdate();
    }



    //méthode pour supprimer un progrés

    public void deleteRegime(Long id) throws SQLException {
        String supprimer = "DELETE FROM régime WHERE id_regime = ?";

        try (PreparedStatement preparedStatement = con1.prepareStatement(supprimer)) {
            preparedStatement.setLong(1, id);

            int rowsDeleted = preparedStatement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("regime supprimée avec succès !");
            } else {
                System.out.println("Aucune regime trouvée avec l'ID : " + id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    //methode pour modifier un regime

    public void modifierRegime(Long id, RegimeEntity regimeDetails) throws SQLException {
        String query = "UPDATE régime SET nom_regime = ?,description = ?,date_debut=?,date_fin=? WHERE id_regime = ?";

        try (PreparedStatement preparedStatement = con1.prepareStatement(query)) {
            preparedStatement.setString(1, regimeDetails.getNom_regime());
            preparedStatement.setString(2, regimeDetails.getDescription());
            preparedStatement.setDate(3, java.sql.Date.valueOf(regimeDetails.getDate_debut()));
            preparedStatement.setDate(4, java.sql.Date.valueOf(regimeDetails.getDate_fin()));
            preparedStatement.setLong(5, id);

            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("regime modifiée avec succès !");
            } else {
                System.out.println("Aucune regime trouvée avec l'ID à modifier : " + id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }






















   /* private Connection con1= DataSource.getInstance().getCon();
    private Statement ste;

    //établir la connexion BD
    public RegimeService(){

        try {
            ste=con1.createStatement();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
/*
    //Ajouter Regime
    public void AjouterRegime(RegimeEntity regime) throws SQLException
    {
        String req="INSERT INTO `régime` (`id_regime`, `id_client`, `nom_regime`, `description`, `date_debut`, `date_fin`) " +
                "VALUES ( '\"+RegimeEntity.getId_regime+\"', '\"+RegimeEntity.getId_client+\"' ,'\"+RegimeEntity.getNom_regime+\"' ,'\"+RegimeEntity.getDescription+\"' ,'\"+RegimeEntity.getDate_debut+\"' ,'\"+RegimeEntity.getDate_fin+\"' );" ;
        ste.executeUpdate(req);



    }


    //Afficher regime
    public List<RegimeEntity> readAll() throws SQLException {
        List<RegimeEntity> l1 = new ArrayList<>();
        String aff = "SELECT * FROM régime;";

        ResultSet res = ste.executeQuery(aff);
        while (res.next()) {
            int id_regime = res.getInt(1);
            int id_client = res.getInt(2);
            String nom_regime = res.getString("nom_regime");
            String description = res.getString("description");
            Date date_debut = res.getDate("date_debut");
            Date date_fin = res.getDate("date_fin");

            RegimeEntity r1 = new RegimeEntity(id_regime, id_client, nom_regime, description, date_debut, date_fin);
            l1.add(r1);
        }
            return l1;
        }



    //Afficher regime par id client

    public void afficherRegimesParIdClient(Long id_client) throws SQLException {
        String query = "SELECT * FROM régime WHERE id_client = ?";

        try (PreparedStatement preparedStatement = con1.prepareStatement(query)) {
            preparedStatement.setLong(1, id_client);

            try (ResultSet res = preparedStatement.executeQuery()) {
                while (res.next()) {
                    int id_regime = res.getInt("id_regime");
                    String nom_regime = res.getString("nom_regime");
                    String description = res.getString("description");
                    java.util.Date date_debut = res.getDate("date_debut");
                    java.util.Date date_fin = res.getDate("date_fin");

                    System.out.println("ID Régime: " + id_regime);
                    System.out.println("Nom Régime: " + nom_regime);
                    System.out.println("Description: " + description);
                    System.out.println("Date début: " + date_debut);
                    System.out.println("Date fin: " + date_fin);
                    System.out.println("------------------------------------");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }






    //modifier un regime

    public void modifierRegime(Long id, RegimeEntity regimeDetails) throws SQLException {
        String modif = "UPDATE régime SET id_regime = ?, nom_regime = ?, description = ?, date_debut = ?, date_fin = ? WHERE id_client = ?";
                   //PreparedStatement pour eviter les erreur lors de l'injection
        try (PreparedStatement preparedStatement = con1.prepareStatement(modif)) {
            preparedStatement.setInt(1, regimeDetails.getId_regime());
            preparedStatement.setString(2, regimeDetails.getNom_regime());
            preparedStatement.setString(3, regimeDetails.getDescription());

            // Conversion des dates en java.sql.Date
            Date dateDebut = new Date(regimeDetails.getDate_debut().getTime());
            Date dateFin = new Date(regimeDetails.getDate_fin().getTime());

            preparedStatement.setDate(4, dateDebut);
            preparedStatement.setDate(5, dateFin);
            preparedStatement.setLong(6, id);
                       //rowsUpdated utilisé pour pour vérifier si l'opération de mise à jour a réussi
            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Modification réussie!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }



    //supprimer un regime
    // Méthode pour supprimer un régime par ID
    public void deleteRegime(Long id) throws SQLException {
        String deleteSQL = "DELETE FROM régime WHERE id_client = ?";

        try (PreparedStatement preparedStatement = con1.prepareStatement(deleteSQL)) {
            preparedStatement.setLong(1, id);
            //rpws

            int rowsDeleted = preparedStatement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Régime supprimé avec succès!");
            } else {
                System.out.println("Aucun régime trouvé avec l'ID: " + id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }
*/

}

