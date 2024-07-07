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

    private Connection con1= DataSource.getInstance().getCon();
    private Statement ste;
    // Déclaration de l'instance unique du singleton
    private static RegimeService instance;

    // Méthode privée pour le constructeur privé du singleton
    public RegimeService() {}

    // Méthode publique pour obtenir l'instance unique du singleton
    public static RegimeService getInstance() {
        if (instance == null) {
            instance = new RegimeService();
        }
        return instance;
    }

    // Obtenez la connexion à la base de données à partir de la source de données
    private Connection connection = DataSource.getInstance().getCon();

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


    //**************recuperer nom regime et date début***************
    public List<RegimeEntity> getNomDateRegimAvecUserDetails(int idClient) {
        List<RegimeEntity> regimess = new ArrayList<>();
        String regime = "SELECT r.nom_regime, r.date_fin FROM régime r JOIN users u ON r.id_client = u.id WHERE u.id = ? ORDER BY date_fin DESC LIMIT 1;";
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/profit2_db", "root", "");
             PreparedStatement statement = connection.prepareStatement(regime)) {

            statement.setInt(1, idClient); // Set the idClient parameter
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    RegimeEntity regim = new RegimeEntity();
                    regim.setNom_regime(resultSet.getString("nom_regime"));
                    regim.setDate_fin(LocalDate.parse(resultSet.getString("date_fin")));
                    regimess.add(regim);

                    // Ajoutez des messages de debug
                    System.out.println("NomRégime from DB: " + resultSet.getString("nom_regime"));
                    System.out.println("date_fin from DB: " + resultSet.getDate("date_fin"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return regimess;
    }


}

