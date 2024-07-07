package services;

import entites.Exercice;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ExerciceServices {

    private Connection connection;

    public ExerciceServices(Connection connection) {
        this.connection = connection;
    }

    public void ajouter(Exercice exercice) {
        String query = "INSERT INTO exercices (nomEx, description, difficulte, dureeMinutes, caloriesBrulees, musculesCibles) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, exercice.getNomEx());
            preparedStatement.setString(2, exercice.getDescription());
            preparedStatement.setString(3, exercice.getDifficulte());
            preparedStatement.setInt(4, exercice.getDureeMinutes());
            preparedStatement.setInt(5, exercice.getCaloriesBrulees());
            preparedStatement.setString(6, exercice.getMusculesCibles());

            preparedStatement.executeUpdate();
            System.out.println("Exercice ajouté avec succès !");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Exercice> getAll() {
        List<Exercice> list = new ArrayList<>();
        String sql = "SELECT * FROM exercices";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet rs = preparedStatement.executeQuery()) {

            while (rs.next()) {
                Exercice exercice = new Exercice(
                        rs.getString("nomEx"),
                        rs.getString("description"),
                        rs.getString("difficulte"),
                        rs.getInt("dureeMinutes"),
                        rs.getInt("caloriesBrulees"),
                        rs.getString("musculesCibles")
                );
                exercice.setId(rs.getInt("id"));
                list.add(exercice);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void update(Exercice exercice, int id) throws SQLException {
        String sql = "UPDATE exercices SET nomEx = ?, description = ?, difficulte = ?, dureeMinutes = ?, caloriesBrulees = ?, musculesCibles = ? WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, exercice.getNomEx());
            statement.setString(2, exercice.getDescription());
            statement.setString(3, exercice.getDifficulte());
            statement.setInt(4, exercice.getDureeMinutes());
            statement.setInt(5, exercice.getCaloriesBrulees());
            statement.setString(6, exercice.getMusculesCibles());
            statement.setInt(7, id);

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("Échec de la mise à jour de l'exercice, aucune ligne affectée.");
            }
        }
    }
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM exercices WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("Échec de la suppression de l'exercice, aucune ligne affectée.");
            }

            System.out.println("Exercice supprimé avec succès !");
        }
    }

}
