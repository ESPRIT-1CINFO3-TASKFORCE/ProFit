package Services;

import Entites.UserEntity;
import Utils.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserService implements IServices<UserEntity> {

    public UserService(Connection connection) {
        this.connection = connection;
    }

    private static Statement ste;
    private Connection connection;

    public UserService() {}

    public static UserEntity login(String login, String mdp) {
        String query = "SELECT * FROM users WHERE login = ? AND mdp = ?";
        try {
            PreparedStatement preparedStatement = DataSource.getInstance().getConnection().prepareStatement(query);
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, mdp);
            ResultSet rs = preparedStatement.executeQuery();
            System.out.println("Here: " + login);
            if (rs.next()) {
                UserEntity usr = new UserEntity();
                usr.setId(rs.getInt("id"));
                usr.setNom(rs.getString("nom"));
                usr.setPrenom(rs.getString("prenom"));
                usr.setAge(rs.getInt("age"));
                usr.setEmail(rs.getString("email"));
                usr.setN_tel(rs.getInt("n_tel"));
                usr.setPoids(rs.getInt("poids"));
                usr.setLongeur(rs.getInt("longeur"));
                usr.setNote_c(rs.getInt("note_c"));
                usr.setNote_n(rs.getInt("note_n"));
                usr.setRole(rs.getString("role"));

                System.out.println(usr.getRole());
                return usr;
            } else {
                System.out.println("Nothing found");
                return null; // ou lever une exception
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void logout(UserEntity user) {
        String query = "UPDATE users SET last_logout_time = ? WHERE id = ?";
        try {
            PreparedStatement preparedStatement = DataSource.getInstance().getConnection().prepareStatement(query);
            preparedStatement.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
            preparedStatement.setInt(2, user.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void ajouter(UserEntity user) throws SQLException {
        String req = "INSERT INTO users (nom, prenom, age, email, n_tel, login, mdp, poids, longeur, note_c, note_n, role) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = DataSource.getInstance().getConnection().prepareStatement(req)) {
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
            preparedStatement.setString(12, user.getRole());

            preparedStatement.executeUpdate();
        }
    }

    public List<UserEntity> readAll() {
        try {

            List<UserEntity> users = new ArrayList<>();
            String query = "SELECT * FROM users";
            try (Statement statement = DataSource.getInstance().getConnection().createStatement();
                 ResultSet resultSet = statement.executeQuery(query)) {
                while (resultSet.next()) {
                    UserEntity user = new UserEntity();
                    user.setId(resultSet.getInt("id"));
                    user.setNom(resultSet.getString("nom"));
                    user.setPrenom(resultSet.getString("prenom"));
                    user.setEmail(resultSet.getString("email"));
                    user.setRole(resultSet.getString("role"));
                    user.setN_tel(resultSet.getInt("n_tel"));
                    users.add(user);
                }
                return users;
            }
        } catch (SQLException se) {
            return null;
        }

    }

    public void supprimer(UserEntity user) throws SQLException {
        String req = "DELETE FROM users WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(req)) {
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
    public void update(UserEntity userEntity, int id) throws SQLException {

    }

    @Override
    public UserEntity findbyId(String e) throws SQLException {
        return null;
    }

    @Override
    public List<UserEntity> readAll1() throws SQLException {
        return null;
    }


    // Mise à jour
    public void update(UserEntity user) throws SQLException {
        String req = "UPDATE users SET nom = ?, prenom = ?, email = ?, n_tel = ?, role = ?, age = ?, active = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = DataSource.getInstance().getConnection().prepareStatement(req)) {
            preparedStatement.setString(1, user.getNom());
            preparedStatement.setString(2, user.getPrenom());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setInt(4, user.getN_tel());
            preparedStatement.setString(5, user.getRole());
            preparedStatement.setInt(6, user.getAge());
            preparedStatement.setBoolean(7, user.isActive());
            preparedStatement.setInt(8, user.getId());

            System.out.println("Executing query: " + preparedStatement);
            preparedStatement.executeUpdate();
            System.out.println("Update successful for user ID: " + user.getId());
        }
    }


    public void activateUser(int userId) throws SQLException {
        String sql = "UPDATE users SET active = true WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("Failed to activate user with ID: " + userId);
            }
        }
    }

    public void deactivateUser(int userId) throws SQLException {
        String req = "UPDATE users SET active = 0 WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(req)) {
            preparedStatement.setInt(1, userId);
            preparedStatement.executeUpdate();
            System.out.println("User with ID " + userId + " has been deactivated.");
        }
    }

    @Override
    public UserEntity findbyId(int e) throws SQLException {
        return null;
    }

    public UserEntity findById(int id) {
        String req = "SELECT * FROM users WHERE id = ?";
        UserEntity user = null;
        try (PreparedStatement preparedStatement = DataSource.getInstance().getConnection().prepareStatement(req)) {
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
                        resultSet.getString("role")

                );
            }
        } catch (Exception e) {
        }
        return user;
    }

    public boolean getUserByEmail(String email) {

        String req = "SELECT * FROM users WHERE email = ?";
        UserEntity user = null;
        System.out.println(email);
        try (PreparedStatement preparedStatement = DataSource.getInstance().getConnection().prepareStatement(req)) {
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) return true;
            return false;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<UserEntity> getUserEmail(String email) {
        String req = "SELECT * FROM users WHERE email = ?";
        List<UserEntity> users = new ArrayList<>();
        try (PreparedStatement preparedStatement = DataSource.getInstance().getConnection().prepareStatement(req)) {
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                UserEntity user = new UserEntity();
                user.setId(resultSet.getInt("id"));
                user.setNom(resultSet.getString("nom"));
                user.setPrenom(resultSet.getString("prenom"));
                user.setEmail(resultSet.getString("email"));
                user.setRole(resultSet.getString("role"));
                users.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }

    public UserEntity geEmailUser(String email) {
        String req = "SELECT * FROM users WHERE email = ?";
        UserEntity user = null;
        System.out.println(email);
        try (PreparedStatement preparedStatement = DataSource.getInstance().getConnection().prepareStatement(req)) {
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user = new UserEntity();
                user.setId(resultSet.getInt("id"));
                user.setEmail(resultSet.getString("email"));
                user.setMdp(resultSet.getString("mdp"));
                // Assurez-vous de définir les autres attributs de UserEntity
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    public void updateUserPassword(UserEntity user) {
        String req = "UPDATE users SET mdp = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = DataSource.getInstance().getConnection().prepareStatement(req)) {
            preparedStatement.setString(1, user.getMdp());
            preparedStatement.setInt(2, user.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public UserEntity findUser(String nom, String prenom, String email, int tel) {
        List<UserEntity> users = readAll();
        System.out.println("0 " + tel);
        if (nom.length() > 0) {
            for (int i = 0; i < users.size(); i++) {
                if (!users.get(i).getNom().toLowerCase().contains(nom.toLowerCase())) {
                    users.remove(i);
                    i--;
                }
            }
        }
        System.out.println("NAME FILTER " + users.size());
        if (prenom.length() > 0) {
            for (int i = 0; i < users.size(); i++) {
                if (!users.get(i).getPrenom().toLowerCase().contains(prenom.toLowerCase())) {
                    users.remove(i);
                    i--;
                }
            }
        }
        System.out.println("PRENAME FILTER " + users.size());
        if (email.length() > 0) {
            for (int i = 0; i < users.size(); i++) {
                if (!users.get(i).getEmail().toLowerCase().contains(email.toLowerCase())) {
                    users.remove(i);
                    i--;
                }
            }
        }
        System.out.println("EMAIL FILTER " + users.size());
        if (tel != 0) {
            for (int i = 0; i < users.size(); i++) {
                if (users.get(i).getN_tel() != tel) {
                    users.remove(i);
                    i--;
                }
            }
        }
        System.out.println("TEL FILTER " + users.size());
        if (users.size() > 0) return users.get(0);
        return null;
    }
}
