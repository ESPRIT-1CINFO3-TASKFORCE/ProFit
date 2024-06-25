package Services;

import java.sql.*;
import Entites.ProgressionEntity;
import Entites.RegimeEntity;
import Utils.DataSource;
import com.mysql.cj.protocol.a.LocalDateTimeValueEncoder;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ProgressionService {
    //********************************SINGLETON et CONNEXION BD****************************************

    private Connection con1= DataSource.getInstance().getCon();
    private Statement ste;
    // Déclaration de l'instance unique du singleton
    private static ProgressionService instance;

    // Méthode privée pour le constructeur privé du singleton
    public ProgressionService() {}

    // Méthode publique pour obtenir l'instance unique du singleton
    public static ProgressionService getInstance() {
        if (instance == null) {
            instance = new ProgressionService();
        }
        return instance;
    }

    // Obtenez la connexion à la base de données à partir de la source de données
    private Connection connection = DataSource.getInstance().getCon();

    //******************************************CRUD*************************************************

    // Méthode pour ajouter une progression à la base de données
    public void AjouterProgression(ProgressionEntity progression) throws SQLException {
        String req = "INSERT INTO progression (id_prg, id, poids, longueur, IMC, date_inscri, description, masse_musc) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(req);
        preparedStatement.setInt(1, progression.getId_prg());
        preparedStatement.setInt(2, progression.getId());
        preparedStatement.setInt(3, progression.getPoids());
        preparedStatement.setDouble(4, progression.getLongueur());
        preparedStatement.setInt(5, progression.getIMC());
        preparedStatement.setDate(6, java.sql.Date.valueOf(progression.getDate_inscri()));
        preparedStatement.setString(7, progression.getDesciption());
        preparedStatement.setInt(8, progression.getMasse_musc());
        preparedStatement.executeUpdate();
    }

    // Méthode pour lire toutes les progressions de la base de données
    public List<ProgressionEntity> readAll() throws SQLException {
        List<ProgressionEntity> progressions = new ArrayList<>();
        String query = "SELECT * FROM progression";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                int id_prg = resultSet.getInt("id_prg");
                int id = resultSet.getInt("id");
                int poids = resultSet.getInt("poids");
                double longueur = resultSet.getDouble("longueur");
                int IMC = resultSet.getInt("IMC");
                LocalDate date_inscri = resultSet.getDate("date_inscri").toLocalDate();
                String description = resultSet.getString("description");
                int masse_musc = resultSet.getInt("masse_musc");
                ProgressionEntity progression = new ProgressionEntity(id_prg, id, poids, longueur, IMC, date_inscri, description, masse_musc);
                progressions.add(progression);
            }
        }
        return progressions;
    }

    //méthode pour supprimer un progrés

    public void deleteProgression(Long id) throws SQLException {
        String supprimer = "DELETE FROM progression WHERE id_prg = ?";

        try (PreparedStatement preparedStatement = con1.prepareStatement(supprimer)) {
            preparedStatement.setLong(1, id);

            int rowsDeleted = preparedStatement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Progression supprimée avec succès !");
            } else {
                System.out.println("Aucune progression trouvée avec l'ID : " + id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    //methode pour modifier un progres

    public void modifierProgres(Long id, ProgressionEntity progressionDetails) throws SQLException {
        String query = "UPDATE progression SET poids = ?, longueur = ?, IMC = ?, Masse_musc = ?, date_inscri = ?, description = ? WHERE id_prg = ?";

        try (PreparedStatement preparedStatement = con1.prepareStatement(query)) {
            preparedStatement.setInt(1, progressionDetails.getPoids());
            preparedStatement.setDouble(2, progressionDetails.getLongueur());
            preparedStatement.setInt(3, progressionDetails.getIMC());
            preparedStatement.setInt(4, progressionDetails.getMasse_musc());
            preparedStatement.setDate(5, java.sql.Date.valueOf(progressionDetails.getDate_inscri()));
            preparedStatement.setString(6, progressionDetails.getDesciption());
            preparedStatement.setLong(7, id);

            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Progression modifiée avec succès !");
            } else {
                System.out.println("Aucune progression trouvée avec l'ID à modifier : " + id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }


    //Afficher progression par id client

    public List<ProgressionEntity> getProgressionsByClientId(int clientId) {
        List<ProgressionEntity> progressions = new ArrayList<>();

        String query = "SELECT id_prg, id, poids, longueur, IMC, date_inscri, description, masse_musc FROM progression WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, clientId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id_prg = resultSet.getInt("id_prg");
                int id_client = resultSet.getInt("id");
                int poids = resultSet.getInt("poids");
                double longueur = resultSet.getDouble("longueur");
                int IMC = resultSet.getInt("IMC");
                LocalDate date_inscri = resultSet.getDate("date_inscri").toLocalDate();
                String description = resultSet.getString("description");
                int masse_musc = resultSet.getInt("masse_musc");

                ProgressionEntity progression = new ProgressionEntity(id_prg, id_client, poids, longueur, IMC, date_inscri, description, masse_musc);
                progressions.add(progression);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return progressions;
    }
}











