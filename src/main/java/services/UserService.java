package services;

import entites.RoleEntity;
import entites.UserEntity;
import utils.MySQLConnector;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserService implements IServices<UserEntity> {

    private static Connection con = MySQLConnector.getInstance().getConnection();
    private static Statement ste;

    public UserService() {
        try {
            ste = UserService.con.createStatement();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public boolean isEmailUnique(String email) throws SQLException {
        String query = "SELECT COUNT(*) FROM users WHERE email = ?";
        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            int count = resultSet.getInt(1);
            return count == 0;
        }
    }

    public static UserEntity login(String login, String mdp) {
        String query = "SELECT * FROM users WHERE login = ? AND mdp = ?";
        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, mdp);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("id");
                String nom = rs.getString("nom");
                String prenom = rs.getString("prenom");
                int age = rs.getInt("age");
                String email = rs.getString("email");
                int n_tel = rs.getInt("n_tel");
                int poids = rs.getInt("poids");
                int longeur = rs.getInt("longeur");
                int note_c = rs.getInt("note_c");
                int note_n = rs.getInt("note_n");
                int roleId = rs.getInt("role");

                RoleEntity role = new RoleEntity(roleId, null);

                UserEntity user = new UserEntity(id, age, poids, longeur, note_c, note_n, n_tel, nom, prenom, login, mdp, email, roleId);
                return user;
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void ajouter(UserEntity user) throws SQLException {
        if (!isEmailUnique(user.getEmail())) {
            System.out.println("Un utilisateur avec cet email existe déjà.");
            return;
        }

        String req = "INSERT INTO users (nom, prenom, age, email, n_tel, login, mdp, poids, longeur, note_c, note_n, role) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = con.prepareStatement(req)) {
            preparedStatement.setString(1, user.getNom());
            preparedStatement.setString(2, user.getPrenom());
            preparedStatement.setInt(3, user.getAge());
            preparedStatement.setString(4, user.getEmail());
            preparedStatement.setInt(5, user.getN_tel());
            preparedStatement.setString(6, user.getLogin());
            preparedStatement.setString(7, user.getMdp());
            preparedStatement.setInt(8, user.getPoids());
            preparedStatement.setInt(9, user.getLongeur());
            preparedStatement.setInt(10, user.getNote_c());
            preparedStatement.setInt(11, user.getNote_n());
            preparedStatement.setInt(12, user.getRole());

            preparedStatement.executeUpdate();
        }
    }

    @Override
    public void modifier(UserEntity userEntity) throws SQLException {
        // Implement modification logic if necessary
    }

    public void supprimer(UserEntity user) throws SQLException {
        String req = "DELETE FROM users WHERE id = ?";
        try (PreparedStatement preparedStatement = con.prepareStatement(req)) {
            preparedStatement.setInt(1, user.getId());
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Utilisateur supprimé avec succès.");
            } else {
                System.out.println("Aucun utilisateur trouvé avec cet ID.");
            }
        }
    }

    @Override
    public List<UserEntity> afficher() throws SQLException {
        return null;
    }

    public void update(UserEntity user) throws SQLException {
        String req = "UPDATE users SET nom = ?, prenom = ?, email = ?, mdp = ?, n_tel = ?, role = ?, age = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = con.prepareStatement(req)) {
            preparedStatement.setString(1, user.getNom());
            preparedStatement.setString(2, user.getPrenom());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getMdp());
            preparedStatement.setInt(5, user.getN_tel());
            preparedStatement.setInt(6, user.getRole());
            preparedStatement.setInt(7, user.getAge());
            preparedStatement.setInt(8, user.getId());
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Utilisateur mis à jour avec succès.");
            } else {
                System.out.println("Aucun utilisateur trouvé avec cet ID.");
            }
        }
    }

    @Override
    public UserEntity findbyId(int e) throws SQLException {
        return null;
    }

    public List<UserEntity> readAll() throws SQLException {
        List<UserEntity> user1 = new ArrayList<>();
        String query = "SELECT * FROM users";
        try (Statement statement = con.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                String nom = resultSet.getString("nom");
                String prenom = resultSet.getString("prenom");
                int age = resultSet.getInt("age");
                String email = resultSet.getString("email");
                int n_tel = resultSet.getInt("n_tel");

                UserEntity user2 = new UserEntity(nom, prenom, age, email, n_tel);
                user1.add(user2);
            }
        }
        return user1;
    }

    public UserEntity findById(int id) throws SQLException {
        String req = "SELECT * FROM users WHERE id = ?";
        UserEntity user = null;
        try (PreparedStatement preparedStatement = con.prepareStatement(req)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                user = new UserEntity(
                        resultSet.getInt("id"),
                        resultSet.getInt("age"),
                        resultSet.getInt("poids"),
                        resultSet.getInt("longeur"),
                        resultSet.getInt("note_c"),
                        resultSet.getInt("note_n"),
                        resultSet.getInt("n_tel"),
                        resultSet.getString("nom"),
                        resultSet.getString("prenom"),
                        resultSet.getString("login"),
                        resultSet.getString("mdp"),
                        resultSet.getString("email"),
                        resultSet.getInt("role")
                );
            }
        }
        return user;
    }

    public UserEntity readLastUser() throws SQLException {
        String req = "SELECT * FROM users ORDER BY id DESC LIMIT 1";
        ResultSet resultSet = ste.executeQuery(req);

        if (resultSet.next()) {
            return new UserEntity(
                    resultSet.getInt("id"),
                    resultSet.getInt("age"),
                    resultSet.getInt("poids"),
                    resultSet.getInt("longeur"),
                    resultSet.getInt("note_c"),
                    resultSet.getInt("note_n"),
                    resultSet.getInt("n_tel"),
                    resultSet.getString("nom"),
                    resultSet.getString("prenom"),
                    resultSet.getString("login"),
                    resultSet.getString("mdp"),
                    resultSet.getString("email"),
                    resultSet.getInt("role")
            );
        }
        return null;
    }
}
